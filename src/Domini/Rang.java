package Domini;

//import javafx.util.Pair
import java.util.ArrayList;

public class Rang{

	private String nom;
	private ArrayList<Boolean> casos;


	//creadores

	public Rang(String nomA){
		nom = nomA;
		casos = new ArrayList<Boolean>();
	}

	public Rang(String nomA, ArrayList<Boolean> c){
		nom = nomA;
		casos = c;
	}

	public Rang(Rang r){
		nom = r.getNom();
		casos = r.getCasos();
	}

	//setters

	public void setNom(String name) {

		nom = name;
	}
	public void setCasos(ArrayList<Boolean> cases) {
		casos = cases;
	}

	//Getters

	public String getNom() {
		return nom;
	}
	public ArrayList<Boolean> getCasos() {
		return casos;
	}

	//operacions

	public void setCas(int i, Boolean b){
		casos.set(i, b);
	}
	public Rang divideixRang(int r1, int r2){
		Rang r = new Rang(nom, new ArrayList<Boolean>(casos.subList(r1, r2)));
		return r;
	}

}