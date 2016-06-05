package storage;

import basics.Connection;
import basics.Location;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by user on 5/2/2016.
 */
public class FileUtilities {

    /// Tokenize string is not working!
    public static ArrayList<Location> readCitiesFromFile(String filename) throws IOException {
        ArrayList<Location> nl = new ArrayList<>();

        File file = new File(filename);
        FileReader reader = new FileReader(file);
        LineNumberReader bufferedReader =
                new LineNumberReader(reader);
        String line = bufferedReader.readLine();
        while(line != null){
            //////
            line = bufferedReader.readLine();
        }

        return nl;
    }


    public static boolean checker(String filename,boolean overwrite){
        boolean value = false;
        File f = new File(filename);
        if(f.exists()&& !f.isDirectory()){
            value=false;
        }else{
            value=true;
        }

        return value;
    }

    public static void writeLoc(String filename,boolean overwrite,ArrayList<Location> arr){

        try {
            //"Text/cities.txt"
            overwrite = checker(filename,overwrite);
            File f = new File(filename);
            if(overwrite){
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(f,overwrite));

            for (Location w : arr) {
                writer.write(w.toString());
                writer.newLine();
            }
            writer.close();
            System.out.println("Write Loc done!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCitiesCon(String filename, boolean overwrite,ArrayList<Connection> arrCon) {
        try {
            //"Text/cities.txt"
            overwrite = checker(filename,overwrite);
            File f = new File(filename);
            if(overwrite){
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (Connection w : arrCon) {
                writer.write(w.toString());
                writer.newLine();
            }
            writer.close();
            System.out.println("Write Con done!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeCitiesToFile(String filename, boolean overwrite, ArrayList<Location> cities){
        try {
            //"Text/cities.txt"
            overwrite = checker(filename,overwrite);
            File f = new File(filename);
            if(overwrite){
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(f,overwrite));

            for (Location w : cities) {
                writer.write(w.toString());
                writer.newLine();
            }
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
