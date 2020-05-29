//import java.util.*;
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
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;
import java.util.Arrays;
import java.util.ArrayList;


import java.io.IOException;

public class Llista{

    private static int atribs = 0;
    private static int atribsDisc = 0;
    private static int casos = 0;
    private static String nomLlista = "";
    private static Boolean discret = false;
    public static ArrayList <String> tipus = new ArrayList <String>();  //vector de tipus de dades
    public static ArrayList <String> atributs = new ArrayList <String>(); // vector dels noms dels atributs
    public static ArrayList <String[]> taula = new ArrayList <String[]>(); // matriu continguts del fitxer
    public static ArrayList <String> atributsDiscrets = new ArrayList <String>(); // noms dels atributs de la taula de booleans un cop discretitzats
    public static ArrayList <Boolean[]> taulaComp = new ArrayList <Boolean[]>(); // matriu de booleans per l'algorisme




    /*
    Llista(){
        atribs = 0;
        atribsDisc = 0;
        casos = 0;
        nomLlista = "";
        discret = false;
        tipus = new ArrayList <String>();  //vector de tipus de dades
        atributs = new ArrayList <String>(); // vector dels noms dels atributs
        taula = new ArrayList <String[]>(); // matriu continguts del fitxer
        atributsDiscrets = new ArrayList <String>(); // noms dels atributs de la taula de booleans un cop discretitzats
        taulaComp = new ArrayList <Boolean[]>();

        System.out.print("\nM'he creat\n\n");

    }
    */

    //Carrega la llista de casos des del fitxer amb el path indicat i els guarda.
    public static void carregaLlista(String fileName) throws IOException{
        //fileName = "iris.csv";
        /*
        int size = fileName.length();
        Boolean dot = false;
        for (int i = 0; i<size; i++){
            if (fileName[i] == "."){
                fileName[i+1] = 'c';
                fileName[i+2] = 's';
                fileName[i+3] = 'v';
                dot = true;
                i+=4;
            }
            if (dot) fileName[i] = '\0';
        }*/

        //llegir la llista del fitxer
        //carregar els valors

        //Aixo anira al main
        /*
        System.out.print("Introdueix el nom del arxiu\n ");
        Scanner llegit = new Scanner(System.in);
        String pathArx = llegit.next();
        */

        String line ="";
        //atribs = 0;
        //per algo
        
        //ArrayList<String[]> padre=new ArrayList<String[]>(0);
        //String[] atributs = new String[0];
        String[] atributsAL = new String[0];
       
        BufferedReader br2 = new BufferedReader(new FileReader(fileName));
        //primera LINEA
        if ((line = br2.readLine()) != null) {
            atributsAL = line.split(",");
            atribs = atributsAL.length; 
        }

        System.out.print("Hi ha ");
        System.out.print(atribs);
        System.out.print(" atributs. \n");

        //DEFINIR ATRIBUTS I ELS SEUS TIPUS
        //String[] tipus = new String[atribs];
        

        
        while ((line = br2.readLine()) != null) {
            // use comma as separator
            String[] country = line.split(",");
            taula.add(country);
            casos++;
        }
        System.out.print("S'han llegit ");
        System.out.print(taula.size());
        System.out.print(" lineas del fitxer.\n");

        br2.close();

        //tipus = new String[atribs];
        System.out.print("Atributs abans de trobaTipus: ");
        System.out.print(atribs);
        System.out.print("\n");

        swap(trobaTipus(atributsAL), atributsAL);
    }

