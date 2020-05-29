import java.util.Scanner;
/*import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrIntegerStream;*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;

import java.io.IOException;

import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
/*
package com.journaldev.files
import java.io.BufferReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
*/

public class Assignacions{

    private static Integer n=0; // numero de lineas, ens servira per calcular la frequencia
    private static float frequencia=0.5f;
    private static float confiança=1f;
    private static ArrayList<String> assig = new ArrayList<String>(); // matriu amb les assignacions fetes
    private static ArrayList<Float> confiances = new ArrayList<Float>();


    //setters-------------------------------------------------------------------------

    //estableix el numero de files a num
    public static void setN(Integer num){
        n = num;
    }

    //un setter per cada atribut
    //establir el mapa amb els valors de contingut
    public static void setFrequencia(float f){
        frequencia = f;
    }

    //un setter per cada atribut
    //establir el mapa amb els valors de contingut
    public static void setConfiança(float c){
        confiança = c;
    }

    //un setter per cada atribut
    //establir el mapa amb els valors de contingut
    public static void setAssig(ArrayList<String> a){
        assig = a;
    }

    //getters-------------------------------------------------------------------------

    public static Integer getN(){
        return n;
    }
    public static float getFrequencia(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return frequencia;
    }
    public static float getConfiança(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return confiança;
    }
    public static ArrayList<String> getAssig(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        //a = atribu
        return assig;
    }
    public static ArrayList<Float> getConfiances(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return confiances;
    }


    //operacions------------------------------------------------------------------------

    //Algorisme per calcular les assignacions. Aquesta part només calcula la primera tanda d'atributs.
    public static void algorisme(ArrayList<Boolean[]> taula, ArrayList<String> atribs){

        ArrayList<String> llistaCandidats = new ArrayList<String>();
        ArrayList<Integer> ocurrenciesCandidats = new ArrayList<Integer>();

        //Ens guardem tots els atributs candidats a ser escollits
        //Contem el nombre de vegades que aquests atributs son certs
        for (int i=0; i<taula.size(); i++){
            Boolean[] aux = taula.get(i);
            llistaCandidats.add(atribs.get(i));
            ocurrenciesCandidats.add(0);
            for (int j=0; j<n; j++) {
                if (aux[j]) ocurrenciesCandidats.set(i,ocurrenciesCandidats.get(i)+1);
            }
        } 

        //Seleccionem aquells atributs que compleixen una frequència major o igual a la desitjada
        for (int i=0; i<llistaCandidats.size(); i++){
            if ((float)ocurrenciesCandidats.get(i)/(float)n < frequencia) {
                llistaCandidats.remove(i);
                ocurrenciesCandidats.remove(i);
                taula.remove(i);
                i--;
            }
        }

        //Per cada un dels atributs candidats, comprovem les combinacions que compleixen la frequencia desitjada.
        for (int i=0; i<llistaCandidats.size(); i++){
            System.out.print("Iteracio: ");
            System.out.print(i);
            System.out.print("\n");
            algorisme_i(taula, llistaCandidats, llistaCandidats.get(i), taula.get(i), ocurrenciesCandidats.get(i), i);
        }

        System.out.print("Numero total: ");
        System.out.print(assig.size());
        System.out.print("\n");
    }



    //Immersio de l'algorisme. Permet fer una cerca en arbre per recorrer només aquells casos amb una frequencia desitjable.
    public static void algorisme_i(ArrayList<Boolean[]> taula, ArrayList<String> atribs, String previousAssig, Boolean[] taulaAssig, Integer previousSupport, int del){
        String candidat;
        Integer ocurrenciesCandidat;
        //ArrayList<String> atribsOut = new ArrayList<String>();
        Boolean[] tOut;
        ArrayList<Boolean[]> taulaAux = new ArrayList <Boolean[]>();
        for (int i=0; i<taula.size(); i++){ 
            taulaAux.add(taula.get(i));
        }
        ArrayList<String> atribsAux = new ArrayList <String>();
        for (int i=0; i<atribs.size(); i++){ 
            atribsAux.add(atribs.get(i));
        }
 
        taulaAux.remove(del);
        atribsAux.remove(del);

        for (int i=1; i<taulaAux.size(); i++){
            Boolean[] aux1 = taulaAux.get(i);
            Boolean[] aux2 = taulaAssig;
            candidat = previousAssig + "    " + atribsAux.get(i);
            ocurrenciesCandidat = 0;
            Boolean[] out = new Boolean[n];
            for (int j=0; j<n; j++) {
                if (aux1[j] && aux2[j]){
                    //System.out.print(ocurrenciesCandidats.get(i));
                   ocurrenciesCandidat++;
                   out[j] = true;
                }
                else out[j] = false;
            }
            tOut = out;
            if ((float)ocurrenciesCandidat/(float)n >= frequencia) {
                //atribsOut.add(llistaCandidats.get(i));
                assig.add(candidat);

                float confi = (float)ocurrenciesCandidat/(float)previousSupport;
                confiances.add(confi);
                /*
                System.out.print(candidat);
                System.out.print(" ");
                System.out.print(confi);
                System.out.print("\n");
                */
                algorisme_i(taulaAux, atribsAux, candidat, tOut, ocurrenciesCandidat, i);
            }
        }

    }


}