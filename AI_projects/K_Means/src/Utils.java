import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Utils {

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

    public static List<Unit> getListUnits(String readPath){

        List<String> strList = Utils.readFile(readPath);

        List<Unit> unitList = new ArrayList<>();

        for (int i=0; i < strList.size();i++) {

            try {
                String[] tmp = strList.get(i).split(";");
                LinkedList<Double> vektor = new LinkedList<>();

                for (int j = 0; j < tmp.length; j++) {
                    vektor.add(Double.valueOf(tmp[j]));
                }

                unitList.add(new Unit(vektor));
            } catch (NumberFormatException e) {
                System.err.println("Problem with line :" + i);
            }
        }

        return unitList;
    }
}