    private static String[] trobaTipus(String[] atributsAL){


        System.out.print("Atributs al principi de trobaTipus: ");
        System.out.print(atribs);
        System.out.print("\n");

        String[] tipusAL = new String[atribs];
        //per mirar quins falten i tornaru a fer
        ArrayList<Integer> faltatipus=new ArrayList<>();
        //funcio que ens diu els tipus
        String[] tx = taula.get(0);
        
        for (int i = 0; i < atribs; i++) {
            String temp = tx[i];
            if (temp.equals("Null") || temp.equals("NULL")) {
                faltatipus.add(i);
                //tipus[i] = "mirabo";
            }
            else if (esLletra(temp.charAt(0))) {
                tipusAL[i] = "String";
            }
            else if ((temp.equals("0") && esBolea(atributsAL[i])) || (temp.equals("0") && esBolea(atributsAL[i]))) {
                tipusAL[i] = "Boolean";
                //if (atributs[i].charAt(0) == 'i') tipus[i] = "Boolean";
            }
            else {
                boolean dec = false;
                boolean data = false;
                for (int j = 1; j < temp.length(); j++) {
                    if (temp.charAt(j) == '.') dec = true;
                    else if (temp.charAt(j) == '-') data = true;
                }
                if (data)  tipusAL[i] = "Date";
                else if (dec) tipusAL[i] = "Double";
                else tipusAL[i] = "Double";
            }
            
        }


        int k = 1;
        tx = taula.get(k);
        boolean acaba = true;
        while (!faltatipus.isEmpty() && acaba) {
            for (int i = 0; i < faltatipus.size(); i++) {
                int mark = faltatipus.get(i);
                String temp = tx[mark];
                if (temp.equals("Null") || temp.equals("NULL")) {
                    //res, seguim
                }
                else if (esLletra(temp.charAt(0))) {
                    tipusAL[mark] = "String";
                    faltatipus.remove(i);
                }
                else if ((temp.equals("0") && esBolea(atributsAL[mark])) || (temp.equals("1") && esBolea(atributsAL[mark]))) {
                    tipusAL[mark] = "Boolean";
                    faltatipus.remove(i);
                }
                else {
                    boolean dec = false;
                    boolean data = false;
                    for (int j = 1; j < temp.length(); j++) {
                        if (temp.charAt(j) == '.') dec = true;
                        else if (temp.charAt(j) == '-') data = true;
                    }
                    if (data)  tipusAL[mark] = "Date";
                    else if (dec) tipusAL[mark] = "Double";
                    else tipusAL[mark] = "Double";
                    faltatipus.remove(i);
                }
                
            }
            // prova System.out.print("volta ");
            k++;
            if (k < taula.size()) tx = taula.get(k);
            //else faltatipus.clear();
            else acaba = false;
        }

        for(int j = 0; j < faltatipus.size(); j++) {
            tipusAL[faltatipus.get(j)] = "Null";
        }

        faltatipus.clear();
        return tipusAL;
    }

    private static void swap(String[] tipusAL, String[] atributsAL){

        ArrayList<String[]> swap = new ArrayList<String[]>(0);

        for (int i = 0; i < atribs; i++) {
            //String[] temp = padre.get(i);
            String[] tSwap = new String[casos];
            for(int j = 0; j < casos; j++) {
                String[] temp = taula.get(j);
                tSwap[j] = temp[i];
                
            }
            swap.add(tSwap);
        }

        taula = swap;

        //cambia arrays per AList
        for (int i = 0; i < atribs; i++) {

            System.out.print("Iteracio: ");
            System.out.print(i);
            System.out.print("\n");
            tipus.add(tipusAL[i]);
            atributs.add(atributsAL[i]);
        }

        System.out.print("Hi ha ");
        System.out.print(atributs.size());
        System.out.print(" atributs. \n");
    }

    public static void discretitzaValNum(String rang1, String rang2, Double tall, int pos){
        String[] valors = taula.get(pos);
        Boolean[] r1 = ompleFals();
        Boolean[] r2 = ompleFals();
        for (int i=0; i<casos; i++){
            if (valors[i].equals("NULL") || valors[i].equals("null") || valors[i].equals("Null")){
            }
            else if (Double.parseDouble(valors[i]) < tall){
                r1[i] = true;
                r2[i] = false;
            }
            else {
                r1[i] = false;
                r2[i] = true;
            }
        }
        taulaComp.add(r1);
        atributsDiscrets.add(rang1);
        taulaComp.add(r2);
        atributsDiscrets.add(rang2);
        atribsDisc+=2;
    }

