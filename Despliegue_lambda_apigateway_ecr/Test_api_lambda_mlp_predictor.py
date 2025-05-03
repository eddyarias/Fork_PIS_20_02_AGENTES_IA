import pandas as pd
import numpy as np
import requests
import json
import time

filename_dataset = '../Actualizacion_2024/Cliente/20221215_151443_pre.csv'
df = pd.read_csv(filename_dataset)

X = df[['steering_angle_', 'speed', 'rpm_', 'acceleration', 'throttle_position', 'engine_temperature','system_voltage', 
            'heart_rate', 'distance_travelled', 'latitude','longitude', 'current_weather', 'accidents_onsite']]

def Agente():
    url = 'https://ye3c74bo62.execute-api.us-east-1.amazonaws.com/nueva_etapa/recurso-mlp-predictor'
    counter = 0
    risk_level_values = {1: 'low', 2: 'medium', 3: 'high', 4: 'very high'}

    while counter < 20:
        random_number = np.random.randint(0, 2634)
        data = X.loc[[random_number]].values.tolist()
        my_obj = {"Input": data}

        response = requests.post(url, data=json.dumps(my_obj))

        if response.status_code == 200:
            outer = json.loads(response.text)               
            inner = json.loads(outer['body'])               
            output_value = inner['Output']                  # Obtener valor numérico

            risk_level_category = risk_level_values.get(output_value, 'unknown')

            print("El valor riesgo para: '{}' es '{}'.".format(my_obj['Input'], risk_level_category.upper()))
            print(inner)
            print("        ")
        else:
            print(f"Error en la solicitud: {response.status_code}")
        
        counter += 1
        time.sleep(0.25)

Agente()