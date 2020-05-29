
package Domini;

import java.util.ArrayList;


public class Clients{

	private ArrayList<Assignacio> assigs;

	//creadores

	Clients(){
		assigs = new ArrayList<Assignacio>();
	}

	Clients(ArrayList<Assignacio> assigsA){
		assigs = assigsA;
	}

	//setters 
	public void setAssigs(ArrayList<Assignacio> a){
		assigs = a;
	}

	//getters
	public ArrayList<Assignacio> getAssigs(){
		return assigs;
	}


	//operacions
}