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

//Clases usadas para reconocimiento de elementos en actividades
//Clases usadas para la presentación de información al usuario
//Clases usadas para cambio entre actividades
//Clases usadas para la conexión interclases
//Clases usadas para el uso de fragmentos
//Clases usadas para el mapeo de cadenas
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import com.proyecto.pis_20_02.R;
import android.content.Context;

@SuppressWarnings({"deprecation", "unused", "NullableProblems"})
public class NumeroPestanas extends FragmentPagerAdapter {
    //Variable que permite nombrar cada pestaña en la actividad principal
    private static final int[] nombrePestana = new int[]{R.string.tab_datos, R.string.tab_condiciones, R.string.tab_flujo};
    //Variable que permite obtener el fragmento de actividad que se presenta actualmente
    private final Context contexto;

    public NumeroPestanas(Context recibirContexto, FragmentManager esteFragmento) {
        //Método que permite establecer y recibir el fragmento de actividad y presentarlo en la actividad principal
        super(esteFragmento);
        contexto = recibirContexto;
    }

    public CharSequence getPageTitle(int posicion) {
        //Método que permite obtener el nombre del fragmento
        return contexto.getResources().getString(nombrePestana[posicion]);
    }

    public Fragment getItem(int posicionPestana) {
        //Método que permite obtener el indice del fragmento actual
        return PosicionFragmentoPestana.newInstance(posicionPestana + 1);
    }

    public int getCount() {
        //Número de fragmentos en la actividad principal
        return 3;
        //TODO
    }
}