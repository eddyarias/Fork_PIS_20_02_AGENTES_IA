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

//Clases usadas para la conexión con AWS mediante Amplify y Cognito
//Clases usadas para reconocimiento de elementos en actividades
//Clases usadas para la presentación de información al usuario
//Clases usadas para cambio entre actividades
//Clases usadas para la conexión interclases
//Clases usadas para el uso de fragmentos
//Clases usadas para el mapeo de cadenas
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.pis_20_02.clases.comentarios.ComentariosActivity;
import com.proyecto.pis_20_02.databinding.ActivityPrincipalBinding;
import com.proyecto.pis_20_02.clases.menu.MenuLateralActivity;
import com.proyecto.pis_20_02.clases.mapas.MapasActivity;
import com.proyecto.pis_20_02.base_datos.DataBaseAccess;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.proyecto.pis_20_02.dataset.DataSet;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import com.amplifyframework.core.Amplify;
import java.nio.charset.StandardCharsets;
import android.content.DialogInterface;
import java.io.InputStreamReader;
import com.proyecto.pis_20_02.R;
import android.widget.TextView;
import android.content.Intent;
import java.io.BufferedReader;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.InputStream;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import java.util.Arrays;

@SuppressWarnings({"BusyWait", "unused", "RegExpRedundantEscape"})
public class PrincipalActivity extends AppCompatActivity {
    //Creación del concatenador de los fragmentos dentro de la actividad principal
    //Creación de variables para enviar, presentar y recibir información
    private ActivityPrincipalBinding vinculante;
    TextView nombreApellidoUsuario;
    String apellidoUsuario;
    String nombreUsuario;
    String tipoUsuario;

    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja los clics del elemento de la barra de acción aquí.
        // La barra de acción se maneja automáticamente los clics en el botón Inicio/Arriba, hasta luego
        // a medida que especifica una actividad principal en AndroidManifest.xml.
        int id = item.getItemId();