    public static void discretitzaValBool(String nomCert, String nomFals, int pos){
        String[] valors = taula.get(pos);
        Boolean[] r1 = ompleFals();
        Boolean[] r2 = ompleFals();
        for (int i=0; i<casos; i++){
            if (valors[i] == "0"){
                r1[i] = false;
                r2[i] = true;
            }
            else if(valors[i] == "1"){
                r1[i] = true;
                r2[i] = false;
            }
            else {
                r1[i] = false;
                r2[i] = false;
            }

        }
        taulaComp.add(r1);
        atributsDiscrets.add(nomCert);
        taulaComp.add(r2);
        atributsDiscrets.add(nomFals);
        atribsDisc+=2;
    }

    public static void discretitzaString(int pos){
        String nomAtrib = atributs.get(pos);
        String[] valors = taula.get(pos);
        ArrayList<String> namesFound = new ArrayList<String>();
        ArrayList<Boolean[]> valuesFound = new ArrayList<Boolean[]>();

        for (int i=0; i<casos; i++){
            //int aux = findName(namesFound,valors[i]);
            if (nomAtrib.equals("NULL") || nomAtrib.equals("null") || nomAtrib.equals("Null")){
            }
            else if (!namesFound.contains(nomAtrib+"_"+valors[i])){
                namesFound.add(nomAtrib+"_"+valors[i]);
                Boolean[] b = ompleFals();
                b[i] = true;
                valuesFound.add(b);
            }
            else {
                int aux = namesFound.indexOf(nomAtrib+"_"+valors[i]);
                Boolean[] b = valuesFound.get(aux);
                b[i] = true;
                valuesFound.set(aux, b);
            }

        }
        for (int i=0; i<namesFound.size(); i++){
            atributsDiscrets.add(namesFound.get(i));
            taulaComp.add(valuesFound.get(i));
            atribsDisc++;
        }
        
    }

