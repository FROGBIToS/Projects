import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double parametr_a = Double.parseDouble(args[0]);
        String pathToData = args[1];
        Map<String, List<List<Double>>> mapLeng = Util.readFiles(pathToData);

        Warstwa war = new Warstwa(mapLeng,parametr_a,1);

        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Put your text -> :");

            String outStr = "";
            String str = sc.nextLine();
            do{
                outStr = outStr+str;
                str = sc.nextLine();
            }while (str != "");

            System.out.println("Сonclusion -> " + war.getGrup(outStr));
        }

    }
}
