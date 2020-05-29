
package Domini;

import java.util.*;
/*
package com.journaldev.files
import java.io.BufferReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
*/


public class Resultats{
    private static String nomRes;
    private ArrayList<Clients> clients;


    //creadores

    Resultats(String nom){
    	nomRes = nom;
    	clients = new ArrayList<>();
    }

    Resultats(String nom, ArrayList<Clients> c){
    	nomRes = nom;
    	clients = c;
    }


    //Setters

    public static void setNomRes(String n){
        nomRes = n;
    }

    public void setClients(ArrayList<Clients> c){
        clients = c;
    }

    //getters
    public static String getNomRes(){
        return nomRes;
    }

    public ArrayList<Clients> getClients(){
        return clients;
    }

    //operacions


    //algorisme per comprovar resultats.
    public void comprovacio(ArrayList<Rang> rangs, ArrayList<Assignacio> regles) {
        // agafa les assignacions trobades i les aplica al exemple donat
        // guarda el resultat a results
        int length = rangs.get(0).getCasos().size();
        for (int i = 0; i < rangs.size(); i++) {
            String r = rangs.get(i).getNom();
            ArrayList<Assignacio> assigs = new ArrayList<Assignacio>();
            for (int j = 0; j < regles.size(); j++) {
                Boolean guarda = true;
                Conjunts c = regles.get(j).getAntecedents();
                ArrayList<Rang> rang = c.getRangs();
                for (int k = 0; k < rang.size(); k++) {
                    Boolean trobat = false;
                    Boolean valid = true;
                    for (int t=0; t<length; t++) {
                        if (rangs.get(i).getNom() == rang.get(k).getNom()){
                            trobat = true;
                            if (!rangs.get(i).getCasos().get(t)){
                                valid = false;
                            }
                        }
                    }
                    if (!valid || !trobat){
                        guarda = false;
                        k= rang.size();
                    }
                }
                if (guarda){
                    assigs.add(regles.get(j));
                }
            }
            if (assigs.size() > 0){
                clients.add(new Clients(assigs));
            }
        }
    }


    //public static void calcula 


}