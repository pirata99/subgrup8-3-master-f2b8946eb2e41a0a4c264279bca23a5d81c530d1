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
    public static Llista list;
    public Regles reg;
    public Resultats res;

    public int creaLlista(String nom, String fitxer) throws IOException, NoSuchMethodException, InstantiationException, CloneNotSupportedException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if (ctrl_Persistencia.existeixFitxer("@../../Dades/"+nom+".llista")) return -1; //retorn: -1 -> ja existeix la llista
        if (!ctrl_Persistencia.existeixFitxer("@../../files/"+fitxer+".csv")) return 1;
        list = ctrl_Persistencia.llegeixCSV(nom, fitxer);
        //list = new Llista(nom,ctrl_Persistencia.funcioQueLlegeixFitxers(fitxer)); //es crea tambe amb els atributs llegits del fitxer, falta fer la funcio a persistencia
        return 0;
    }

    public void carregaLlista(String nom) throws IOException {
        list = ctrl_Persistencia.llegeixLlista(nom);
    }

    public void guardaLlista() throws IOException {
        //agafa la instancia de llista i la guarda.
        ctrl_Persistencia.guardaLlista("@../../Dades/"+list.getNomLlista()+".llista",list);
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
        System.out.print("h222");
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

    public ArrayList<String> obteRegles(){
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<Assignacio> ass = reg.getAssignacions();
        for (int i=0; i<ass.size(); i++){
            String aux = escriuAssig(ass.get(i));
            ret.add(aux);
        }
        return ret;
    }

    private static String escriuAssig(Assignacio a){
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

}
