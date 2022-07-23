import java.util.*;

public class Perceptron {

    double[] wags;
    double prog;
    double parametr_a;
    double accurancyPerceptron = 0;
    String grup = "";

    public Perceptron(String grup, double parametr_a, double[] wags, double prog) {

        this.wags = new double[wags.length];

        for (int i = 0; i < wags.length; i++) {
            this.wags[i] = wags[i];
        }
        this.prog = prog;

        this.grup = grup;
        this.parametr_a = parametr_a;
    }

    public void trainPerceptron(Map<String, List<List<Double>>> trainMap){
        String[] grups = trainMap.keySet().toArray(new String[0]);

        int notOk = 0;
        int all = 0;

        for (String x: grups) {
            for (int i = 0; i < trainMap.get(x).size(); i++) {
                double sum = 0;
                all++;
                List<Double> list = new ArrayList<>();

                for (int j = 0; j < trainMap.get(x).get(i).size(); j++) {

                    sum += trainMap.get(x).get(i).get(j)*wags[j];

                    list.add(trainMap.get(x).get(i).get(j));

                    if (j == trainMap.get(x).get(i).size()-1){
                        int y;
                        if (sum >= prog){
                            y = 1;
                        }else{
                            y = 0;
                        }

                        if (y == 1 && !x.equals(grup)){
                            regDelta(y, list);
                            notOk++;
                        }
                        else if (y == 0 && x.equals(grup)){
                            regDelta(y,list);
                            notOk++;
                        }
                    }

                    accurancyPerceptron = (double) (all-notOk)/all;
                }
            }
        }
    }

    public double getNet(String str){
        List<String> list = Arrays.asList(str);
        List<Double> doubleList = Util.countLitery(list);

        double sum = 0;
        for (int i = 0; i < doubleList.size(); i++) {
            sum += doubleList.get(i)*wags[i];
        }

        return sum;
    }

    public double getAccurancyPerceptron() {
        return accurancyPerceptron;
    }

    public void regDelta(int y, List<Double> list){
        int d;
        if (y == 1){
            d = 0;
        }else{
            d = 1;
        }

        for (int k = 0; k < wags.length; k++) {
            wags[k] = wags[k] + (d-y)*parametr_a * list.get(k);
        }
    }

}
