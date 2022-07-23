import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static double parametr_a;
    public static Map<String, List<List<Float>>> trainMap = null;
    public static Map<String, List<List<Float>>> testMap = null;
    public static int iloscArg = 0;

    public static void main(String[] args) {
        parametr_a = Double.parseDouble(args[0]);
        final String path_To_Train_Set = args[1];
        final String path_To_Test_Set = args[2];

        trainMap = sort(readFile(path_To_Train_Set));
        testMap = sort(readFile(path_To_Test_Set));

        Perceptron perceptron = new Perceptron(parametr_a, iloscArg);
        perceptron.trainPerceptron(trainMap);
        perceptron.testPerceptron(testMap);

        boolean times = true;

        while (true){
            Scanner s = new Scanner(System.in);
            if (times){
                System.out.print("If you want to increase accuracy please write new accuracy: ");
                String newAccur =  s.nextLine();
                if (newAccur != "" && Double.parseDouble(newAccur) <= 1.0){
                    while (perceptron.accuracy < Double.parseDouble(newAccur)){
                        perceptron.trainPerceptron(trainMap);
                    }
                    perceptron.testPerceptron(testMap);
                    times = false;
                }
            }
            System.out.println("Write your vektor");
            perceptron.whatGrup(s.nextLine());

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

        String[] str = null;
        for (String s : mainList) {
            if (!s.equals("")){
                str = s.split(";");
                List<Float> flList = new ArrayList<>();
                for (int i = 0; i < str.length - 1; i++) {
                    flList.add(Float.parseFloat(str[i]));
                }
                map.get(str[str.length-1]).add(flList);
            }
        }
        iloscArg = str.length-1;

        return map;
    }

}