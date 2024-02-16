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

//Clases usadas para el uso de fragmentos
//Clases usadas para el mapeo de cadenas
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class PosicionFragmentoPestana extends Fragment {
    public void onCreate(Bundle crearInstancias) {
        //Creación y presentación visual del fragmento en la  actividad principal
        super.onCreate(crearInstancias);
        //TODO
    }

    public static Fragment newInstance(int index) {
        Fragment fragmento = null;
        switch (index) {
            case 1:
                //Escogiendo el fragmento con indice 1
                fragmento = new FragmentosDatos();
                break;
            case 2:
                //Escogiendo el fragmento con indice 2
                fragmento = new FragmentosFlujo();
                break;
            case 3:
                //Escogiendo el fragmento con indice 3
                fragmento = new FragmentosCondiciones();
                break;
        }
        //Retorna el fragmento escogido
        return fragmento;
    }

    public void onDestroyView() {
        //Finaliza la vista del fragmento escogido
        super.onDestroyView();
        //TODO
    }
}