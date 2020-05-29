package Domini;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Atribut{
	private String nom;
	private String tipus;
	private Boolean discret;
	private Boolean showDefault;
	private ArrayList<String> casos;
	private ArrayList<Rang> atribsDisc;

	//Creadores
	public Atribut(String nomA){
		//Pre: nomA és un String vàlid amb el nom de l'atribut que es vol crear.
		//Post: Crea una instancia de la classe Atribut i la inicialitza amb el nom definit i valors nuls.
		nom = nomA;
		tipus = "";
		atribsDisc = new ArrayList<Rang>();
		discret = false;
		showDefault = true;
		casos = new ArrayList<String>();
	}
	public Atribut(String nomA, String tipusA){
		//Pre: nomA és un String vàlid amb el nom de l'atribut que es vol crear, tipusA és un String càlid amb el tipus de l'atribut.
		//Post: Crea una instancia de la classe Atribut i la inicialitza amb el nom i el tipus definits, i valors nuls.
		nom = nomA;
		tipus = tipusA;
		atribsDisc = new ArrayList<Rang>();
		discret = false;
		showDefault = true;
		casos = new ArrayList<String>();
	}
	public Atribut(String nomA, String tipusA,ArrayList<String> casosA, ArrayList<Rang> atribsDiscA){
		//Pre: nomA és un String vàlid amb el nom de l'atribut que es vol crear, tipusA és un String vàlid amb el tipus de l'atribut.
		//Post: Crea una instancia de la classe Atribut i la inicialitza amb el nom, el tipus, els casos i els atributs discrets definits, i valors nuls.
		nom = nomA;
		tipus = tipusA;
		casos = casosA;
		atribsDisc = atribsDiscA;
		discret = false;
		showDefault = true;
	}

	//Getters
	public String getNom(){
		//Pre: -
		//Post: Retorna el nom de l'atribut.
		return nom;
	}
	public String getTipus(){
		//Pre: -
		//Post: Retorna el tipus de l'atribut.
		return tipus;
	}
	public Boolean getDiscret(){
		//Pre: -
		//Post: Retorna si l'atribut està discretitzat.
		return discret;
	}
	public Boolean getShowDefault(){
		//Pre: -
		//Post: Retorna si es vol comptar amb l'atribut discretitzat per defecte.
		return showDefault;
	}
	public ArrayList<String> getCasos(){
		//Pre: -
		//Post: Retorna la llista de casos de l'atribut.
		return casos;
	}
	public ArrayList<Rang> getAtribsDisc(){
		return atribsDisc;
	}

	//Setters
	public void setNom(String n) {
		nom = n;
	}
	public void setTipus(String t){
		tipus = t;
	}
	public void setDiscret(Boolean d){
		discret = d;
	}
	public void setShowDefault(Boolean d){
		showDefault = d;
	}
	public void setCasos(ArrayList<String> c){
		casos = c;
	}
	public void setAtribsDisc(ArrayList<Rang> a){
		atribsDisc = a;
	}

	//operacions
	public int numCasos(){
		return casos.size();
	}
	public void addCasos(String cas){
		casos.add(cas);
	}
	public void discretitzaValNum(String rang, Double tall1, Double tall2){
		if (atribsDisc.size() == 0) rangDefecte();
		ArrayList<Boolean> r1 = ompleFals();
		ArrayList<Boolean> r2 = atribsDisc.get(0).getCasos();
		for (int i=0; i<casos.size(); i++){
			if (casos.get(i).equals("NULL") || casos.get(i).equals("null") || casos.get(i).equals("Null")){
			}
			else if (Double.parseDouble(casos.get(i)) > tall1 && Double.parseDouble(casos.get(i)) < tall2){
				r1.set(i, true);
				r2.set(i, false);
			}
			else {
				r1.set(i, false);
			}
		}
		//Rang r = new Rang(rang, r1);
		atribsDisc.add(new Rang(rang, r1));
		//atribsDisc.get(0).setCasos(r2); 	// no hauria de ser necessari
		discret = true;
	}
	public void discretitzaValBool(){
		ArrayList<Rang> lRang = new ArrayList<Rang>();
		String nomCert = this.nom + "_cert";
		String nomFals = this.nom + "_fals";
		if (atribsDisc.size() == 0) rangDefecte();
		//ArrayList<Boolean> r1 = ompleFals();
		//ArrayList<Boolean> r2 = ompleFals();
		ArrayList<Boolean> r1 = ompleFals();
		ArrayList<Boolean> r2 = ompleFals();
		System.out.print("nom cert: "+nomCert+"\n");
		System.out.print("nom fals: "+nomFals+"\n");
		for (int i=0; i<casos.size(); i++){

			if (casos.get(i).equals("0")){
				//r1.set(i,false);
				r2.set(i,true);
			}
			else if(casos.get(i).equals("1")){
				r1.set(i,true);
				//r2.set(i,false);
			}
		}
		Rang r = new Rang(nomCert, r1);
		lRang.add(r);
		Rang r3 = new Rang(nomFals, r2);
		lRang.add(r3);
		atribsDisc = lRang;
		for (int i=0; i<atribsDisc.size(); i++){
			System.out.print("nous atributs discretitzats: "+atribsDisc.get(i).getNom()+"   ");
		}
		System.out.print("\n");

		discret = true;
	}
	public void discretitzaString(){
		if (atribsDisc.size() == 0) rangDefecte();
		for (int i=0; i<casos.size(); i++){
			//int aux = findName(namesFound,casos[i]);
			String nomAux = nom+"_"+casos.get(i);
			if (casos.get(i).equals("NULL") || casos.get(i).equals("null") || casos.get(i).equals("Null")){
			}
			else if (trobaRang(casos.get(i)) == -1){
				Rang r = new Rang(nomAux, ompleFals());
				r.setCas(i, true);
				atribsDisc.add(r);
			}
			else {
				atribsDisc.get(trobaRang(casos.get(i))).setCas(i, true);
			}

		}
		discret = true;
	}
	public void discretitzaData(String nomRang, String data1, String data2){
		if (atribsDisc.size() == 0) rangDefecte();
		ArrayList<Boolean> r1 = ompleFals();
		atribsDisc.add(new Rang(nomRang+"_"+"default", ompleFals()));
		ArrayList<Boolean> r2 = atribsDisc.get(0).getCasos();
		for (int i=0; i<casos.size(); i++){
			String aux = casos.get(i);
			if (aux.equals("null") || aux.equals("NULL") || aux.equals("Null")){
				atribsDisc.get(0).setCas(i, true);
			}
			else if ((anyDe(casos.get(i))>anyDe(data1) || (anyDe(casos.get(i))==anyDe(data1) && mesDe(casos.get(i))>mesDe(data1))
					|| (anyDe(casos.get(i))==anyDe(data1) && mesDe(casos.get(i))==mesDe(data1) && diaDe(casos.get(i))>diaDe(data1)))
					&& (anyDe(casos.get(i))<anyDe(data2) || (anyDe(casos.get(i))==anyDe(data2) && mesDe(casos.get(i))<mesDe(data2))
					|| (anyDe(casos.get(i))==anyDe(data2) && mesDe(casos.get(i))==mesDe(data2) && diaDe(casos.get(i))<diaDe(data2)))){
				r1.set(i,true);
				r2.set(i,false);
			}
			else {
				r1.set(i,false);
				r2.set(i,true);
			}
		}
		atribsDisc.add(new Rang(nomRang, r1));

		discret = true;
	}
	private void rangDefecte(){
		ArrayList<Boolean> b = new ArrayList<Boolean>();
		for (int i=0; i<casos.size(); i++){
			if (casos.get(i).equals("null") || casos.get(i).equals("Null") || casos.get(i).equals("NULL")) b.add(false);
			else b.add(true);
		}
		atribsDisc.add(new Rang(nom+"_"+"default", b));
	}
	private int trobaRang(String n){
		for (int i=0; i<atribsDisc.size(); i++){
			if (atribsDisc.get(i).getNom().equals(n)) return i;
		}
		return -1;
	}
	private ArrayList<Boolean> ompleFals(){
		ArrayList<Boolean> aux = new ArrayList<Boolean>();
		for (int i=0; i< casos.size(); i++) aux.add(false);
		return aux;
	}
	private int diaDe(String s) {
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
	private int anyDe(String s) {
		//XXXX-XX-XX
		String aux = "";
		aux += s.charAt(0);
		aux += s.charAt(1);
		aux += s.charAt(2);
		aux += s.charAt(3);
		int any = Integer.parseInt(aux);
		return any;
	}
	private int mesDe(String s) {
		//XXXX-XX-XX
		String aux = "";
		aux += s.charAt(5);
		aux += s.charAt(6);
		int mes = Integer.parseInt(aux);
		return mes;
	}
	public Atribut divideixAtrib(int r1, int r2){
		ArrayList<Rang> r = new ArrayList<Rang>();
		if (!discret) return new Atribut(nom, tipus, new ArrayList<String>(casos.subList(r1,r2)), r);
		for (int i=0; i<atribsDisc.size(); i++){
			r.add(atribsDisc.get(i).divideixRang(r1, r2));
		}
		Atribut atr = new Atribut(nom, tipus, new ArrayList<String>(casos.subList(r1,r2)), r);
		atr.setDiscret(discret);
		atr.setShowDefault(showDefault);
		return atr;
	}

}