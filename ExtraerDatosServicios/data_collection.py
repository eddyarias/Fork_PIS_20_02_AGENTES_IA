import ast
import pandas as pd
from haversine import haversine

# Constantes necesarias
ROUND_DISTANCE_FOR_ACCIDENT = 0.2
ROUND_DISTANCE_FOR_SPEED = 0.2
DEFAULT_SPEED = 50

# Accidentes hora 3
def get_number_accidents_time(locations, time, latitude, longitude):
     # Initialize coordinate variable
    coordinate = (test_latitude, test_longitude)
 
    points = locations['coordinates'].unique()
    distance = 0

    for point in points:
        lat_lon = str(point).split(',')
        latitude_1 = ast.literal_eval(lat_lon[0])
        longitude_1 = ast.literal_eval(lat_lon[1])
        haver_distance = haversine((latitude_1, longitude_1), (latitude, longitude))

        if haver_distance <= ROUND_DISTANCE_FOR_SPEED:
            if distance == 0:
                distance = haver_distance
            if haver_distance <= distance:
                distance = haver_distance
                coordinate = point

    hour = int(time.split(':')[0])
    group = locations[locations['coordinates'] == coordinate]

    number = 0
    for k, v in group.iterrows():
        if v['hour'] == hour:
            number = v['count']
            break

    return number

# Accidentes sitio 1
def get_number_accidents_onsitu(locations, latitude, longitude):
    new_column = []
    for row in locations.itertuples():
        latitude_1 = row.LATITUD_Y  # replace with actual column name
        longitude_1 = row.LONGITUD_X  # replace with actual column name
        distance = haversine((latitude, longitude), (latitude_1, longitude_1))
        new_column.append(distance)

    number_accidents = 0
    for i in new_column:
        if i <= ROUND_DISTANCE_FOR_ACCIDENT:
            number_accidents += 1
    return number_accidents

# Velocidad de diseño 2
def get_design_speed_onsitu(locations, latitude, longitude):
    speed = DEFAULT_SPEED
    distance = 0
    for row in locations.itertuples():
        latitude_1 = row.latitude
        longitude_1 = row.longitude
        design_speed = row.speed
        
        haver_distance = haversine((latitude, longitude), (latitude_1, longitude_1))
        if haver_distance <= ROUND_DISTANCE_FOR_SPEED:
            if distance == 0:
                distance = haver_distance
            if haver_distance <= distance:
                distance = haver_distance
                speed = design_speed
    return speed

if __name__ == '__main__':
    # Se leen los CSV necesarios
    df_acc_time = pd.read_csv("route2_accident_time.csv")
    df_acc_loc = pd.read_csv('accidents_quito.csv')
    df_speed = pd.read_csv('design_speed.csv')
    
    # Parámetros de prueba (estas coordenadas y hora pueden ajustarse según los datos reales)
    test_latitude = -0.083501
    test_longitude = -78.417742
    test_time = "3"  # Se espera que en el CSV de accidentes por hora exista un registro con esta hora

    # Llamada a las funciones de prueba
    accidents_time_result = get_number_accidents_time(df_acc_time, test_time, test_latitude, test_longitude)
    accidents_onsitu_result = get_number_accidents_onsitu(df_acc_loc, test_latitude, test_longitude)
    design_speed_result = get_design_speed_onsitu(df_speed, test_latitude, test_longitude)
    
    # Mostrar resultados
    print("Número de accidentes por hora:", accidents_time_result)
    print("Número de accidentes en sitio:", accidents_onsitu_result)
    print("Velocidad de diseño:", design_speed_result)
