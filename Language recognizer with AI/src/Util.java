import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    public static Map<String, List<List<Double>>> readFiles(String readPath) {
        Map<String, List<List<Double>>> map = new HashMap<>();

        try {

            Files.walk(Paths.get(readPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(x-> {
                        if (!map.containsKey(x.getParent().replaceAll(".*\\\\",""))){
                            map.put(x.getParent().replaceAll(".*\\\\",""), new ArrayList<>());
                        }

                        try {
                            FileReader fr = new FileReader(x);
                            BufferedReader reader = new BufferedReader(fr);
                            String line = reader.readLine();

                            List<String> listStr = new ArrayList<>();
                            while (line != null) {
                                listStr.add(line);
                                line = reader.readLine();
                            }
                            map.get(x.getParent().replaceAll(".*\\\\","")).add(countLitery(listStr));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Double> countLitery(List<String> list){

        char[] litery = {'a','b','c','d','e', 'f', 'g', 'h', 'i', 'j', 'k', 'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};


        double[] countChars = new double[26];
        List<Double> charsList = new ArrayList<>();

        for (String str : list) {
            str.toLowerCase(Locale.ROOT);
            char[] charsInLine = str.toCharArray();
            for (int i = 0; i < charsInLine.length; i++) {
                for (int j = 0; j < litery.length; j++) {
                    if (charsInLine[i] == litery[j]){
                        countChars[j] = countChars[j]+1D;
                    }
                }
            }
        }
        int suma = 0;
        for (int i = 0; i < countChars.length; i++) {
            suma += countChars[i];
        }
        for (int i = 0; i < countChars.length; i++) {
            charsList.add(countChars[i]/suma);
        }

        return charsList;
    }

    public static int getMaxI(double[] mass){
        int maxI = 0;
        for (int i = 0; i < mass.length-1; i++) {
            if (mass[maxI] < mass[i+1]){
                maxI = i+1;
            }
        }
        return maxI;
    }

}