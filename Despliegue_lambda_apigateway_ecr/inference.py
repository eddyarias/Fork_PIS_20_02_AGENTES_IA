from joblib import load
from boto3 import client
from json import loads, dumps
from os import path

# Configuración de S3
s3 = client("s3")
BUCKET_NAME = "bucket-poli-api"  # Reemplaza con el nombre de tu bucket
MODEL_FILE = "model.joblib"
MODEL_PATH = "/tmp/model.joblib"

# Descargar modelo desde S3 (solo si no existe en /tmp/)
def download_model():
    if not path.exists(MODEL_PATH):
        s3.download_file(BUCKET_NAME, MODEL_FILE, MODEL_PATH)
    return load(MODEL_PATH)

# Cargar el modelo
model = download_model()

# Función principal de Lambda
import json

def lambda_handler(event, context):
    try:
        print("Evento recibido:", event)
        headers = event.get("headers", {})
        print("Headers:", headers)
        body = event.get("body", {})
        print("Body:", body)
        
        # Si se usa API Gateway proxy integration, el cuerpo viene en event["body"]
        if "body" in event:
            if event["body"]:
                body = json.loads(event["body"])
            else:
                response = {'Error': 'El cuerpo de la solicitud (body) está vacío.'}
                return {
                    "statusCode": 400,
                    "headers": {
                        "Access-Control-Allow-Origin": "*"
                    },
                    "body": json.dumps(response)
                }
        else:
            body = event

        if "Input" not in body:
            response = {'Error': 'No se encontró la clave "Input" en el evento.'}
            return {
                "statusCode": 400,
                "headers": {
                    "Access-Control-Allow-Origin": "*"
                },
                "body": json.dumps(response)
            }
        
        input_data = body["Input"]

        prediction = model.predict(input_data)
        res = int(prediction[0])
        response = {'Output': res}
        return {
            "statusCode": 200,
            "headers": {
                "Access-Control-Allow-Origin": "*"
            },
            "body": json.dumps(response)
        }
    except Exception as e:
        response = {'Error en try': str(e)}
        return {
            "statusCode": 500,
            "headers": {
                "Access-Control-Allow-Origin": "*"
            },
            "body": json.dumps(response)
        }
