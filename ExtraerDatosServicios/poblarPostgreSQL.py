#pip install pandas==1.3.3 sqlalchemy==1.4.23 psycopg2==2.9.1 haversine==2.5.1

import pandas as pd
from sqlalchemy import create_engine

# Configura la conexión a PostgreSQL (ajusta el usuario, contraseña, host, puerto y nombre de base de datos)
engine = create_engine('postgresql://postgres:P%40ssw0rd@localhost:5432/accidents_uio')

# Cargar route2_accident_time.csv
df_acc_time = pd.read_csv("route2_accident_time.csv")
# Transformar las coordenadas a tipo string si es necesario
df_acc_time['coordinates'] = df_acc_time['coordinates'].astype(str)
# Insertar en la tabla route2_accident_time (si la tabla ya existe, agregará los datos sin sobrescribirla)
df_acc_time.to_sql('route2_accident_time', engine, if_exists='append', index=False)

# Cargar accidents_quito.csv
df_acc_quito = pd.read_csv('accidents_quito.csv')
# Si es necesario, transformar columnas de fecha y hora
df_acc_quito['fecha'] = pd.to_datetime(df_acc_quito['FECHA'], format='%d/%m/%Y', errors='coerce')
df_acc_quito['hora'] = pd.to_datetime(df_acc_quito['HORA'], format='%H:%M:%S', errors='coerce').dt.time
# Renombrar columnas para que se almacenen en minúsculas (coincidiendo con la definición en la BD)
df_acc_quito.columns = [col.lower() for col in df_acc_quito.columns]
# Insertar en la tabla accidents_quito
df_acc_quito.to_sql('accidents_quito', engine, if_exists='append', index=False)

# Cargar design_speed.csv
df_speed = pd.read_csv("design_speed.csv")
# Asegúrate de que las columnas tengan el formato correcto
df_speed['latitude'] = df_speed['latitude'].astype(float)
df_speed['longitude'] = df_speed['longitude'].astype(float)
df_speed['speed'] = df_speed['speed'].astype(float)
# Insertar en la tabla design_speed
df_speed.to_sql('design_speed', engine, if_exists='append', index=False)
