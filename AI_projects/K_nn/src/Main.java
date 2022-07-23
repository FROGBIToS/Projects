import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static int parametr_k;
    public static Map<String, List<List<Float>>> trainMap = null;
    public static Map<String, List<List<Float>>> testMap = null;

    public static void main(String[] args) {
        parametr_k = Integer.parseInt(args[0]);
        final String path_To_Train_Set = args[1];
        final String path_To_Test_Set = args[2];

        trainMap = sort(readFile(path_To_Train_Set));
        testMap = sort(readFile(path_To_Test_Set));

        List<String> tmpList = testMap.keySet().stream().collect(Collectors.toList());
        int all = 0;
        int poprawne = 0;
        for (int k = 0; k < tmpList.size(); k++) {
            for (int i = 0; i < testMap.get(tmpList.get(k)).size(); i++) {
                System.out.println(tmpList.get(k) + " " + whatGr(testMap.get(tmpList.get(k)).get(i),trainMap));
                all++;
                if (tmpList.get(k).equals(whatGr(testMap.get(tmpList.get(k)).get(i),trainMap))){
                    poprawne++;
                }
            }
        }
        System.out.println("accuracy->" + (double)poprawne/all);
        System.out.println();

        while (true){
            Scanner in = new Scanner(System.in);
            String str = in.nextLine();
            if (str.length() > 0){

                String[] strMass = str.split(";");
                List<Float> flList = new ArrayList<>();

                for (String s : strMass) {
                    flList.add(Float.parseFloat(s));
                }
                System.out.println(whatGr(flList, trainMap));
            }
        }
    }

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

    public static Map<String, List<List<Float>>> sort(List<String> mainList) {

        Map<String, List<List<Float>>> map = new HashMap<>();
        List<String> groupList = new ArrayList<>();



        for (String s : mainList) {
            if (!s.equals("")) {
                String[] str = s.split(";");

                if (!groupList.contains(str[str.length-1])){
                    groupList.add(str[str.length-1]);
                }
            }
        }
        groupList.stream().forEach(x -> {
            map.put(x, new ArrayList<>());
        });

        for (String s : mainList) {
            if (!s.equals("")){
            String[] str = s.split(";");
            List<Float> flList = new ArrayList<>();
            for (int i = 0; i < str.length - 1; i++) {
                flList.add(Float.parseFloat(str[i]));
            }

            map.get(str[str.length-1]).add(flList);
        }
        }

        return map;
    }

    public static double getDistance(List<Float> vek1, List<Float> vek2) {
        if (vek1.size() != vek2.size()) {
            System.out.println(vek1.size());
            System.out.println(vek2.size());
            throw new IllegalArgumentException("Not the same vektors!");
        }

        double suma = 0;
        for (int i = 0; i < vek1.size(); i++) {
            suma += Math.sqrt(Math.pow(vek1.get(i) - vek2.get(i), 2));
        }

        return suma;
    }

    public static String whatGr(List<Float> list, Map<String, List<List<Float>>> map) {

        Map<Double, String> doubleList = new TreeMap<>();
        map.keySet().stream().forEach(x -> {
            for (int j = 0; j < map.get(x).size(); j++) {
                    doubleList.put(getDistance(map.get(x).get(j), list), x);
            }
        });

        List<Double> tmpListOfKey = doubleList.keySet().stream().limit(parametr_k).collect(Collectors.toList());

        List<String> grups = new ArrayList<>();

        for (Double d : tmpListOfKey) {
            grups.add(doubleList.get(d));
        }

        Map<String, Long> tmpMap = grups.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long max = Collections.max(tmpMap.values());

        return findingKeyOfVal(tmpMap, max);

    }

    public static String findingKeyOfVal(Map<String, Long> map, Long num){
        Set<Map.Entry<String, Long>> entrySet=map.entrySet();


        for (Map.Entry<String, Long> pair : entrySet) {
            if (num.equals(pair.getValue())) {
                return pair.getKey();
            }
        }
        return null;
    }

}