import java.util.*;
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

public class Resultats{
    private static int n = 0;
    private static ArrayList <String[]> results = new ArrayList<String[]>(); // matriu amb les assignacions fetes
    private static ArrayList <Float[]> confis = new ArrayList<Float[]>();
    private static ArrayList <Boolean> casos = new ArrayList<Boolean>();

    //algorisme
    public static void comprovacio(ArrayList<String[]> regles, ArrayList<Boolean[]> taula, ArrayList<String> atribs){
        // agafa les assignacions trobades i les aplica al exemple donat
        // guarda el resultat a results

    }


    //Setters

    public static void setN(int num){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        n = num;
    }
    public static void setResults(ArrayList<String[]> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut  
        results = contingut;
    }
    public static void setConfis(ArrayList<Float[]> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut  
        confis = contingut;
    }
    public static void setCasos(ArrayList<Boolean> contingut){
        //Pre:
        //Post:
        casos = contingut;
    }

    //getters
    public static int getN(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut 
        return n;
    }
    public static ArrayList<String[]> getResults(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return results;
    }
    public static ArrayList<Float[]> getConfis(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return confis;
    }
    public static ArrayList<Boolean> getCasos(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return casos;
    }

    //operacions

    //public static void calcula 


}