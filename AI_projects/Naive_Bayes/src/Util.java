import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Util {

    public static List<String> readFile(String readPath) {
        List<String> outList = new ArrayList<>();
        try {
            File file = new File(readPath);

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                outList.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outList;
    }

    public static List<HashSet<String>> getAllAttributes(String readPath){

        List<HashSet<String>> outList = new ArrayList<>();
        List<String> strList = readFile(readPath);

        for (String line : strList) {

                String[] strings = line.split(",");

                for (int i = 0; i < strings.length; i++) {
                    if (outList.size() <= i){
                        outList.add(i, new HashSet<>());
                    }
                    outList.get(i).add(strings[i]);

                }
        }
        return outList;
    }

    public static Map<String, Integer> getCountAnswer(String trainPath){
        List<String> strList = readFile(trainPath);
        Map<String, Integer> outList = new HashMap<>();

        for (int i = 0; i < strList.size(); i++) {
            String[] strings = strList.get(i).split(",");

            if (outList.size() == 0){
                outList.put(strings[strings.length-1], 0);
            }

            for (int j = 0; j < outList.size(); j++) {
                outList.put(strings[strings.length-1], 0);
            }

        }

        for (int i = 0; i < strList.size(); i++) {
            String[] strings = strList.get(i).split(",");
            outList.put(strings[strings.length-1], outList.get(strings[strings.length-1])+1);

        }

        return outList;
    }

    public static List<Map<String,Map<String, Integer>>> doListMap(String readPath){

        List<HashSet<String>> parsedList = getAllAttributes(readPath);
        List<String> strList = readFile(readPath);
        List<Map<String, Map<String, Integer>>> outList = new ArrayList<>();

        for (int i = 0; i < parsedList.size()-1; i++) {

            Iterator<String> iterator = parsedList.get(i).iterator();

            if (outList.size() <= i ){
                outList.add(new HashMap<>());
            }

            while (iterator.hasNext()){
                String strTmp = iterator.next();

                if (!outList.get(i).containsKey(strTmp)){
                    outList.get(i).put(strTmp, new HashMap<>());


                    for (int j = 0; j < parsedList.get(parsedList.size()-1).size(); j++) {

                        List<String> keys = parsedList.get(parsedList.size()-1).stream().toList();

                        for (String key: keys) {
                            outList.get(i).get(strTmp).put(key, 0);
                        }
                    }
                }

            }
        }

        for (String str : strList) {
            String[] strings = str.split(",");

            for (int t = 0; t < strings.length-1; t++) {

                for (int i = 0; i < outList.size(); i++) {
                    for (String artykow : outList.get(i).keySet()) {
                        if (artykow.equals(strings[t])){
                            outList.get(i).get(artykow).put(strings[strings.length-1], outList.get(i).get(artykow).get(strings[strings.length-1]) + 1 );
                        }
                    }
                }
            }
        }

        return outList;
    }

    public static String getBiggerKey(Map<String, Double> map){

        String[] keys = map.keySet().toArray(new String[0]);
        String biggerKey = keys[0];

        for (int i = 1; i < keys.length; i++) {
            if (map.get(biggerKey) < map.get(keys[i])){
                biggerKey = keys[i];
            }
        }

        return biggerKey;
    }

}