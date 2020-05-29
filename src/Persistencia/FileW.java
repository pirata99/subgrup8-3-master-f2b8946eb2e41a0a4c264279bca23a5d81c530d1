package Persistencia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class FileW {

    public static void outputfile (String a, String nom) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(nom, true));
        writer.append(a);
        writer.append('\n');
        writer.close();
    }

}