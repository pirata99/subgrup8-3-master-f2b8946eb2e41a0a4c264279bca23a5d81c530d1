
package Domini;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Double.valueOf;

public class Regles{

    private String nomRegles;
    private ArrayList<Assignacio> assignacions;
    private Double frequencia;
    private Double confiansa;

    //creadores
    Regles(String nomReglesA){
        nomRegles = nomReglesA;
        assignacions = new ArrayList<>();
        frequencia = 0.0;
        confiansa = 0.0;
    }
    Regles(String nomReglesA, ArrayList<Assignacio> assigs){
        nomRegles = nomReglesA;
        assignacions = assigs;
        frequencia = 0.0;
        confiansa = 0.0;
    }
    Regles(String nomReglesA, ArrayList<Assignacio> assigs, Double freq, Double confi){
        nomRegles = nomReglesA;
        assignacions = assigs;
        frequencia = freq;
        confiansa = confi;
    }

    //setters-------------------------------------------------------------------------

    public void setNomRegles(String nom){
        nomRegles = nom;
}
    public void setAssignacions(ArrayList<Assignacio> a){
        assignacions = a;
    }
    public void setFrequencia(Double f){
        frequencia = f;
    }
    public void setConfiansa(Double c){
        confiansa = c;
    }


    //getters-------------------------------------------------------------------------

    public String getNomRegles(){
        return nomRegles;
    }
    public ArrayList<Assignacio> getAssignacions(){
        return assignacions;
    }
    public Double getFrequencia(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return frequencia;
    }
    public Double getConfiansa(){
        //un setter per cada atribut
        //establir el mapa amb els valors de contingut
        return confiansa;
    }


    //operacions------------------------------------------------------------------------

    //Algorisme per calcular les assignacions. Aquesta part només calcula la primera tanda d'atributs.
    public void algorisme(ArrayList<Rang> taula){

        ArrayList<Rang> llistaCandidats = new ArrayList<Rang>();
        ArrayList<Conjunts> itemset1 = new ArrayList<Conjunts>();
        ArrayList<Conjunts> conjuntsDefinitius = new ArrayList<Conjunts>();

        //Ens guardem tots els atributs candidats a ser escollits
        //Contem el nombre de vegades que aquests atributs son certs
        for (int i=0; i<taula.size(); i++){
            Rang rAux = taula.get(i);
            ArrayList<Boolean> aux = rAux.getCasos();
            int counter = 0;
            for (int j=0; j<aux.size(); j++) {
                if (aux.get(j)) {
                    counter++;
                }
            }
            if ((valueOf(counter) / valueOf(aux.size())) >= frequencia) {
                Conjunts conjAux = new Conjunts(rAux, aux);
                itemset1.add(conjAux);
                llistaCandidats.add(rAux);
            }
        }
        trobaConjunts(llistaCandidats, itemset1, conjuntsDefinitius);
        trobaAssignacions(conjuntsDefinitius);

    }

    //Immersio de l'algorisme. Permet fer una cerca en arbre per recorrer només aquells casos amb una frequencia desitjable.
    private void trobaConjunts(ArrayList<Rang> llistaCandidats, ArrayList<Conjunts> itemset1, ArrayList<Conjunts> conjuntsDefinitius){
        ArrayList<Rang> llista2 = new ArrayList<Rang>(llistaCandidats);
        llista2.remove(0);
        for (int i=0; i<llistaCandidats.size(); i++){
            ArrayList<Conjunts> itemset2 = new ArrayList<Conjunts>();
            Rang rAux = llistaCandidats.get(i);
            ArrayList<Boolean> aux1 = rAux.getCasos();
            for (int k=0; k<itemset1.size(); k++) {
                Conjunts cAux = itemset1.get(k);
                ArrayList<Boolean> aux2 = new ArrayList<Boolean>(cAux.getCasos());
                int counter = 0;
                for (int j = 0; j < aux1.size(); j++) {
                    if (aux1.get(j) && aux2.get(j)) {
                        counter++;
                    }
                    else aux2.set(j,false);
                }
                if ((valueOf(counter) / valueOf(aux1.size())) >= frequencia) {
                    Conjunts conjAux = new Conjunts(rAux, aux2);
                    itemset2.add(conjAux);
                    conjuntsDefinitius.add(conjAux);
                }
            }
            trobaConjunts(llista2, itemset2, conjuntsDefinitius);
        }
    }
    private void trobaAssignacions(ArrayList<Conjunts> conjunts){
        for (int i=0; i<conjunts.size(); i++){
            Conjunts conj = conjunts.get(i);
            ArrayList<Rang> rangs = conj.getRangs();
            for (int j=0; j<rangs.size(); j++){
                Conjunts conjunt = new Conjunts(conj);
                conjunt.removeRang(j);
                fesAssignacions(conjunt, rangs.get(j));
            }
        }

    }
    private void fesAssignacions(Conjunts conjunt, Rang r){
        Rang rang = new Rang(r);
        ArrayList<Boolean> casosCons = rang.getCasos();
        ArrayList<Rang> rangs = conjunt.getRangs();
        ArrayList<Boolean> casosAnt = rangs.get(0).getCasos();
        Double ant=0.0, cons=0.0, anticons=0.0;
        for (int i=1; i<rangs.size();i++){
            ArrayList<Boolean> aux = rangs.get(i).getCasos();
            for (int j=0; j<casosAnt.size(); j++){
                if(!casosAnt.get(j) || !aux.get(j)) casosAnt.set(j,false);
            }
        }
        for (int i=0; i<casosCons.size(); i++){
            if (casosCons.get(i)) cons++;
            if (casosAnt.get(i)) ant++;
            if (casosAnt.get(i) && casosCons.get(i)) anticons++;
        }
        if (anticons/ant >= confiansa) {
            Assignacio nova = new Assignacio(anticons/casosCons.size(), anticons/ant, conjunt, rang);
            assignacions.add(nova);
        }
    }

}