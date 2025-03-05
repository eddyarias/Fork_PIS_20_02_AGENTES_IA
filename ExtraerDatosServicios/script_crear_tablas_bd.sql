-- Tabla route2_accident_time (se utiliza una clave compuesta con point, coordinates y hour)
CREATE TABLE route2_accident_time (
  point VARCHAR(10),
  coordinates VARCHAR(50),
  hour INTEGER,
  count INTEGER,
  PRIMARY KEY (point, coordinates, hour)
);

-- Tabla accidents_quito (todos los campos se crean según el CSV)
CREATE TABLE accidents_quito (
  id INTEGER PRIMARY KEY,
  anio INTEGER,
  siniestros VARCHAR(50),
  lesionados INTEGER,
  fallecidos INTEGER,
  ente_de_control VARCHAR(100),
  latitud_y DOUBLE PRECISION,
  longitud_x DOUBLE PRECISION,
  dpa_1 INTEGER,
  provincia VARCHAR(50),
  dpa_2 INTEGER,
  canton VARCHAR(50),
  dpa_3 INTEGER,
  parroquia VARCHAR(50),
  direccion VARCHAR(200),
  zona_planificacion VARCHAR(50),
  zona VARCHAR(50),
  id_de_la_via VARCHAR(50),
  nombre_de_la_via VARCHAR(100),
  ubicacion_de_la_via VARCHAR(100),
  jerarquia_de_la_via VARCHAR(50),
  fecha DATE,
  hora TIME,
  periodo_1 VARCHAR(50),
  periodo_2 VARCHAR(50),
  dia_1 VARCHAR(50),
  dia_2 VARCHAR(50),
  mes_1 VARCHAR(50),
  mes_2 VARCHAR(50),
  feriado VARCHAR(10),
  codigo_causa VARCHAR(50),
  causa_probable VARCHAR(255),
  tipo_de_siniestro VARCHAR(50),
  tipo_de_vehiculo_1 VARCHAR(50),
  servicio_1 VARCHAR(50),
  automovil INTEGER,
  bicicleta INTEGER,
  bus INTEGER,
  camion INTEGER,
  camioneta INTEGER,
  emergencias INTEGER,
  especial INTEGER,
  furgoneta INTEGER,
  motocicleta INTEGER,
  no_identificado INTEGER,
  scooter_electrico INTEGER,
  tricimoto INTEGER,
  vehiculo_deportivo_utilitario INTEGER,
  suma_de_vehiculos INTEGER,
  tipo_id_1 VARCHAR(50),
  edad_1 INTEGER,
  sexo_1 VARCHAR(20),
  condicion_1 VARCHAR(50),
  participante_1 VARCHAR(50),
  casco_1 VARCHAR(50),
  cinturon_1 VARCHAR(50)
);

-- Tabla design_speed
CREATE TABLE design_speed (
  id_point VARCHAR(10) PRIMARY KEY,
  id_route VARCHAR(10),
  id_road VARCHAR(10),
  id_segment VARCHAR(10),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION,
  speed INTEGER
);

CREATE OR REPLACE FUNCTION haversine(
  lat1 DOUBLE PRECISION, lon1 DOUBLE PRECISION,
  lat2 DOUBLE PRECISION, lon2 DOUBLE PRECISION
) RETURNS DOUBLE PRECISION AS $$
DECLARE
    dlat DOUBLE PRECISION := radians(lat2 - lat1);
    dlon DOUBLE PRECISION := radians(lon2 - lon1);
BEGIN
    RETURN 2 * 6371 * asin( sqrt( sin(dlat/2)^2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon/2)^2 ) );
END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE OR REPLACE FUNCTION get_number_accidents_time(
  p_input_lat DOUBLE PRECISION,
  p_input_lon DOUBLE PRECISION,
  p_hour INTEGER
) RETURNS INTEGER AS $$
DECLARE
    rec RECORD;
    dist DOUBLE PRECISION;
BEGIN
    SELECT point, coordinates, hour, count,
           CAST(split_part(coordinates, ',', 1) AS DOUBLE PRECISION) AS lat,
           CAST(split_part(coordinates, ',', 2) AS DOUBLE PRECISION) AS lon
    INTO rec
    FROM route2_accident_time
    ORDER BY haversine(p_input_lat, p_input_lon,
                       CAST(split_part(coordinates, ',', 1) AS DOUBLE PRECISION),
                       CAST(split_part(coordinates, ',', 2) AS DOUBLE PRECISION))
    LIMIT 1;

    IF rec IS NULL THEN
      RETURN 0;
    END IF;

    dist := haversine(p_input_lat, p_input_lon,
                      CAST(split_part(rec.coordinates, ',', 1) AS DOUBLE PRECISION),
                      CAST(split_part(rec.coordinates, ',', 2) AS DOUBLE PRECISION));

    IF dist <= 0.2 AND rec.hour = p_hour THEN
         RETURN rec.count;
    ELSE
         RETURN 0;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_number_accidents_onsitu(
  p_input_lat DOUBLE PRECISION,
  p_input_lon DOUBLE PRECISION
) RETURNS INTEGER AS $$
DECLARE
    total INTEGER;
BEGIN
    SELECT COUNT(*) INTO total
    FROM accidents_quito
    WHERE haversine(p_input_lat, p_input_lon, latitud_y, longitud_x) <= 0.2;

    RETURN total;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_design_speed_onsitu(
  p_input_lat DOUBLE PRECISION,
  p_input_lon DOUBLE PRECISION
) RETURNS INTEGER AS $$
DECLARE
    rec RECORD;
    dist DOUBLE PRECISION;
BEGIN
    SELECT id_point, latitude, longitude, speed
    INTO rec
    FROM design_speed
    ORDER BY haversine(p_input_lat, p_input_lon, latitude, longitude)
    LIMIT 1;

    IF rec IS NULL THEN
      RETURN 50;
    END IF;

    dist := haversine(p_input_lat, p_input_lon, rec.latitude, rec.longitude);

    IF dist <= 0.2 THEN
         RETURN rec.speed;
    ELSE
         RETURN 50;
    END IF;
END;
$$ LANGUAGE plpgsql;