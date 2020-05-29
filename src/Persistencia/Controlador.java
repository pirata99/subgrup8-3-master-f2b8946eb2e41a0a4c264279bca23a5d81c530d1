package Persistencia;

import java.io.IOException;
import java.io.File;
//import java.io.String;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import Domini.Resultats;
import Domini.Regles;
import Domini.Atribut;
import Domini.Rang;
import Domini.Clients;
import Domini.Assignacio;
import Domini.Conjunts;
import Domini.Llista;

public class Controlador {
    private Llista ll;
    private Regles reg;
    private Resultats res;
    private static FileW fw = new FileW();
    private static FileR fr = new FileR();

    //LLegir un CSV nou

    public static Llista llegeixCSV(String nom, String filename) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CloneNotSupportedException {
    //Pre: fileName ha de ser el nom d’un arxiu .csv existent
    //Post: retorna una instancia de llista amb nom nom, amb una taula sense discretitzar, els atributs i tipus.
        String[] atributsAL = fr.inputAtributsCSV("@../../files/"+filename+".csv");
        ArrayList<String[]> taula = fr.inputTaulaCSV("@../../files/"+filename+".csv");
        int numCasos = taula.size();
        Llista l = new Llista(nom);
        l.carregaLlista(taula,atributsAL,numCasos);
        return l;
    }

    //Retorna true si existeix i false si no
    public static Boolean existeixFitxer(String a) {
    //Pre: res
    //Post: si el fitxer existeix retorna true, else retorna false
        File fichero = new File(a);
        if (fichero.exists()) {
            return true;
        }
        return false;
    }

    //Retorna la Llista dels fitxers

    private static String miraTipus(String a) {
    //Pre: a es una arxiu .csv, .resultats, .regles o .llista
    //Post: retorna el tipus de l’arxiu csv, resultats, regles o llista
        if ((a.charAt(a.length()-1)) == 'a') return "llista";
        else if ((a.charAt(a.length()-2)) == 'e') return "regles";
        else if ((a.charAt(a.length()-1)) == 'v') return "csv";
        return "resultats";

    }

    public static ArrayList<String> llistaFitxers(String path, String tipus) {
    //Pre: el path és un path que existeix. Tipus és csv, resultats, regles o llista.
    //Post: retorna una ArrayList de tots els fitxers en el directori path amb extensió tipus.
        File fichero = new File(path);
        String[] pathnames = fichero.list();
        ArrayList<String> nomFitxers = new ArrayList<String>();
        for (String pathname : pathnames) {
            if (miraTipus(pathname) == tipus) {
                String aux = "";
                Character c = pathname.charAt(0);
                int i = 1;
                while (c != '.') {
                    aux += c.toString();
                    c = pathname.charAt(i);
                    i++;
                }
                nomFitxers.add(aux);
            }
        }
        return nomFitxers;
    }

    //Escriu en els Fitxers per guardar les dades que tenim

    /*private static void borraFitxer(String a) {
    //Pre: ha de existir un fitxer amb nom a.
    //Post: si existeix el borra.
        File fichero = new File(a);
        if (fichero.exists()) {
            fichero.delete();
        }
    }*/
    
    //OUTPUTS

    public static void guardaLlista(String nom, Llista ll) throws IOException {
    //Pre: l és una instància inicialitzada
    //Post: Guarda el contingut de l en un fitxer .llista
        
        //String nom = ll.getNomLlista();
        fw.outputfile(ll.getNomLlista(), nom);
        fw.outputfile(String.valueOf(ll.getNumCasos()), nom);
        fw.outputfile(String.valueOf(ll.getDiscretitzat()), nom);
        
        ArrayList<Atribut> atribs = ll.getAtribs();
        fw.outputfile(String.valueOf(atribs.size()), nom);

        for (int i = 0; i < atribs.size(); i++) {
            Atribut tempAt = (Atribut)ll.getAtribs().get(i);
            fw.outputfile(tempAt.getNom(), nom);
            fw.outputfile(tempAt.getTipus(), nom);
            fw.outputfile(String.valueOf(tempAt.getDiscret()), nom);
            fw.outputfile(String.valueOf(tempAt.getShowDefault() ), nom);
            //Casos
            ArrayList<String> casosAt = tempAt.getCasos();
            fw.outputfile(String.valueOf(casosAt.size()), nom);
            for (int j = 0; j < casosAt.size(); j++) {
                fw.outputfile(casosAt.get(j), nom);
            }
            //resta igual
            ArrayList<Rang> atribsDisc = tempAt.getAtribsDisc();
            fw.outputfile(String.valueOf(atribsDisc.size()), nom);
            for (int j = 0; j < atribsDisc.size(); j++) {
                Rang tempRang = atribsDisc.get(j);
                fw.outputfile(tempRang.getNom(), nom);
                ArrayList<Boolean> casos = tempRang.getCasos();
                fw.outputfile(String.valueOf(casos.size()), nom);
                for (int k = 0; k < casos.size(); k++) {
                    fw.outputfile(String.valueOf(casos.get(k)), nom);
                }
            }
        }
    }


