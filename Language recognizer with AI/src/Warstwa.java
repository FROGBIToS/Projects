import java.util.*;

public class Warstwa {

    List<Perceptron> listPerceptrons;
    Map<String, List<List<Double>>> mapLeng;
    String[] grups;
    double parametr_a;

    public Warstwa(Map<String, List<List<Double>>> mapLeng,double parametr_a, double acc) {
        this.mapLeng = mapLeng;
        this.parametr_a = parametr_a;
        listPerceptrons = new LinkedList<>();
        grups = mapLeng.keySet().toArray(new String[0]);

        double[] wags = new double[mapLeng.get(grups[0]).get(0).size()];
        double prog;

        Random r = new Random();
        for (int i = 0; i < wags.length; i++) {
            wags[i] = r.nextDouble()*1-1;
        }
        prog = r.nextDouble()*1-1;


        for (int i = 0; i < grups.length; i++) {
            listPerceptrons.add(new Perceptron(grups[i], parametr_a,wags,prog));
        }

        for (int i = 0; i < listPerceptrons.size(); i++) {
            while (listPerceptrons.get(i).accurancyPerceptron < acc){
                listPerceptrons.get(i).trainPerceptron(mapLeng);
            }
            System.out.println(grups[i] + " " + listPerceptrons.get(i).getAccurancyPerceptron() + " accuracy");
        }
    }

    public String getGrup(String str){
        double[] nets = new double[listPerceptrons.size()];
        for (int i = 0; i < listPerceptrons.size(); i++) {
            nets[i] = listPerceptrons.get(i).getNet(str);
            System.out.println("Think that it is -> " + listPerceptrons.get(i).grup + " " + nets[i]);
        }

        return listPerceptrons.get(Util.getMaxI(nets)).grup;
    }
}
