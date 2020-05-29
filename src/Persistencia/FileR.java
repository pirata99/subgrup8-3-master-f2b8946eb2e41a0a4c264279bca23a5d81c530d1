package Persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Path;

public class FileR {

    public static String[] inputAtributsCSV (String fileName) throws IOException{
        String line ="";
        BufferedReader br2 = new BufferedReader(new FileReader(fileName));
        String[] atributsAL = new String[0];
        if ((line = br2.readLine()) != null) {
            atributsAL = line.split(",");
        }
        br2.close();
        return atributsAL;
    }

    public static ArrayList<String[]> inputTaulaCSV (String fileName) throws IOException{
        String line ="";
        ArrayList<String[]> taula = new ArrayList<>();
        BufferedReader br2 = new BufferedReader(new FileReader(fileName));
        line = br2.readLine();
        while ((line = br2.readLine()) != null) {
                // use comma as separator
                String[] country = line.split(",");
                taula.add(country);
            }
        br2.close();
        return taula;
    }

    public static String inputArxiuGuardat(String fileName) throws IOException {
        return Files.readString(Paths.get(fileName), StandardCharsets.US_ASCII);
    }

}