/**************************************************************************************************************************
 **                                                                                                                      **
 **                                              PROYECTO PIS-20-02                                                      **
 **                                         AGENTE DE RESPUESTA SIGOAVE                                                  **
 **                                                                                                                      **
 **                                                DESARROLLADOR                                                         **
 **                                                                                                                      **
 **                                         ARROYO AUZ CHRISTIAN XAVIER                                                  **
 **                                         christian.arroyo@epn.edu.ec                                                  **
 **                                                                                                                      **
 *************************************************************************************************************************/

//Paquete que contiene un conjunto de clases relacionadas por finalidad, ámbito y herencia
package com.proyecto.pis_20_02.clases.principal;

//Clases que permiten el control de vida de los Fragmentos de la App
//Clases usadas para reconocimiento de elementos en actividades
//Clases usadas para cambio entre actividades
//Clases usadas para la conexión interclases
//Clases usadas para el uso de fragmentos
//Clases usadas para el mapeo de cadenas
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class FragmentoModeloVista extends ViewModel {
    //Creación de variable para enviar, presentar y recibir información
    private static final MutableLiveData<Integer> indice = new MutableLiveData<>();

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> respuestaAgente = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> precipitacion = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> temperatura = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> inclinacion = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> ubicacion = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> vehiculos = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> ocupacion = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> direccion = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> velocidad = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> baterias = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> promedio = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> humedad = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> viento = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> lugar = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> motor = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación de variable para enviar, presentar y recibir información
    private static LiveData<String> rpm = new LiveData<String>() {
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
            super.observe(owner, observer);
        }
    };

    //Creación del r que permite presentar la información en las actividad principal de flujo, datos y condiciones
    public void datosDataSet(String A, String B, String C, String D, String E, String F, String G, String H, String I, String J, String K, String L, String M, String N, String O) {
        new Thread(new Runnable() {
            public void run() {
                precipitacion = Transformations.map(indice, input -> A);
                inclinacion = Transformations.map(indice, input -> B);
                temperatura = Transformations.map(indice, input -> C);
                velocidad = Transformations.map(indice, input -> D);
                ubicacion = Transformations.map(indice, input -> E);
                vehiculos = Transformations.map(indice, input -> F);
                ocupacion = Transformations.map(indice, input -> G);
                direccion = Transformations.map(indice, input -> H);
                baterias = Transformations.map(indice, input -> I);
                promedio = Transformations.map(indice, input -> J);
                humedad = Transformations.map(indice, input -> K);
                viento = Transformations.map(indice, input -> L);
                lugar = Transformations.map(indice, input -> M);
                motor = Transformations.map(indice, input -> N);
                rpm = Transformations.map(indice, input -> O);
            }
        }).start();
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getRespuestaAgente() {
        return respuestaAgente;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getPrecipitacion() {
        return precipitacion;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getInclinacion() {
        return inclinacion;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getTemperatura() {
        return temperatura;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getVelocidad() {
        return velocidad;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getUbicacion() {
        return ubicacion;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getVehiculos() {
        return vehiculos;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getOcupacion() {
        return ocupacion;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getDireccion() {
        return direccion;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getBaterias() {
        return baterias;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getPromedio() {
        return promedio;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public void datosRespuesta(String RESPUESTA) {
        new Thread(new Runnable() {
            public void run() {
                respuestaAgente = Transformations.map(indice, input -> RESPUESTA);
                //TODO
            }
        }).start();
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getHumedad() {
        return humedad;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getViento() {
        return viento;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getLugar() {
        return lugar;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getMotor() {
        return motor;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public static LiveData<String> getRpm() {
        return rpm;
        //TODO
    }

    //Creación del get que permite obtener la información a presentar
    public void setIndex(int index) {
        indice.setValue(index);
        //TODO
    }
}