    public void outputRegles(Regles r) throws IOException {
    //Pre: r es una instancia inicialitzada
    //Post: Guarda el contingut de r en un fitxer .regles
        reg = r; //AIXO SERA LA LLISTA ACTUAL
        String nom = reg.getNomRegles();
        String contingut = fr.inputArxiuGuardat("@../../Dades/"+nom+".regles");
        fw.outputfile(nom, contingut);
        fw.outputfile(String.valueOf(reg.getFrequencia()), contingut);
        fw.outputfile(String.valueOf(reg.getConfiansa()), contingut);
        
        outputAssignacio(reg.getAssignacions(), contingut);

    }

    public void outputResultats(Resultats r) throws IOException {
    //Pre: r es una instancia inicialitzada
    //Post: Guarda el contingut de r en un fitxer .resultats
        res = r;

        String nom = res.getNomRes();
        String contingut = fr.inputArxiuGuardat("@../../Dades/"+nom+".resultats");
        fw.outputfile(nom, contingut);
        ArrayList<Clients> clients = res.getClients();
        fw.outputfile(String.valueOf(clients.size()), contingut);
        for (int i = 0; i < clients.size(); i++) {
            outputAssignacio(clients.get(i).getAssigs(), contingut);
        }

    }

    private static void outputAssignacio(ArrayList<Assignacio> assignacions, String nom) throws IOException {
    //Pre: nom és el nom d’un fitxer .regles o .resultats
    //Post: Guarda el contingut de assignacions en el fitxer amb nom nom.
        fw.outputfile(String.valueOf(assignacions.size()), nom);
        for (int i = 0; i < assignacions.size(); i++) {
            Assignacio tempAs = assignacions.get(i);
            fw.outputfile(String.valueOf(tempAs.getFreq()), nom);
            fw.outputfile(String.valueOf(tempAs.getConfi()), nom);
            //rang consequent
            Rang tempRang = tempAs.getConsequent();
            fw.outputfile(tempRang.getNom(), nom);
            ArrayList<Boolean> casos = tempRang.getCasos();
            fw.outputfile(String.valueOf(casos.size()), nom);
            for (int k = 0; k < casos.size(); k++) fw.outputfile(String.valueOf(casos.get(k)),nom);
            //Conjunts antecedents
            Conjunts antecedents = tempAs.getAntecedents();
            ArrayList<Rang> rangs = antecedents.getRangs();
            fw.outputfile(String.valueOf(rangs.size()), nom);
            for (int j = 0; j < rangs.size(); j++) {
                tempRang = rangs.get(j);
                fw.outputfile(tempRang.getNom(), nom);
                casos = tempRang.getCasos();
                fw.outputfile(String.valueOf(casos.size()), nom);
                for (int k = 0; k < casos.size(); k++) {fw.outputfile(String.valueOf(casos.get(k)),nom);}
            }
            casos = antecedents.getCasos();
            fw.outputfile(String.valueOf(casos.size()), nom);
            for (int k = 0; k < casos.size(); k++) fw.outputfile(String.valueOf(casos.get(k)),nom);
        }
    }

    //INPUTS
    
