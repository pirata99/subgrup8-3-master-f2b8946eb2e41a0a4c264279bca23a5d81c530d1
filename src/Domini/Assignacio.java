


package Domini;

import java.util.ArrayList;

public class Assignacio{

	private Double freq;
	private Double confi;
	private Conjunts antecedents;
	private Rang consequent;


	//constructores
	public Assignacio(){
		//Pre: -
		//Post: Crea una instancia de la classe Assignacio i la inicialitza amb valors nuls.
		freq = 0.0;
		confi = 0.0;
		antecedents = new Conjunts();
		consequent = null;
	}

	public Assignacio(Double freqA, Double confiA, Conjunts antecedentsA, Rang consequentA){
		//Pre: La frequencia i confiansa son valors Double entre 0 i 1, antecedentsA i consequentA son instancies de la seva classe corresponent
		//Post: Crea una instancia de la classe Assignacio i la inicialitza amb else valors definits.
		freq = freqA;
		confi = confiA;
		antecedents = antecedentsA;
		consequent = consequentA;
	}


	//getters
	public Double getFreq(){
		//Pre: -
		//Post: Retorna la frequencia.
		return freq;
	}
	public Double getConfi(){
		//Pre: -
		//Post: Retorna la confiança.
		return confi;
	}
	public Conjunts getAntecedents(){
		//Pre: -
		//Post: Retorna una instància de la classe Conjunts que conté els antecedents.
		return antecedents;
	}
	public Rang getConsequent(){
		//Pre: -
		//Post: Retorna una instància de Rang que conté el consequent.
		return consequent;
	}

	//setters

	public void setFreq(Double f){
		//Pre: f és una valor double vàlid.
		//Post: La freqüència freq passa a valer f.
		freq = f;
	}
	public void setConfi(Double c){
		//Pre: c és una valor double vàlid.
		//Post: La confiança confi passa a valer c.
		confi = c;
	}
	public void setAntecedents(Conjunts a){
		//Pre: a és una instància vàlida de la classe Conjunts.
		//Post: antecedents passa a tenir els rangs de a.
		antecedents = a;
	}
	public void setConsequent(Rang c){
		//Pre: c és una instància vàlida de la classe rang.
		//Post: consequent passa a tenir el valor del Rang c.
		consequent = c;
	}



}