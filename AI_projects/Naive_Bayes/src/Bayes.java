import java.util.*;

public class Bayes {
    private List<HashSet<String>> attributes;
    private Map<String, Integer> answers;
    private int sumAnswers;
    private List<Map<String, Map<String, Integer>>> mainList;


    public Bayes(String pathFileTrain){
        this.attributes = Util.getAllAttributes(pathFileTrain);
        this.mainList = Util.doListMap(pathFileTrain);
        this.answers = Util.getCountAnswer(pathFileTrain);
        sumAnswers = getSumInMap(answers);
    }

    public void testAlgorithm(String pathTest){
        List<String> strList = Util.readFile(pathTest);
        for (String str: strList) {
            System.out.println(str + " -> " + getAnswer(str));
        }
    }

    public String getAnswer(String str){

        if (isAttributes(str)) {
            Map<String, Double> chances = new HashMap<>();

            String[] strings = str.split(",");
            double chance = 0;
            for (String answer : answers.keySet()) {

//                System.out.println("For answer [" + answer + "] we have got \n{");

                chance = (double) answers.get(answer) / sumAnswers;

                for (int j = 0; j < strings.length; j++) {

                    if ((double) mainList.get(j).get(strings[j]).get(answer) / answers.get(answer) == 0) {
                        chance *= laplace(j, answers.get(answer));
                    } else {
                        chance *= (double) mainList.get(j).get(strings[j]).get(answer) / answers.get(answer);
                    }

//                    System.out.println(strings[j] + " : " + mainList.get(j).get(strings[j]) + " --> " + chance);

                }
                chances.put(answer, chance);
//                System.out.println("}\nchanse for [" + answer + "] = " + chance);
//                System.out.println("------------------------------------------------------");
            }

            return Util.getBiggerKey(chances);
        }

        return "Podano zle atrybuty!";

    }

    private int getSumInMap(Map<String, Integer> listMaps){
        int sumAnswers = 0;

        for (String answer: listMaps.keySet()) {
            sumAnswers += listMaps.get(answer);
        }

        return sumAnswers;
    }

    private double laplace(int numberAtrybut, int answerNumber){
        double outDouble = 0.00000001;

        outDouble = (double) 1/(answerNumber + mainList.get(numberAtrybut).size());

        return outDouble;
    }

    private boolean isAttributes(String strMain){
        boolean des = true;
        String[] strings = strMain.split(",");
        for (int i = 0; i < strings.length; i++) {
                for (int k = 0; k < attributes.get(i).size(); k++) {
                    if (!attributes.get(i).contains(strings[i])){
                        des = false;
                    }
            }
        }
        return des;
    }

}