    public Llista llegeixLlista(String filename) throws IOException {
    //Pre: existeix un fitxer .llista amb nom fileName
    //Post: retorna una instància Llista amb el contingut del fitxer
        String contingut = fr.inputArxiuGuardat("@../../Dades/"+filename+".llista");

        String[] lines = contingut.split("\n");
        int nl = 0; //nextLine
        //nse si sha de inciar un buit o algo si
        ll = new Llista(lines[nl++]);
        //ll.setNomLlista(lines[nl++]);
        ll.setNumCasos(Integer.valueOf(lines[nl++]));
        ll.setDiscretitzat(Boolean.valueOf(lines[nl++]));
        Integer Atsize = Integer.valueOf(lines[nl++]);
        ArrayList<Atribut> atribs = new ArrayList<Atribut>();
        for (int i = 0; i < Atsize; i++) {
            Atribut A = new Domini.Atribut(lines[nl++],lines[nl++]);
            A.setDiscret(Boolean.valueOf(lines[nl++]));
            A.setShowDefault(Boolean.valueOf(lines[nl++]));
            ArrayList<String> casosAt = new ArrayList<String>();
            Integer casosAtsize = Integer.valueOf(lines[nl++]);
            for (int j = 0; j < casosAtsize; j++) {
                casosAt.add(lines[nl++]);
            }
            A.setCasos(casosAt);
            ArrayList<Rang> atribsDisc = new ArrayList<Rang>();
            int atribsDiscsize = Integer.valueOf(lines[nl++]);
            for (int j = 0; j < atribsDiscsize; j++) {
                Rang tempRang = new Domini.Rang(lines[nl++]);
                ArrayList<Boolean> casos = new ArrayList<Boolean>();
                int casossize = Integer.valueOf(lines[nl++]);
                for (int k = 0; k < casossize; k++) casos.add(Boolean.valueOf(lines[nl++]));
                tempRang.setCasos(casos);
                atribsDisc.add(tempRang);
            }
            for (int j = 0; j < atribsDiscsize; j++) {
            }
            A.setAtribsDisc(atribsDisc);
            atribs.add(A);
        }
        ll.setAtribs(atribs);
        return ll;
    }
    
    
    public Regles inputRegles(String filename) throws IOException {
    //Pre: existeix un fitxer .regles amb nom fileName
    //Post: retorna una instancia Regles amb el contingut del fitxer
        String contingut = fr.inputArxiuGuardat("@../../Dades/"+filename+".regles");
        String[] lines = contingut.split("\n");
        int nl = 0; //nextLine
        reg = new Regles(lines[nl++]);
        reg.setFrequencia(Double.valueOf(lines[nl++]));
        reg.setConfiansa(Double.valueOf(lines[nl++]));
        ArrayList<Assignacio> a = inputAssignacio(lines, nl);
        reg.setAssignacions(a);
        return reg;
    }

    public Resultats inputResultats(String filename) throws IOException {
    //Pre: existeix un fitxer .resultats amb nom fileName
    //Post: retorna una instancia Resultats amb el contingut del fitxer
        String contingut = fr.inputArxiuGuardat("@../../Dades/"+filename+".resultats");
        String[] lines = contingut.split("\n");
        int nl = 0; //nextLine
        res = new Resultats(lines[nl++]);
        ArrayList<Clients> clients = new ArrayList<Clients>();
        Integer clientssize = Integer.valueOf(lines[nl++]);
        for (int i = 0; i < clientssize; i++) {
            Clients c = new Domini.Clients();
            c.setAssigs(inputAssignacio(lines, nl)); //nl ha de passar per referencia
            clients.add(c);
        }
        res.setClients(clients);
        return res;
    }


    private ArrayList<Assignacio> inputAssignacio(String[] lines, int nl) throws IOException {
    //Pre: existeix un fitxer .regles o .resultats amb nom fileName
    //Post: retorna una ArrayListAssignacio amb els continguts del fitxer        
        ArrayList<Assignacio> assignacions = new ArrayList<Assignacio>();
        Integer assignacionssize = Integer.valueOf(lines[nl++]);
        for (int i = 0; i < assignacionssize; i++) {
            Assignacio A = new Domini.Assignacio();
            A.setFreq(Double.valueOf(lines[nl++]));
            A.setConfi(Double.valueOf(lines[nl++]));
            //rangs consequent
            Rang consequent = new Domini.Rang(lines[nl++]);
            ArrayList<Boolean> casos = new ArrayList<Boolean>();
            Integer casossize = Integer.valueOf(lines[nl++]);
            for (int k = 0; k < casossize; k++) casos.add(Boolean.valueOf(lines[nl++]));
            consequent.setCasos(casos);
            A.setConsequent(consequent);
            //Conjunts antecedents
            Conjunts antecedents = new Domini.Conjunts();
            //rangs
            ArrayList<Rang> rangs = new ArrayList<Rang>();
            Integer rangssize = Integer.valueOf(lines[nl++]);
            for (int j = 0; j < rangssize; j++) {
                Rang tempRang = new Domini.Rang(lines[nl++]);
                ArrayList<Boolean> casos2 = new ArrayList<Boolean>();
                casossize = Integer.valueOf(lines[nl++]);
                for (int k = 0; k < casossize; k++) casos2.add(Boolean.valueOf(lines[nl++]));
                tempRang.setCasos(casos2);
                rangs.add(tempRang);
            }
            antecedents.setRangs(rangs);
            ArrayList<Boolean> casos3 = new ArrayList<Boolean>();
            casossize = Integer.valueOf(lines[nl++]);
            for (int k = 0; k < casossize; k++) casos3.add(Boolean.valueOf(lines[nl++]));
            antecedents.setCasos(casos3);
            A.setAntecedents(antecedents);
            assignacions.add(A);
        }
        return assignacions;
    }

}


