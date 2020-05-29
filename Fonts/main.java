import java.util.Scanner;
/*import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrIntegerStream;*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;

import java.util.ArrayList;

public class main{

	private static Llista llistesGuardades;
	private static Assignacions assignacionsGuardades;
	private static Resultats resultatsGuardats;


	/*
	private static ArrayList<llista> llistesGuardades = new ArrayList<llista>();
	private static ArrayList<assignacions> assignacionsGuardades = new ArrayList<assignacions>();
	private static ArrayList<resultats> resultatsGuardats = new ArrayList<resultats>();
	*/

	public static void main (String[] args) throws IOException{
		llistesGuardades = new Llista();
		assignacionsGuardades = new Assignacions();
		resultatsGuardats = new Resultats();
		Boolean sortida = false;
		while (!sortida){
			System.out.print("\n---------Programa per calcular Regles d'Associacio-----------\n\n");
			System.out.print("Selecciona l'operació que vols realitzar:\n\n");
			System.out.print("1. Crear nova llista de casos.\n");
			System.out.print("2. Editar o visualitzar llista existent.\n");
			System.out.print("3. Crear conjunt de regles d'associacio.\n");
			System.out.print("4. Editar o visualitzar regles d'associacio existents.\n");
			System.out.print("5. Realitzar test d'associacions.\n");
			System.out.print("6. Editar o visualitzar test existent.\n");
			System.out.print("7. Sortir.\n");
			System.out.print("\nIntrodueix el numero: ");
			
        	Scanner llegit = new Scanner(System.in);
        	String accio = llegit.next();

			switch (accio){
				case "1":
				//case "create list":
					System.out.print("\nDe moment nomes utilitzem una llista i ja esta creada.\n\n");
					//creaLlista();
					break;
				case "2":/*
					System.out.print("\nIntrodueix el nom de la llista amb la que vols treballar: ");
	        		Scanner llegit2 = new Scanner(System.in);
    		    	String nom = llegit2.next();
    		    	int aux = existeixLlista(nom);
					if (aux != -1){
							editaLlista(llistesGuardades.get(aux));
					}
					else System.out.print("\nLa llista introduida no existeix. Si us plau, introdueix un nom vàlid o crea una llista nova.\n\n");
					*/
					editaLlista();
					break;
				case "3":
					creaRegles();
					break;
				case "4":
					editaRegles();
					break;
				case "5":
					System.out.print("\nDe moment no esta implementada la part dels testos.\n\n");
					creaTest();
					break;
				case "6":
					System.out.print("\nDe moment no esta implementada la part dels testos.\n\n");
					editaTest();
					break;
				case "7":
					sortida = true;
					break;
				default:
					System.out.print("\n---------------------------------------------------\nOpcio no valida, si us plau torna a intentar-ho.\n---------------------------------------------------\n");
					break;

			}
		}
	}



	//Menús secundaris.
