


package Domini;

import java.util.ArrayList;

public class Assignacio{

	private Double freq;
	private Double confi;
	private Conjunts antecedents;
	private Rang consequent;


	//constructores
	Assignacio(){
		freq = 0.0;
		confi = 0.0;
		antecedents = new Conjunts();
		consequent = null;
	}

	Assignacio(Double freqA, Double confiA, Conjunts antecedentsA, Rang consequentA){
		freq = freqA;
		confi = confiA;
		antecedents = antecedentsA;
		consequent = consequentA;
	}

	//getters

	public Double getFreq(){
		return freq;
	}
	public Double getConfi(){
		return confi;
	}
	public Conjunts getAntecedents(){
		return antecedents;
	}
	public Rang getConsequent(){
		return consequent;
	}

	//setters

	public void setFreq(Double f){
		freq = f;
	}
	public void setConfi(Double c){
		confi = c;
	}
	public void setAntecedents(Conjunts a){
		antecedents = a;
	}
	public void setConsequent(Rang c){
		consequent = c;
	}



}