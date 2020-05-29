package Domini;

import com.sun.jdi.ArrayReference;

import java.util.ArrayList;
import java.util.spi.AbstractResourceBundleProvider;

public class Conjunts {
    private ArrayList<Rang> rangs;
    private ArrayList<Boolean> casos;

    //Creadores

    public Conjunts(){
        rangs = new ArrayList<Rang>();
        casos = new ArrayList<Boolean>();
    }
    public Conjunts(Rang r){
        rangs = new ArrayList<Rang>();
        rangs.add(r);
    }
    public Conjunts(Rang r, ArrayList<Boolean> c){
        rangs = new ArrayList<Rang>();
        rangs.add(r);
        casos = c;
    }
    public Conjunts(Rang r, ArrayList<Boolean> c, Conjunts con){
        rangs = new ArrayList<Rang>();
        rangs.add(r);
        casos = c;
    }
    public Conjunts(Conjunts c){
        rangs = c.getRangs();
        casos = c.getCasos();
    }

    //setters
    public void setRangs(ArrayList<Rang> r){
        rangs = r;
    }

    public void setCasos(ArrayList<Boolean> b){
        casos = b;
    }

    //getters
    public ArrayList<Rang> getRangs(){
        return rangs;
    }

    public ArrayList<Boolean> getCasos(){
        return casos;
    }


    //operacions
    public void addRang(Rang r){
        rangs.add(r);
    }

    public void removeRang(Integer i){
        rangs.remove(i);
    }

}
