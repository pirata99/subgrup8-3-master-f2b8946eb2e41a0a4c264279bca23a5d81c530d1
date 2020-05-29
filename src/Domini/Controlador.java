package Domini;

//import javafx.collections.ObservableList;

//import javax.crypto.AEADBadTagException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controlador {
    public static Persistencia.Controlador ctrl_Persistencia = new Persistencia.Controlador();
    public Llista list;
    public Regles reg;
    public Resultats res;

    public int creaLlista(String nom, String fitxer) throws IOException, NoSuchMethodException, InstantiationException, CloneNotSupportedException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if (ctrl_Persistencia.existeixFitxer("@../../Dades/"+nom+".llista")) return -1; //retorn: -1 -> ja existeix la llista
        if (!ctrl_Persistencia.existeixFitxer("@../../files/"+fitxer+".csv")) return 1;
        list = ctrl_Persistencia.llegeixCSV(nom, fitxer);
        System.out.print("num atribs: "+list.getAtribs().size()+"\n");
        //list = new Llista(nom,ctrl_Persistencia.funcioQueLlegeixFitxers(fitxer)); //es crea tambe amb els atributs llegits del fitxer, falta fer la funcio a persistencia
        return 0;
    }

    public void guardaLlista() throws IOException {
        //agafa la instancia de llista i la guarda.
        ctrl_Persistencia.guardaLlista("@../../Dades/"+list.getNomLlista()+".llista",list);
    }

    public void carregaLlista(String nom) throws IOException {
        list = ctrl_Persistencia.llegeixLlista(nom);
    }

    public static ArrayList<String> getLlistaNoms() throws IOException{
        //accedir a la capa de persistencia i llegir el nom de totes les llistes guardades.
        return ctrl_Persistencia.llistaFitxers("@../../Dades","llista");
    }

    public static ArrayList<String> getFitxerNoms() throws IOException{
        return ctrl_Persistencia.llistaFitxers("@../../files", "csv");
    }

    public ArrayList<String> mostraLlistaAtribs(){
        //lectura classe llista de fitxer
        ArrayList<String> tauleta = new ArrayList<>();
        System.out.print("num atribs: "+list.getNomLlista()+"\n");
        String aux = list.getAtribs().get(0).getNom();
        for (int i=1; i<list.getAtribs().size(); i++){
            aux += "\t" + list.getAtribs().get(i).getNom();
        }
        tauleta.add(aux);
        for (int j=0; j<list.getNumCasos(); j++) {
            aux = list.getAtribs().get(0).getCasos().get(j);
            for (int i = 1; i < list.getAtribs().size(); i++) {
                aux += "\t" + list.getAtribs().get(i).getCasos().get(j);
            }
            tauleta.add(aux);
        }
        return tauleta;
    }

    public ArrayList<String> mostraLlistaDiscret(){
        //lectura classe llista de fitxer
        ArrayList<String> tauleta = new ArrayList<>();
        int p = 0;
        System.out.print("num atribs disc de 0: "+list.getAtribs().get(0).getAtribsDisc().size());
        ArrayList<Atribut> disc = new ArrayList<Atribut>();

        for (int i=0; i<list.getAtribs().size(); i++){
            if (list.getAtribs().get(i).getDiscret()) disc.add(list.getAtribs().get(i));
        }


        String aux = "";
        for (int i=0; i<disc.size(); i++){
            for (int k=0; k<disc.get(i).getAtribsDisc().size(); k++) {
                aux += disc.get(i).getAtribsDisc().get(k).getNom() + "\t";
            }
        }
        tauleta.add(aux);
        for (int j=0; j<list.getNumCasos(); j++) {
            aux = "";
            for (int i = 0; i < disc.size(); i++) {
                int f = 0;
                if (!disc.get(i).getShowDefault()) f++;
                for (int k=f; k<disc.get(i).getAtribsDisc().size(); k++) {
                    aux += String.valueOf(disc.get(i).getAtribsDisc().get(k).getCasos().get(j)) + "\t";
                }
            }
            tauleta.add(aux);
        }
        return tauleta;
    }

    public ArrayList<String> mostraRegles(){
        //lectura classe llista de fitxer
        ArrayList<String> tauleta = new ArrayList<>();
        String aux = reg.getAssignacions().get(0).getAntecedents().getRangs().get(0).getNom();
        for (int i=1; i<list.getAtribs().size(); i++){
            aux += "\t" + list.getAtribs().get(i).getNom();
        }
        tauleta.add(aux);
        for (int j=0; j<list.getNumCasos(); j++) {
            aux = list.getAtribs().get(0).getCasos().get(j);
            for (int i = 1; i < list.getAtribs().size(); i++) {
                aux += "\t" + list.getAtribs().get(i).getCasos().get(j);
            }
            tauleta.add(aux);
        }
        return tauleta;
    }

    public ArrayList<String> getAtribsDiscrets(){
        ArrayList<String> disc = new ArrayList<String>();

        for (int i=0; i<list.getAtribs().size(); i++){
            if (list.getAtribs().get(i).getTipus().equals("Double") || list.getAtribs().get(i).getTipus().equals("Date")) disc.add(list.getAtribs().get(i).getNom());
        }
        return disc;
    }

    public ArrayList<String> getAtribsDiscrets(String nom){
        Atribut a = trobaAtrib(nom);
        ArrayList<String> disc = new ArrayList<String>();
        for (int i=1; i<a.getAtribsDisc().size(); i++){
            disc.add(a.getAtribsDisc().get(i).getNom());
        }
        return disc;
    }

    public void eliminaAtribDisc(String nomA, String nomD){
        Atribut a = trobaAtrib(nomA);
        for (int i=0; i< a.getAtribsDisc().size(); i++){
            if (a.getAtribsDisc().get(i).getNom().equals(nomD)) a.getAtribsDisc().remove(i);
        }
    }

    public ArrayList<String> mostraUnAtributDiscret(String nom){
        Atribut a = trobaAtrib(nom);
        ArrayList<String> ret = new ArrayList<String>();

        String aux = "";
        int f = 0;
        if (!a.getShowDefault()) f++;
        for (int k=f; k<a.getAtribsDisc().size(); k++) {
            aux += a.getAtribsDisc().get(k).getNom() + "\t";
        }

        ret.add(aux);
        for (int j=0; j<list.getNumCasos(); j++) {
            aux = "";
            f = 0;
            if (!a.getShowDefault()) f++;
            for (int k=f; k<a.getAtribsDisc().size(); k++) {
                aux += String.valueOf(a.getAtribsDisc().get(k).getCasos().get(j)) + "\t";
            }
            ret.add(aux);
        }
        return ret;

    }

    private Atribut trobaAtrib(String nom){
        for (int i=0; i< list.getAtribs().size(); i++){
            if (list.getAtribs().get(i).getNom().equals(nom)) return list.getAtribs().get(i);
        }
        return null;
    }

    public String obteTipus(String nom){
        Atribut a = trobaAtrib(nom);
        return a.getTipus();
    }

    public void setShowDefaultAtrib(String nom, Boolean b){
        Atribut a = trobaAtrib(nom);
        a.setShowDefault(b);
    }

    public Boolean getShowDefaultAtrib(String nom){
        Atribut a = trobaAtrib(nom);
        return a.getShowDefault();
    }

    public void afegeixRang(String nomAtr, String nomR, String rang1, String rang2){
        Atribut a = trobaAtrib(nomAtr);
        System.out.print(a.getTipus());
        if (a.getTipus().equals("Double")) a.discretitzaValNum(nomR, Double.valueOf(rang1), Double.valueOf(rang2));
        else if (a.getTipus().equals("Date")) a.discretitzaData(nomR, rang1, rang2);
    }

    public int creaRegles_i_Resultats(String nomReg, String nomRes, Double percentatge, Double freq, Double confi){
        ArrayList<Atribut> atr = list.getAtribs();
        ArrayList<Atribut> atribs1 = new ArrayList<Atribut>();
        ArrayList<Atribut> atribs2 = new ArrayList<Atribut>();
        Double aux = Double.valueOf(list.getNumCasos()) * percentatge;
        int j = (aux).intValue();
        for (int i=0; i<atr.size(); i++){
            atribs1.add(atr.get(i).divideixAtrib(0, j));
            atribs2.add(atr.get(i).divideixAtrib(j+1, atr.get(i).numCasos()));
        }
        reg = new Regles(nomReg);
        reg.setFrequencia(freq);
        reg.setConfiansa(confi);
        res = new Resultats(nomRes);
        ArrayList<Rang> r1 = new ArrayList<Rang>();
        ArrayList<Rang> r2 = new ArrayList<Rang>();
        for (int i=0; i<atribs1.size();i++){
            ArrayList<Rang> ra = atribs1.get(i).getAtribsDisc();
            if (atribs1.get(i).getShowDefault()) r1.add(ra.get(0));
            for (int k=1; k<ra.size(); k++){
                r1.add(ra.get(k));
            }
        }
        for (int i=0; i<atribs1.size();i++){
            ArrayList<Rang> ra = atribs1.get(i).getAtribsDisc();
            if (atribs1.get(i).getShowDefault()) r2.add(ra.get(0));
            for (int k=1; k<ra.size(); k++){
                r2.add(ra.get(k));
            }
        }
        reg.algorisme(r1);
        ArrayList<Assignacio> asig = reg.getAssignacions();
        res.comprovacio(r2,asig);
        return 0;
    }

    public void guardaRegles_i_Resultats() throws IOException {
        ctrl_Persistencia.outputRegles(reg);
        ctrl_Persistencia.outputResultats(res);
    }

    public ArrayList<String> obteRegles(){
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<Assignacio> ass = reg.getAssignacions();
        for (int i=0; i<ass.size(); i++){
            String aux = escriuAssig(ass.get(i));
            ret.add(aux);
        }
        return ret;
    }

    public static ArrayList<String> getReglesNoms() throws IOException{
        //accedir a la capa de persistencia i llegir el nom de totes les llistes guardades.
        return ctrl_Persistencia.llistaFitxers("@../../Dades","regles");
    }

    public static ArrayList<String> getResultatNoms() throws IOException{
        //accedir a la capa de persistencia i llegir el nom de totes les llistes guardades.
        return ctrl_Persistencia.llistaFitxers("@../../Dades","resultats");
    }

    private String escriuAssig(Assignacio a){
        String aux = "{";
        for (int j=0; j<a.getAntecedents().getRangs().size(); j++){
            aux += a.getAntecedents().getRangs().get(j).getNom();
            aux += ", ";
        }
        aux += " } -> ";
        aux += a.getConsequent().getNom();
        return aux;
    }

    public void obteResultats(ArrayList<String> a, ArrayList<String> b){
        for (int i=0; i<res.getClients().size(); i++){
            a.add("Client "+i+1);
            b.add(escriuAssig(res.getClients().get(i).getAssigs().get(0)));
            for (int j=1; j<res.getClients().get(i).getAssigs().size(); j++) {
                a.add("");
                b.add(escriuAssig(res.getClients().get(i).getAssigs().get(j)));
            }
        }
    }

    public ArrayList<String> mostraResultats(){
        //lectura classe llista de fitxer
        ArrayList<String> tauleta = new ArrayList<>();
        for (int j=0; j<res.getClients().size(); j++) {
            for (int k=0; k<res.getClients().get(j).getAssigs().size(); k++) {
                String aux = "";
                for (int i = 0; i < res.getClients().get(j).getAssigs().get(k).getAntecedents().getRangs().size(); i++) {
                    aux += res.getClients().get(j).getAssigs().get(k).getAntecedents().getRangs().get(i).getNom() + "\t";
                }
                aux += " -> " + res.getClients().get(j).getAssigs().get(k).getConsequent().getNom();
                tauleta.add(aux);
            }
        }
        return tauleta;
    }

}
