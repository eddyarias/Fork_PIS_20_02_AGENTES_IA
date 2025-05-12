# Fork del Proyecto: PIS_20_02_AGENTES_IA

Este repositorio es un fork del proyecto Polidriving, creado para implementar actualizaciones y mejoras específicas.

**Última actualización:** 11/05/2025

## Principales características de este fork

### Despliegue en la nube
- Uso de **Amazon ECR**, **AWS Lambda** y **API Gateway** para desplegar el modelo entrenado (.joblib) en la nube.

### Base de datos relacional
- Implementación de una base de datos relacional en **Amazon RDS** utilizando PostgreSQL.
- Integración con **API Gateway** para obtener datos como accidentes y velocidad del sitio, los cuales serán utilizados en las predicciones del modelo.

## Manuales para despliegues en AWS

1. [Manual para el despliegue del modelo en ECR, S3, Lambda y API Gateway](Despliegue_lambda_apigateway_ecr/Manual%20Despliegue%20modelo%20en%20ECR,%20S3,%20Lambda%20y%20api%20gateway/Despliegue%20modelo%20en%20ECR,%20S3,%20Lambda%20y%20api%20gateway.md)

2. [Manual para el despliegue de la base de datos en RDS y su consumo con Lambda y API Gateway](ExtraerDatosServicios/Manual%20Despliegue%20BD%20en%20RDS%20y%20consumir%20con%20lambda%20y%20api%20gateway/Despliegue%20BD%20en%20RDS%20y%20consumir%20con%20lambda%20y%20api%20g.md)
    