/*
	private static void creaLlista(){
		System.out.print("Per crear una llista necessites un nom. Introdueix el nom de la llista que vols cear: ");
       	Scanner llegit = new Scanner(System.in);
       	String nom = llegit.next();

       	int aux = existeixLlista(nom);

       	if (aux == -1) {
       		llista a = new llista();

       		a.setNomLlista(nom);
       		llistesGuardades.add(a);
       	}
       	else {
       		System.out.print("\nJa existeix una llista amb aquest nom. Si us plau prova amb un altre nom.\n\n");
       	}
	}
	*/
	private static void editaLlista() throws IOException{
		System.out.print("Escull quina modificació vols fer a la llista de casos:\n");
		System.out.print("1. Carregar fitxer.\n");
		System.out.print("2. Tornar al menu principal.\n");
		System.out.print("\nIntrodueix el numero: ");

		Scanner llegit = new Scanner(System.in);
       	String accio = llegit.next();
		switch (accio){
			case "1":
				carregaFitxer();
				break;
			case "2":
				break;
			default:
				System.out.print("\n---------------------------------------------------\nOpcio no valida, si us plau torna a intentar-ho.\n---------------------------------------------------\n");
				break;
		}

	}
	private static void creaRegles(){
		if (llistesGuardades.getDiscret()) {
			assignacionsGuardades = new Assignacions();
			assignacionsGuardades.setN(llistesGuardades.getCasos());
			System.out.print("Introdueix la frequencia llindar per les regles: ");
			Scanner llegit = new Scanner(System.in);
			Float f = Float.parseFloat(llegit.next());
			assignacionsGuardades.setFrequencia(f);
			assignacionsGuardades.algorisme(llistesGuardades.getTaulaComp(), llistesGuardades.getAtributsDiscrets());
		}
		else {
			System.out.print("\nPer calcular les regles d'associacio primer has de discretitzar els atributs de la llista.\n\n");
		}
	}
	private static void editaRegles(){
		
		System.out.print("Escull quina operacio vols fer a la llista de regles d'associacio:\n");
		System.out.print("1. Consulta regles.\n");
		System.out.print("2. Afegeix regles.\n");
		System.out.print("3. Elimina regles.\n");
		System.out.print("4. Guardar resultats.\n");
		System.out.print("5. Tornar al menu principal.\n");
		System.out.print("\nIntrodueix el numero: ");

		Scanner llegit = new Scanner(System.in);
       	String accio = llegit.next();
		switch (accio){
			case "1":
				consultaRegles();
				break;
			case "2":
				//consultaLlista();
				break;
			case "3":
				//discretitza();
				break;
			case "4":
				//afegirAtrib();
				break;
			case "5":
				break;
			default:
				System.out.print("\n---------------------------------------------------\nOpcio no valida, si us plau torna a intentar-ho.\n---------------------------------------------------\n");
				break;
		}
	}
	private static void creaTest(){
		//a
	}
	private static void editaTest(){
		//a
	}


	//Operacions

	private static void carregaFitxer() throws IOException{
		System.out.print("Introdueix el nom del fitxer que vols obrir (ha d'estar al mateix directori que el programa):");
		Scanner llegit = new Scanner(System.in);
       	String nom = llegit.next();
       	System.out.print("El fitxer a llegir es: ");
       	System.out.print(nom);
       	System.out.print("\n");
       	llistesGuardades.carregaLlista(nom);
		System.out.print("Numero de casos: ");
		System.out.print(llistesGuardades.getCasos());
		System.out.print("\n");

	}