    public static void discretitzaData(String rang1, String rang2, String data, int pos){
        String dataAtrib = atributs.get(pos);
        String[] valors = taula.get(pos);
        Boolean[] r1 = ompleFals();
        Boolean[] r2 = ompleFals();
        for (int i=0; i<casos; i++){
            if (valors[i].equals("NULL") || valors[i].equals("null") || valors[i].equals("Null")){
            }
            else if (anyDe(valors[i])<anyDe(data) || (anyDe(valors[i])==anyDe(data) && mesDe(valors[i])<mesDe(data)) || (anyDe(valors[i])==anyDe(data) && mesDe(valors[i])==mesDe(data) && diaDe(valors[i])<diaDe(data))){
                r1[i] = true;
                r2[i] = false;
            }
            else {
                r1[i] = false;
                r2[i] = true;
            }
        }
        taulaComp.add(r1);
        atributsDiscrets.add(rang1);
        taulaComp.add(r2);
        atributsDiscrets.add(rang2);
        atribsDisc+=2;
    }
    /*
    public static void discretitza(){

        System.out.print("Introdueix el valor de tall dels seguents atributs numerics\n ");

        //int double
        ArrayList<Integer> posDiscret=new ArrayList<>();
        ArrayList<Double> valDiscret=new ArrayList<>();
        ArrayList<Character> mesmenysDiscret=new ArrayList<>();
        //data
        ArrayList<String> datavalDiscret=new ArrayList<>();
        ArrayList<Character> mesmenysData = new ArrayList<>();
        ArrayList<Integer> posdataDiscret=new ArrayList<>();


        for (int i = 0; i < llargada; i++) {
            if (tipus[i].equals("Double")) {
                //tipus[i] = "Boolean";
                System.out.print(atributs[i]);
                System.out.print(" ");
                llegit = new Scanner(System.in);
                String valortemp = llegit.next();
                double pls = Double.parseDouble(valortemp);

                System.out.print("A munt 'a' o abaix 'b'? ");
                llegit = new Scanner(System.in);
                Character ak47= llegit.next().charAt(0);
                mesmenysDiscret.add(ak47);
                //double pls = new Double(valortemp)
                valDiscret.add(pls);
                posDiscret.add(i);
            }
            if (tipus[i].equals("Date")) {
                System.out.print(atributs[i]);
                System.out.print(" introduiex de la forma XXXX-XX-XX (any-mes-dia) ");
                llegit = new Scanner(System.in);
                String datatemp = llegit.next();

                System.out.print("A munt 'a' o abaix 'b'? ");
                llegit = new Scanner(System.in);
                Character ak47= llegit.next().charAt(0);

                posdataDiscret.add(i);
                datavalDiscret.add(datatemp);
                mesmenysData.add(ak47);
            }
        }
        System.out.print("\n");


        
        //---INTS I DOUBLES
        for(int i = 0; i < taula.size(); i++) {
            String[] temp = taula.get(i);
            for(int j = 0; j < posDiscret.size(); j++) {
                if (temp[posDiscret.get(j)].equals("Null") || temp[posDiscret.get(j)].equals("NULL") || temp[posDiscret.get(j)].equals("null")) {}
                else {
                    tipus[posDiscret.get(j)] = "Boolean";
                    double comp = Double.parseDouble(temp[posDiscret.get(j)]);
                    //double comp = 3.3;
                    if (temp[posDiscret.get(j)].equals("Null")) {}
                    else if (comp >= valDiscret.get(j) && mesmenysDiscret.get(j).equals('a')  ||
                                comp < valDiscret.get(j) && mesmenysDiscret.get(j).equals('b')) temp[posDiscret.get(j)] = "1";
                    else temp[posDiscret.get(j)] = "0";
                }
            }
            taula.set(i, temp);
        }

        //---DATES

        for(int i = 0; i < taula.size(); i++) {
            String[] temp = taula.get(i);
            for(int j = 0; j < posdataDiscret.size(); j++) {
                    if (temp[posdataDiscret.get(j)].equals("Null") || temp[posdataDiscret.get(j)].equals("NULL") || temp[posdataDiscret.get(j)].equals("null")) {}
                    else {
                        tipus[posdataDiscret.get(j)] = "Boolean";
                        String data = temp[posdataDiscret.get(j)];

                        int diaT = diaDe(data);
                        int anyT = anyDe(data);
                        int mesT = mesDe(data);

                        
                        int diaC = diaDe(datavalDiscret.get(j));
                        int mesC = mesDe(datavalDiscret.get(j));
                        int anyC = anyDe(datavalDiscret.get(j));

                        if (mesmenysData.get(j).equals('a'))  {
                            if (anyC < anyT) temp[posdataDiscret.get(j)] = "1";
                            else if (anyC == anyT && mesC < mesT) temp[posdataDiscret.get(j)] = "1";
                            else if (anyC == anyT && mesC == mesT && diaC <= diaT) temp[posdataDiscret.get(j)] = "1";
                            else temp[posdataDiscret.get(j)] = "0";
                        }
                        else {
                            if (anyC < anyT) temp[posdataDiscret.get(j)] = "0";
                            else if (anyC == anyT && mesC < mesT) temp[posdataDiscret.get(j)] = "0";
                            else if (anyC == anyT && mesC == mesT && diaC <= diaT) temp[posdataDiscret.get(j)] = "0";
                            else temp[posdataDiscret.get(j)] = "1";
                        }
                    }        
                
            }
            
            taula.set(i, temp);
        }
    }
    */

    //setters
    public static void setAtribs(int n){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        atribs = n;
    }
    public static void setAtribsDisc(int n){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        atribsDisc = n;
    }
    public static void setCasos(int m){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        casos = m;
    }
    public static void setNomLlista(String s){

        nomLlista = s;
        System.out.print("\nEl nom de la llista ara es ");
        System.out.print(nomLlista);
        System.out.print("\n\n");
    }
    public static void setDiscret(Boolean b){

        discret = b;
    }
    public static void setTipus(ArrayList <String> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        tipus = contingut;
    }
    public static void setAtributs(ArrayList <String> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        atributs = contingut;
    }
    public static void setTaula( ArrayList <String[]> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        taula = contingut;
    }
    public static void setAtributsDiscrets(ArrayList <String> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        atributsDiscrets = contingut;
    }
    public static void setTaulaComp(ArrayList <Boolean[]> contingut){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        taulaComp = contingut;
    }