        //Obteniendo el tipo del usuario que es enviado desde la actividad login al hacer logueo con éxito
        Intent tipo = getIntent();
        tipoUsuario = tipo.getStringExtra("Tipo");
        // Bloqueando elementos dependiendo del tipo de usuario
        if (tipoUsuario.equals(getString(R.string.tipo_0))) {
            if (id == R.id.accion_usuario) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Información de Usuario
                String saludo = getString(R.string.informacion_usuario);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Información de Usuario
                Intent intent = new Intent(PrincipalActivity.this, MenuLateralActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
            if (id == R.id.accion_trafico) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Mapas
                String saludo = getString(R.string.no_permitido);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
            }
            if (id == R.id.accion_correo) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Mapas
                String saludo = getString(R.string.no_permitido);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (id == R.id.accion_usuario) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Información de Usuario
                String saludo = getString(R.string.informacion_usuario);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Información de Usuario
                Intent intent = new Intent(PrincipalActivity.this, MenuLateralActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
            if (id == R.id.accion_trafico) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Mapas
                String saludo = getString(R.string.informacion_trafico);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Mapas
                Intent intent = new Intent(PrincipalActivity.this, MapasActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
            if (id == R.id.accion_correo) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Comentarios
                String saludo = "Envianos tus Comentarios";
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Comentarios
                Intent intent = new Intent(PrincipalActivity.this, ComentariosActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        //Creación y presentación visual de la actividad
        super.onCreate(savedInstanceState);
        vinculante = ActivityPrincipalBinding.inflate(getLayoutInflater());
        //Presentación de los elementos visuales
        setContentView(vinculante.getRoot());

        //Inicializando las variables con los elementos de la actividad
        FloatingActionButton informacionUsuario = vinculante.botonInformacionUsuario;
        FloatingActionButton correoFlotante = vinculante.botonCorreoPrincipal;
        FloatingActionButton mapasFlotante = vinculante.botonMapasPrincipal;

        //Llamando a la clase que Número de Pestañas que le informa a la actividad principal el número de fragmentos con los que se trabaja
        NumeroPestanas crearPaginas = new NumeroPestanas(this, getSupportFragmentManager());
        ViewPager viewPager = vinculante.visorMultiPagina;
        //Vinculando los fragmentos de actividades al fragmento principal
        viewPager.setAdapter(crearPaginas);
        TabLayout paginas = vinculante.botonesPestanas;
        paginas.setupWithViewPager(viewPager);

        //Creando el evento click en un botón tip flotante inicializado
        informacionUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Información de Usuario
                String saludo = getString(R.string.informacion_usuario);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Información de Usuario
                Intent intent = new Intent(PrincipalActivity.this, MenuLateralActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
        });

        //Creando el evento click en un botón tip flotante inicializado
        correoFlotante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Comentarios
                String saludo = "Envianos tus Comentarios";
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Comentarios
                Intent intent = new Intent(PrincipalActivity.this, ComentariosActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
        });

        //Creando el evento click en un botón tip flotante inicializado
        mapasFlotante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Mensaje emergente que informa al usuario que se va a cambiar de actividad a Mapas
                String saludo = getString(R.string.informacion_trafico);
                Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
                //Redirigiendo al usuario a la actividad de Mapas
                Intent intent = new Intent(PrincipalActivity.this, MapasActivity.class);
                //Iniciando la actividad
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Esto agrega elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void leerDataSetDynamoDB() {
        //Segmento de código que permite leer el Dataset con los datos generados por el vehículo
        new Thread(new Runnable() {
            //Para la lectura se genera un hilo secundario, con el fin de estar leyendo el data set continuamente
            public void run() {
                //Se llama al constructor para las variables obtenidas
                FragmentoModeloVista cambio = new FragmentoModeloVista();
                try {
                    // Llamando a una nueva instancia de la base de datos
                    DataBaseAccess consulta = new DataBaseAccess();
                    // Método para obtener la lista de atributos desde la base de datos mediate la clave del UserName
                    String obtenerId = (String) consulta.obtenerId();
                    // Mostrando mensaje de consulta exitosa en consola
                    Log.i("Consulta Exitosa: ", obtenerId);
                    // Llamando al método replaceAll() y al método split() en cadena
                    // Eliminando los corchetes de la cadena de consulta
                    String[] cadena = obtenerId.replaceAll("\\[", "").trim().replaceAll("\\]", "").trim().split(",");
                    // Declarando el inicio del bucle while
                    int i = 0;
                    // Inicializando el bucle while
                    while (i < cadena.length) {
                        try {
                            // Método para obtener la lista de atributos desde la base de datos de los Datos del Vehiculo
                            String datosDataSetDynamoDB = (String) consulta.datosDataSetDynamoDB(i);
                            // Se separa los atributos obtenidos mediante un split
                            String[] token = datosDataSetDynamoDB.replaceAll("\\[", "").trim().replaceAll("\\]", "").trim().split(",");
                            //Mensaje de consola que presenta la información obtenida
                            Log.d("DATASET_LINEA", Arrays.toString(token));
                            // Retornando al hilo principal
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //Enviando la información al servicio REST de AWS
                                    Agente agenteRespuesta = new Agente(getApplicationContext(), "https://18g5d0scba.execute-api.us-east-1.amazonaws.com/FaseBeta/Recursos_Predictor_Accidentes", String.valueOf(Double.parseDouble(token[39].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[3].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[41].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[5].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[6].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[7].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[8].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[17].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[10].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[13].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[14].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[20].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[38].replaceAll(" ", "").trim())));
                                    //Ejecutando el servicio de AWS
                                    agenteRespuesta.execute();
                                    //Enviando la información a presentar en pantalla con los datos del Dataset
                                    cambio.datosDataSet(String.valueOf(Double.parseDouble(token[37].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[39].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[26].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[3].replaceAll(" ", "").trim())), "La: " + Double.parseDouble(token[13].replaceAll(" ", "").trim()) + "\n" + "Lo: " + Double.parseDouble(token[14].replaceAll(" ", "").trim()), String.valueOf(Integer.parseInt(token[38].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[43].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[29].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[8].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[3].replaceAll(" ", "").trim())), String.valueOf(Integer.parseInt(token[30].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[28].replaceAll(" ", "").trim())), token[21].replaceAll(" ", "").trim(), String.valueOf(Integer.parseInt(token[7].replaceAll(" ", "").trim())), String.valueOf(Double.parseDouble(token[41].replaceAll(" ", "").trim())));
                                }
                            });
                            //Se establece un tiempo entre la lectura de cada línea
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            // Mensaje de error al no poder conectarse a la base de datos
                            Log.e("Error de conexión: ", e.getMessage());
                        }
                        i++;
                    }
                } catch (Exception e) {
                    //Mensaje de consola que informa al usuario que n se pudo leer el Dataset
                    Log.e("ERROR DATASET: ", e.toString());
                }
            }
        }).start();
    }

    public void onBackPressed() {
        //Cuadro de diálogo que se presenta al usuario al momento de presionar el botón atrás en el celular
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        //Mensaje pregunta al usuario si desea salir de la aplicación
        constructor.setTitle("ALERTA").setMessage("¿Desea Salir de la App").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Si el usuario acepta, la aplicación cerrar y presentará la pantalla principal del celular
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Descarta el cuadro de diálogo
                dialog.dismiss();
            }
        }).create().show();
    }

    protected void onDestroy() {
        //Finalizando completamente la actividad Principal
        super.onDestroy();
    }

    public void leerDataSet() {
        //Segmento de código que permite leer el Dataset con los datos generados por el vehículo
        new Thread(new Runnable() {
            //Para la lectura se genera un hilo secundario, con el fin de estar leyendo el data set continuamente
            public void run() {
                //Se obtiene los recursos desde la carpeta RAW, donde previamente se cargo el Dataset
                InputStream data = getResources().openRawResource(R.raw.agente);
                //Esta clase lee texto desde un flujo de entrada de caracteres, almacenando los caracteres para proporcionar una lectura eficiente de caracteres, arreglos y líneas.
                BufferedReader lector = new BufferedReader(new InputStreamReader(data, StandardCharsets.UTF_8));
                //Se llama al constructor para las variables obtenidas
                FragmentoModeloVista cambio = new FragmentoModeloVista();
                String linea = "";
                try {
                    //Se va leyendo el Dataset línea por línea
                    lector.readLine();
                    while ((linea = lector.readLine()) != null) {
                        //Se establece un tiempo entre la lectura de cada línea
                        Thread.sleep(1000);
                        //Se establece un carácter que divide la cadena para extraer lo necesitado
                        String[] token = linea.split(",");
                        //Se llama al constructor del Dataset para presentar la información en el formato adecuado
                        DataSet obtenerDatos = new DataSet();
                        //Se envia los datos solicitados por la aplicación
                        obtenerDatos.setDistance_travelled(Double.parseDouble(token[10]));
                        obtenerDatos.setThrottle_position(Double.parseDouble(token[6]));
                        obtenerDatos.setEngine_temperature(Integer.parseInt(token[7]));
                        obtenerDatos.setSteering_angle_(Double.parseDouble(token[39]));
                        obtenerDatos.setAccidents_onsite(Integer.parseInt(token[38]));
                        obtenerDatos.setCurrent_weather(Integer.parseInt(token[20]));
                        obtenerDatos.setSystem_voltage(Double.parseDouble(token[8]));
                        obtenerDatos.setAcceleration(Double.parseDouble(token[5]));
                        obtenerDatos.setLongitude(Double.parseDouble(token[14]));
                        obtenerDatos.setLatitude(Double.parseDouble(token[13]));
                        obtenerDatos.setHeart_rate(Integer.parseInt(token[17]));
                        obtenerDatos.setRpm_(Double.parseDouble(token[41]));
                        obtenerDatos.setSpeed(Integer.parseInt(token[3]));

                        /*obtenerDatos.setDistance_travelled_total(Double.parseDouble(token[11]));
                        obtenerDatos.setSpeed_vs_accidents_onsite(Integer.parseInt(token[43]));
                        obtenerDatos.setSpeed_vs_steering_angle_(Integer.parseInt(token[40]));
                        obtenerDatos.setReal_feel_temperature(Double.parseDouble(token[27]));
                        obtenerDatos.setSpeed_vs_precipitation(Integer.parseInt(token[44]));
                        obtenerDatos.setBarometric_pressure(Double.parseDouble(token[9]));
                        obtenerDatos.setHas_precipitation(Integer.parseInt(token[23]));
                        obtenerDatos.setRelative_humidity(Integer.parseInt(token[30]));
                        obtenerDatos.setSteering_angle(Double.parseDouble(token[2]));
                        obtenerDatos.setPrecipitation(Double.parseDouble(token[37]));
                        obtenerDatos.setWind_direction(Integer.parseInt(token[29]));
                        obtenerDatos.setAccident_rate(Integer.parseInt(token[45]));
                        obtenerDatos.setSpeed_vs_rpm_(Integer.parseInt(token[42]));
                        obtenerDatos.setTemperature(Double.parseDouble(token[26]));
                        obtenerDatos.setWind_speed(Double.parseDouble(token[28]));
                        obtenerDatos.setBody_battery(Integer.parseInt(token[19]));
                        obtenerDatos.setVisibility(Double.parseDouble(token[31]));
                        obtenerDatos.setCloud_cover(Integer.parseInt(token[34]));
                        obtenerDatos.setIs_day_time(Integer.parseInt(token[25]));
                        obtenerDatos.setId_vehicle(Integer.parseInt(token[12]));
                        obtenerDatos.setPressure(Double.parseDouble(token[36]));
                        obtenerDatos.setId_driver(Integer.parseInt(token[16]));
                        obtenerDatos.setHas_precipitation_category(token[22]);
                        obtenerDatos.setUv_index(Integer.parseInt(token[32]));
                        obtenerDatos.setAltitude(Integer.parseInt(token[15]));
                        obtenerDatos.setCeiling(Integer.parseInt(token[35]));
                        obtenerDatos.setCurrent_weather_category(token[21]);
                        obtenerDatos.setStress(Integer.parseInt(token[18]));
                        obtenerDatos.setIndice(Integer.parseInt(token[0]));
                        obtenerDatos.setIs_day_time_category(token[24]);
                        obtenerDatos.setRpm(Integer.parseInt(token[4]));
                        obtenerDatos.setUv_index_text(token[33]);
                        obtenerDatos.setTime(token[1]);*/

                        //Mensaje de consola que presenta la información obtenida
                        Log.d("DATASET_LINEA", String.valueOf(obtenerDatos));
                        // Retornando al hilo principal
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //Enviando la información al servicio REST de AWS
                                Agente agenteRespuesta = new Agente(getApplicationContext(), "https://18g5d0scba.execute-api.us-east-1.amazonaws.com/FaseBeta/Recursos_Predictor_Accidentes", String.valueOf(Double.parseDouble(token[39])), String.valueOf(Integer.parseInt(token[3])), String.valueOf(Double.parseDouble(token[41])), String.valueOf(Double.parseDouble(token[5])), String.valueOf(Double.parseDouble(token[6])), String.valueOf(Integer.parseInt(token[7])), String.valueOf(Double.parseDouble(token[8])), String.valueOf(Integer.parseInt(token[17])), String.valueOf(Double.parseDouble(token[10])), String.valueOf(Double.parseDouble(token[13])), String.valueOf(Double.parseDouble(token[14])), String.valueOf(Integer.parseInt(token[20])), String.valueOf(Integer.parseInt(token[38])));
                                //Ejecutando el servicio de AWS
                                agenteRespuesta.execute();
                                //Enviando la información a presentar en pantalla con los datos del Dataset
                                cambio.datosDataSet(String.valueOf(Double.parseDouble(token[37])), String.valueOf(Double.parseDouble(token[39])), String.valueOf(Double.parseDouble(token[26])), String.valueOf(Integer.parseInt(token[3])), "La: " + Double.parseDouble(token[13]) + "\n" + "Lo: " + Double.parseDouble(token[14]), String.valueOf(Integer.parseInt(token[38])), String.valueOf(Integer.parseInt(token[43])), String.valueOf(Integer.parseInt(token[29])), String.valueOf(Double.parseDouble(token[8])), String.valueOf(Integer.parseInt(token[3])), String.valueOf(Integer.parseInt(token[30])), String.valueOf(Double.parseDouble(token[28])), String.valueOf(token[21]), String.valueOf(Integer.parseInt(token[7])), String.valueOf(Double.parseDouble(token[41])));
                            }
                        });
                    }
                } catch (Exception e) {
                    //Mensaje de consola que informa al usuario que n se pudo leer el Dataset
                    Log.e("ERROR DATASET: ", linea + e);
                }
            }
        }).start();
    }

    public void onResume() {
        super.onResume();
        //Inicializando las variables con los elementos de la actividad
        FloatingActionButton informacionUsuario = vinculante.botonInformacionUsuario;
        FloatingActionButton correoFlotante = vinculante.botonCorreoPrincipal;
        FloatingActionButton mapasFlotante = vinculante.botonMapasPrincipal;
        // Código que permite obtener una serie de datos del usuario
        try {
            // Adquiriendo información del usuario
            nombreApellidoUsuario = findViewById(R.id.txtSingInNombre);
            Intent intent = getIntent();
            //Obteniendo el nombre del usuario que es enviado desde la actividad login al hacer logueo con éxito
            apellidoUsuario = intent.getStringExtra("ApellidoUsuario");
            nombreUsuario = intent.getStringExtra("NombreUsuario");
            tipoUsuario = intent.getStringExtra("Tipo");

            //Seteando el nombre en pantalla
            if (nombreUsuario.equals(getString(R.string.no_ingresado)) || apellidoUsuario.equals(getString(R.string.no_ingresado))) {
                nombreApellidoUsuario.setText(String.format("%s %s %s", getString(R.string.bienvenido), getString(R.string.a), getString(R.string.nombre_app)));
            } else {
                nombreApellidoUsuario.setText(String.format("%s %s %s", getString(R.string.bienvenido), nombreUsuario, apellidoUsuario));
            }

            // Bloqueando elementos dependiendo del tipo de usuario
            if (tipoUsuario.equals(getString(R.string.tipo_0))) {
                informacionUsuario.setEnabled(true);
                correoFlotante.setEnabled(false);
                mapasFlotante.setEnabled(false);
            } else {
                informacionUsuario.setEnabled(true);
                correoFlotante.setEnabled(true);
                mapasFlotante.setEnabled(true);
            }

            //Solicitando a los servicios de AWS el nombre de usuario si el usuario ya había iniciado sesión con anterioridad
            Amplify.Auth.getCurrentUser(result -> PrincipalActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //Mensaje de consola que permite verificar que el nombre de usuario sea correcto
                    Log.i("Información", result.toString());
                    //Seteando el nombre en pantalla
                    if (nombreUsuario.equals(getString(R.string.no_ingresado)) || apellidoUsuario.equals(getString(R.string.no_ingresado))) {
                        nombreApellidoUsuario.setText(String.format("%s %s %s", getString(R.string.bienvenido), getString(R.string.a), getString(R.string.nombre_app)));
                    } else {
                        nombreApellidoUsuario.setText(String.format("%s %s %s", getString(R.string.bienvenido), nombreUsuario, apellidoUsuario));
                    }
                }
            }), error -> PrincipalActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //Mensaje de consola que informa que no se pudo obtener el nombre de usuario
                    Log.e("Error", error.toString());
                }
            }));

            // Método para leer el Dataset
            leerDataSetDynamoDB();
            // leerDataSet();
        } catch (Exception e) {
            //TODO
        }
    }
}