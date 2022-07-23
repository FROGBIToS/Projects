import java.util.*;

public class Perceptron {

    double[] wags;
    double prog;
    double parametr_a;
    String[] grups;
    double accuracy;

    public Perceptron(double parametr_a, int rozmiarWektora) {
        this.wags = new double[rozmiarWektora];
        Random r = new Random();

        for (int i = 0; i < wags.length ; i++) {
            wags[i] = r.nextDouble()*2-2;
        }
        this.prog = r.nextDouble()*5-5;
        this.parametr_a = parametr_a;
    }

    public void trainPerceptron(Map<String, List<List<Float>>> trainMap){
        int all = 0;
        int bad = 0;

        this.grups = trainMap.keySet().toArray(new String[0]);

        for (int o = 0; o < grups.length; o++) {
            for (int i = 0; i < trainMap.get(grups[o]).size(); i++) {
                if (i%2 == 0){
                    if (o == 0){
                        o++;
                    }else{
                        o--;
                    }
                }
                double sum = 0;
                List<Float> list = new ArrayList<>();

                for (int j = 0; j < trainMap.get(grups[o]).get(i).size(); j++) {

                    sum += trainMap.get(grups[o]).get(i).get(j)*wags[j];
                    list.add(trainMap.get(grups[o]).get(i).get(j));

                    if (j == trainMap.get(grups[o]).get(i).size()-1){
                        all++;
                        int y;
                        if (sum >= prog){
                            y = 1;
                        }else {
                            y = 0;
                        }

                        if (!grups[y].equals(grups[o])){
                            bad++;
                            int d;
                           if (y == 1){
                               d = 0;
                           }else{
                               d = 1;
                           }

                            for (int k = 0; k < wags.length; k++) {
                                wags[k] = wags[k] + (d-y)*parametr_a * list.get(k);
                            }
                            prog = prog-(d-y)*parametr_a;
                        }
                    }
                }
            }
        }
        this.accuracy = (double) (all-bad)/all;
    }

    public void testPerceptron(Map<String, List<List<Float>>> testMap){

        int all = 0;
        int ok = 0;
        int all01 = 0;
        int ok01 = 0;
        int all02 = 0;
        int ok02 = 0;
        for (String x:grups) {
            for (int i = 0; i < testMap.get(x).size(); i++) {
                double sum = 0;
                List<Float> list = new ArrayList<>();
                all++;
                if (x.equals(grups[0])){
                    all01++;
                }else{
                    all02++;
                }
                for (int j = 0; j < testMap.get(x).get(i).size(); j++) {
                    sum += testMap.get(x).get(i).get(j)*wags[j];
                    list.add(testMap.get(x).get(i).get(j));
                    if (j == testMap.get(x).get(i).size()-1){
                        int y;
                        if (sum >= prog){
                            y = 1;
                        }else {
                            y = 0;
                        }

                        if (grups[y].equals(x)) {
                            ok++;
                            if (x.equals(grups[0])){
                                ok01++;
                            }else{
                                ok02++;
                            }
                        }
                    }
                }
            }
        }
            System.out.println("For all grups --> " + (double)ok/all);
            System.out.println(grups[0] +" --> " + (double)ok01/all01);
            System.out.println(grups[1] +" --> " + (double)ok02/all02);
    }

    public void whatGrup(String str){

        try {
            List<Float> list = new ArrayList<>();
            String[] strT = str.split(";");
            for (String s: strT) {
                list.add(Float.parseFloat(s));
            }

            double sum = 0;
            for (int i = 0; i < list.size() ; i++) {
                sum += list.get(i)*wags[i];
            }
            if (sum >= prog ){
                System.out.println(this.grups[1]);
            }else{
                System.out.println(this.grups[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Zły wektor!");
        }
    }
}
