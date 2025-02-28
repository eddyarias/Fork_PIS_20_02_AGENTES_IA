# Usa la imagen base de AWS Lambda para Python
FROM public.ecr.aws/lambda/python:3.9

# Instalar dependencias
RUN pip install --no-cache-dir joblib boto3 scikit-learn

# Copiar el código de inferencia
COPY inference.py . 

# Comando de inicio
CMD ["inference.lambda_handler"]

