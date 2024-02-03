import boto3
import json
import os
import io

ENDPOINT_NAME = os.environ['ENDPOINT_NAME']
runtime = boto3.client('runtime.sagemaker')

def lambda_handler(event, context):
    data = json.loads(json.dumps(event))
    response = runtime.invoke_endpoint(EndpointName = ENDPOINT_NAME, Body = json.dumps(data))
    result = json.loads(response['Body'].read().decode())
    return result