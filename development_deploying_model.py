#!/usr/bin/env python
# coding: utf-8

import boto3
import sagemaker
import pandas as pd
import numpy as np
from sagemaker import image_uris
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPClassifier
import joblib
import subprocess

# load dataset and choose features
filename_dataset = '20221215_151443_pre.csv'
df = pd.read_csv(filename_dataset)
X = df[['steering_angle_', 'speed', 'rpm_', 'acceleration', 'throttle_position', 'engine_temperature',
               'system_voltage', 'heart_rate', 'distance_travelled', 'latitude','longitude', 'current_weather',
               'accidents_onsite']]
y = df[['accident_rate']]
X = X.dropna(0)
x_train, x_test, y_train, y_test = train_test_split(X, y, test_size=0.20)

# configure and train model
mlp = MLPClassifier(hidden_layer_sizes=(10, 10, 10), max_iter=1000)
mlp.fit(x_train, y_train.values.ravel())
predictions = mlp.predict(x_test)

# export model
with open('model.joblib', 'wb') as f:
    joblib.dump(mlp, f)

# compress model
command = "tar -cvpzf model.tar.gz model.joblib inference.py"
process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
output, error = process.communicate()

# upload model to S3
s3 = boto3.session.Session().resource('s3')
session_sagemaker = sagemaker.Session()
bucket = session_sagemaker.default_bucket() 
response = s3.meta.client.upload_file('model.tar.gz', bucket, 'model.tar.gz')

# get region name and configure the client
region = boto3.Session().region_name
client = boto3.client("sagemaker", region_name=region)

# role to give SageMaker permission to access AWS services.
sagemaker_role = sagemaker.get_execution_role()

# get model from S3
model_url = f"s3://{bucket}/model.tar.gz"

# get container image (prebuilt example)
container = image_uris.retrieve(framework='sklearn', region=region, version='0.23-1', py_version="py3", 
                                instance_type="ml.m5.large")
# create model
model_name = "accident-prediction"
model_artifacts = f"s3://{bucket}/model.tar.gz"

response = client.create_model(
    ModelName = model_name,
    ExecutionRoleArn = sagemaker_role,
    Containers = [{
        "Image": container,
        "Mode": "SingleModel",
        "ModelDataUrl": model_url,
        "Environment": {'SAGEMAKER_SUBMIT_DIRECTORY': model_artifacts,
                           'SAGEMAKER_PROGRAM': 'inference.py'} 
    }]
)

# configure the end point
configuration_name = "endpoint-configuration-prediction-model"

response = client.create_endpoint_config(
   EndpointConfigName=configuration_name,
   ProductionVariants=[
        {
            "ModelName": model_name,
            "VariantName": "accident",
            "InstanceType": "ml.m5.large",
            "InitialInstanceCount": 1
            # }
        } 
    ]
)

# create the end point
response = client.create_endpoint(
    EndpointName="endpoint-accident-prediction",
    EndpointConfigName=configuration_name
)
