
package Domini;

//import javax.security.auth.login.AccountExpiredException;
//import java.lang.reflect.Array;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;
//import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;


import java.io.IOException;


public class Llista{

	private String nomLlista;
    private Boolean discretitzat;
	private ArrayList<Atribut> atribs;
    private int numCasos;



    //creadores
    public Llista(String nom){
    	nomLlista = nom;
    	atribs = new ArrayList<Atribut>();
    	numCasos = 0;
    	discretitzat = false;
    }
    public Llista(String nom, ArrayList<Atribut> atr){
    	nomLlista = nom;
    	atribs = atr;
        numCasos = atr.get(0).numCasos();
    	discretitzat = false;
    }
    public Llista(String nom, ArrayList<Atribut> atr, Boolean disc){
        nomLlista = nom;
        atribs = atr;
        numCasos = atr.get(0).numCasos();
        discretitzat = disc;
    }


    //Carrega la llista de casos des del fitxer amb el path indicat i els guarda.
    //Aquestes dos haurien d'anar a la capa de persistència
    /*public static void carregaLlista(String fileName) throws IOException{

        String line ="";
        int numAtribs=0;
        ArrayList<String[]> taula = new ArrayList<>();

        String[] atributsAL = new String[0];
       
        BufferedReader br2 = new BufferedReader(new FileReader(fileName));

        if ((line = br2.readLine()) != null) {
            atributsAL = line.split(",");
            numAtribs = atributsAL.length;
        }
        
        while ((line = br2.readLine()) != null) {
            // use comma as separator
            String[] country = line.split(",");
            taula.add(country);
            numCasos++;
        }

        br2.close();

        String[] tipus = trobaTipus(atributsAL, taula);

        for (int i=0; i<numAtribs; i++){
            Atribut nou = new Atribut(atributsAL[i], tipus[i]);
            atribs.add(nou);
        }
        for(int i=0; i<taula.size(); i++){
            for (int j=0; j<taula.get(i).length; j++){
                atribs.get(j).addCasos(taula.get(i)[j]);
            }
        }

    }*/

    public void carregaLlista(ArrayList<String[]> t, String[] atAL, int nC) throws IOException{
        numCasos = nC;
        String[] atributsAL = atAL;
        ArrayList<String[]> taula = t;

        String[] tipus = trobaTipus(atributsAL, taula);
        for (int i=0; i<atributsAL.length; i++){
            Atribut nou = new Atribut(atributsAL[i], tipus[i]);
            atribs.add(nou);
        }
        for(int i=0; i<taula.size(); i++){
            String[] aux = taula.get(i);
            for (int j=0; j<taula.get(i).length; j++){
                atribs.get(j).addCasos(aux[j]);
            }
        }
        for (int i=0; i<atribs.size(); i++){
            System.out.print("tipus: "+atribs.get(i).getTipus()+"\n");
            if (atribs.get(i).getTipus() == "String") atribs.get(i).discretitzaString();
            else if (atribs.get(i).getTipus() == "Boolean") atribs.get(i).discretitzaValBool();
        }
    }

    private String[] trobaTipus(String[] atributsAL, ArrayList<String[]> taula){

        int atribs = atributsAL.length;
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
            else if ((temp.equals("0") && esBolea(atributsAL[i])) || (temp.equals("1") && esBolea(atributsAL[i]))) {
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
    public void setNomLlista(String s){

        nomLlista = s;
        System.out.print("\nEl nom de la llista ara es ");
        System.out.print(nomLlista);
        System.out.print("\n\n");
    }
    public void setAtribs(ArrayList<Atribut> atr){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        atribs = atr;
    }
    public void setNumCasos(int m){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        numCasos = m;
    }
    public void setDiscretitzat(Boolean b){

        discretitzat = b;
    }

    //getters
    public String getNomLlista(){
        return nomLlista;
    }
    public ArrayList<Atribut> getAtribs(){ // un getter per cada atribut
        return atribs;
    }
    public int getNumCasos(){ // un getter per cada atribut
        return numCasos;
    }
    public Boolean getDiscretitzat(){
        return discretitzat;
    }

    //operacions
    private void Discretitza(Atribut a, String nom, String rang1, String rang2){
        if (a.getTipus() == "String"){

        }
    }

    /*
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
    }*/
    /*
    public static void booleanitza(){
        //converteix el taula en taulaComp, és a dir la taula de valors en una taula booleana
        //s'haura d'executar cada cop que es modifica algun valor
    }
    */
    private boolean esLletra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    private boolean esBolea(String s) {
        if (s.charAt(0) == 'i' && s.charAt(1) == 's' && s.charAt(2) == '_') return true;
        return false; 
    }
    /*
    private static int findName(ArrayList<String> nomsT, String nom){
        for (int i = 0; i<nomsT.size(); i++){
            if (nomsT.get(i) == nom) return i;
        }
        return -1;
    }*/

}