/*
	private static int existeixLlista(String nom){
		for (int i=0; i<llistesGuardades.size(); i++){
			System.out.print("Aquesta es la iteracio ");
			System.out.print(i);
			System.out.print("\n");
			llista a = llistesGuardades.get(i);
			System.out.print("I hem trobat la llista amb el nom ");
			System.out.print(a.getNomLlista());
			System.out.print("\n");
			if (a.getNomLlista().equals(nom)) return i;
		}
		return -1;
	}
*/
	private static void discretitza(){
		ArrayList<String> tipus = llistesGuardades.tipus;
		ArrayList<String> atribs = llistesGuardades.atributs;
		System.out.print("Numero d'atributs: ");
		System.out.print(tipus.size());
		System.out.print("\n");
		int contD=1, contB=1;
		for (int i=0; i<tipus.size(); i++){
			if (tipus.get(i) == "String"){
				llistesGuardades.discretitzaString(i);
			}
			else if (tipus.get(i) == "Boolean"){
				System.out.print("\nL'atribut ");
				System.out.print(atribs.get(i));
				System.out.print(" es de tipus Boolean.\nIntrodueix el nom per als valors certs d'aquest atribut:");
				Scanner llegit = new Scanner(System.in);
				String nom1 = llegit.next();
				//String nom1 = "b" + String.valueOf(contB);
				//contB++;
				System.out.print("Introdueix el nom per als valors falsos d'aquest atribut:");
				Scanner llegit2 = new Scanner(System.in);
				String nom2 = llegit2.next();
				//String nom2 = "b" + String.valueOf(contB);
				//contB++;
				llistesGuardades.discretitzaValBool(nom1, nom2, i);
			}
			else if (tipus.get(i) == "Double"){
				System.out.print("\nL'atribut ");
				System.out.print(atribs.get(i));
				System.out.print(" es de tipus Double.\nIntrodueix el valor de tall per discretitzar aqeust atribut:");
				Scanner llegit = new Scanner(System.in);
				Double tall = Double.parseDouble(llegit.next());
				System.out.print("Introdueix el nom per l'atribut amb els valors mes petits que ");
				System.out.print(tall);
				System.out.print(": ");
				Scanner llegit1 = new Scanner(System.in);
				String nom1 = llegit1.next();
				//String nom1 = "a" + String.valueOf(contD);
				//contD++;
				System.out.print("Introdueix el nom per l'atribut amb els valors mes grans que ");
				System.out.print(tall);
				System.out.print(": ");
				Scanner llegit2 = new Scanner(System.in);
				String nom2 = llegit2.next();
				//String nom2 = "a" + String.valueOf(contD);
				//contD++;
				llistesGuardades.discretitzaValNum(nom1, nom2, tall, i);
			}
			else if (tipus.get(i) == "Date"){
				System.out.print("\nL'atribut ");
				System.out.print(atribs.get(i));
				System.out.print(" es de tipus Date.\nIntrodueix el valor de tall per discretitzar aqeust atribut (format: any-mm-dd):");
				Scanner llegit = new Scanner(System.in);
				String tall = llegit.next();
				System.out.print("Introdueix el nom per l'atribut amb els valors mes petits que ");
				System.out.print(tall);
				System.out.print(" :");
				Scanner llegit1 = new Scanner(System.in);
				String nom1 = llegit1.next();
				System.out.print("Introdueix el nom per l'atribut amb els valors mes grans que ");
				System.out.print(tall);
				System.out.print(" :");
				Scanner llegit2 = new Scanner(System.in);
				String nom2 = llegit2.next();
				llistesGuardades.discretitzaData(nom1, nom2, tall, i);
			}

		}
		llistesGuardades.setDiscret(true);
	}

	private static void consultaRegles(){
		ArrayList<String> rules = assignacionsGuardades.getAssig();
		ArrayList<Float> confis = assignacionsGuardades.getConfiances();
		for (int i=0; i<rules.size(); i++){
			System.out.print(rules.get(i));
			System.out.print("	Confiança: ");
			System.out.print(confis.get(i));
			System.out.print("\n");
		}
	}

}






		/*
		assignacions a = new assignacions();

		a.setFrequencia(0.9f);
		a.setConfiança(0.5);
        //assignacions.varIni();
        
    	System.out.print("Llistat d'assignscions: \n");
        ArrayList <Boolean[]> taula = new ArrayList<Boolean[]>();
        ArrayList <String> atribs = new ArrayList<String>();
        atribs.add("aigua");
        atribs.add("pizza");
        atribs.add("olives");
        atribs.add("cocacola");
        Boolean[] aux1 = new Boolean[]{true,true,true,false,true,true,true,false,true,false};
        Boolean[] aux2 = new Boolean[]{false,true,true,false,true,false,false,true,false,true};
        Boolean[] aux3 = new Boolean[]{false,false,true,false,true,true,false,true,false,false};
        Boolean[] aux4 = new Boolean[]{true,true,false,false,true,false,true,false,true,false};
        taula.add(aux1);
        taula.add(aux2);
        taula.add(aux3);
        taula.add(aux4);
        assignacions.algorisme(taula, atribs);
        
        risas b = new risas();
        b.iniVar();
        b.obteTaula();

        ArrayList<Boolean[]> aux1 = new ArrayList<Boolean[]>();
        ArrayList<String> aux2 = new ArrayList<String>();

        aux1 = b.getTaula();
        aux2 = b.getAtribs();
        a.setN(b.getTotal());
        a.algorisme(aux1, aux2);*/
