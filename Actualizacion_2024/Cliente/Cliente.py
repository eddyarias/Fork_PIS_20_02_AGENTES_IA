import pandas as pd
import numpy as np
import requests
import json
import time

def Agente():
    filename_dataset = '20221215_151443_pre.csv'
    df = pd.read_csv(filename_dataset)
    X = df[['steering_angle_', 'speed', 'rpm_', 'acceleration', 'throttle_position', 'engine_temperature','system_voltage', 
            'heart_rate', 'distance_travelled', 'latitude','longitude', 'current_weather', 'accidents_onsite']]
    url = 'https://18g5d0scba.execute-api.us-east-1.amazonaws.com/FaseBeta/Recursos_Predictor_Accidentes'
    counter = 0
    while counter < 50:
        random_number = np.random.randint(0, 2633 + 1)
        data = X.loc[[random_number]].values.tolist()
        my_obj = {"Input": data}
        response = requests.post(url, data = json.dumps(my_obj))
        risk_level_values = {1: 'low', 2: 'medium', 3: 'high', 4: 'very high'}
        risk_level = eval(response.text)
        risk_level_category = ""
        for value in risk_level_values:
            if value == risk_level['Output']:
                risk_level_category = risk_level_values[value]
                break
        print("El valor riesgo para: '{}' es '{}'.".format(my_obj['Input'], risk_level_category.upper()))
        print(risk_level)
        print("        ")
        counter = counter + 1
        time.sleep(0.25)
Agente()