import json
import pg8000
import os

# Variables de entorno para la conexión a la base de datos
DB_HOST = 'database-1.conw8u8y6q2u.us-east-1.rds.amazonaws.com'
DB_NAME = 'accidents_uio'
DB_USER = 'postgres'
DB_PASSWORD = 'P4ssw0rd123456789'


def lambda_handler(event, context):
    # Obtener los parámetros de entrada (latitud, longitud, time como entero)
    latitude = event.get('latitude')
    longitude = event.get('longitude')
    time = event.get('time')  # Se espera un entero

    if latitude is None or longitude is None or time is None:
        return {
            'statusCode': 400,
            'body': json.dumps({'message': 'Faltan parámetros. Se requieren latitud, longitud y hora (entero).'})
        }
    
    try:
        # Conexión a la base de datos usando pg8000
        conn = pg8000.connect(
            host=DB_HOST,
            database=DB_NAME,
            user=DB_USER,
            password=DB_PASSWORD
        )
        cursor = conn.cursor()

        # Consulta SQL con los parámetros proporcionados
        query = f"""
            SELECT
                get_number_accidents_time({latitude}, {longitude}, {time}) AS num_accidents_time,
                get_number_accidents_onsitu({latitude}, {longitude}) AS num_accidents_onsitu,
                get_design_speed_onsitu({latitude}, {longitude}) AS design_speed;
        """
        cursor.execute(query)
        result = cursor.fetchone()

        # Cerrar la conexión
        cursor.close()
        conn.close()

        if result:
            response = {
                "num_accidents_time": result[0],
                "num_accidents_onsitu": result[1],
                "design_speed": result[2]
            }
            return {
                'statusCode': 200,
                'body': json.dumps(response)
            }
        else:
            return {
                'statusCode': 404,
                'body': json.dumps({'message': 'No se encontraron resultados.'})
            }
    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            'statusCode': 500,
            'body': json.dumps({'message': 'Error en la consulta a la base de datos'})
        }