    //getters
    public static int getAtribs(){ // un getter per cada atribut
        return atribs;
    }
    public static int getAtribsDisc(){ // un getter per cada atribut
        return atribsDisc;
    }
    public static int getCasos(){ // un getter per cada atribut
        return casos;
    }
    public static String getNomLlista(){
        return nomLlista;
    }
    public static Boolean getDiscret(){
        return discret;
    }
    public static ArrayList <String> getTipus(){ // un getter per cada atribut
        return tipus;
    }
    public static ArrayList <String> getAtributs(){ // un getter per cada atribut
        return atributs;
    }
    public static ArrayList <String[]> getTaula(){ // un getter per cada atribut
        return taula;
    }
    public static ArrayList <String> getAtributsDiscrets(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return atributsDiscrets;
    }
    public static ArrayList <Boolean[]> getTaulaComp(){ // un getter per cada atribut
        return taulaComp;
    }


    //operacions
    public static String consultaValor(int fila, String atribut){
        //Pre: l'atribut donat es troba a la llista d'atributs i la fila correspon a un dels casos de l'atribut.
        //Post: retorna el valor guardat a la posicio fila per l'atribut concret    
        for (int i=0; i < atributs.size(); i++){
            if (atributs.get(i) == atribut) {
                return taula.get(i)[fila];
            }
        }
        return "";
    }
    public static void modificaValor(int fila, String atribut, String valor){
        //Pre: l'atribut donat es troba a la llista d'atributs i la fila correspon a un dels casos de l'atribut.
        //Post: asigna valor a l'atribut concret de la fila indicada
        for (int i=0; i < atributs.size(); i++){
            if (atributs.get(i) == atribut) {
                taula.get(i)[fila] = valor;
                i = atributs.size();
            }
        }
    }
    public static void eliminaAtribut(String atrib){
        //Post: elimina un atribut de la taula
        for (int i=0; i < atribs; i++){
            if (atributs.get(i) == atrib) {
                taula.remove(i);
                atributs.remove(i);
                atribs = atributs.size();
                i = atribs;
            }
        }
    }
    public static void afegeixAtribut(String atrib, String valor){
        //Pre: -
        //Post: afegeix un atribut a la taula i a la llista d'atributs amb tots els casos inicialitzats al valor indicat.
        atributs.add(atrib);
        atribs++;
        String[] aux = new String[casos];
        for (int i=0; i<casos; i++){
            aux[i] = valor;
        }
        taula.add(aux);
    }
    /*
    public static void booleanitza(){
        //converteix el taula en taulaComp, és a dir la taula de valors en una taula booleana
        //s'haura d'executar cada cop que es modifica algun valor
    }
    */

    static private boolean esLletra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    static private boolean esBolea(String s) {
        if (s.charAt(0) == 'i' && s.charAt(1) == 's' && s.charAt(2) == '_') return true;
        return false; 
    }

    static private int diaDe(String s) {
        //XXXX-XX-XX
        String aux = "";
        if (s.length() < 8) {
            System.out.print(s);
            System.out.print(" ");
        }
        aux += s.charAt(8);
        aux += s.charAt(9);
        int dia = Integer.parseInt(aux); 
        return dia;
    }

    static private int anyDe(String s) {
        //XXXX-XX-XX
        String aux = "";
        aux += s.charAt(0);
        aux += s.charAt(1);
        aux += s.charAt(2);
        aux += s.charAt(3);
        int any = Integer.parseInt(aux); 
        return any;
    }
    
    static private int mesDe(String s) {
        //XXXX-XX-XX
        String aux = "";
        aux += s.charAt(5);
        aux += s.charAt(6);
        int mes = Integer.parseInt(aux); 
        return mes;
    }

    private static Boolean[] ompleFals(){
        Boolean[] aux = new Boolean[casos];
        for (int i=0; i< casos; i++) aux[i] = false;
        return aux;
    }

    private static int findName(ArrayList<String> nomsT, String nom){
        for (int i = 0; i<nomsT.size(); i++){
            if (nomsT.get(i) == nom) return i;
        }
        return -1;
    }

}