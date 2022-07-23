import java.util.*;

public class KMeans {

    String pathFile;
    List<Unit> unitList;
    double E = -1;
    int parametrK;

    public KMeans(String pathFile, int parametrK) {
        this.pathFile = pathFile;
        this.unitList = Utils.getListUnits(pathFile);
        this.parametrK = parametrK;
    }

    public void workKMeans() {
        //1
        List<Centroid> centroids = chooseRandomCentroids();

        double newE = 0;

        while (E != newE){
            E = newE;
            //2
            for (Unit unit : unitList) {
                unit.setGrup(getGroupWithMinDistance(centroids, unit));
            }

            Map<Integer, List<Unit>> unitMap = divideIntoMap();

            for (Integer i : unitMap.keySet()) {
                System.out.println("Group " + i + " ->" + unitMap.get(i).size());
            }
            //3
            centroids = findNewCertroids(unitMap);
            //4
            newE = calculateE(centroids, unitMap);
            System.out.println("E -> " + newE);

        }
    }

    private List<Centroid> chooseRandomCentroids(){

        Random random = new Random();
        List<Centroid> centroids = new ArrayList<>();

        for (int i = 0; i < parametrK; i++) {
            int randomInt = random.nextInt(unitList.size());

            if (!centroids.contains(unitList.get(randomInt))){
                Centroid centroid = new Centroid(unitList.get(randomInt));
                centroid.setGroup(i);
                centroids.add(centroid);
            }else{
                i--;
            }
        }
        return centroids;

    }

    private double distansEuq(Centroid centroid, Unit unit){
        double distance = 0;
        for (int i = 0; i < centroid.getVektor().size(); i++) {
            distance += Math.pow(centroid.getVektor().get(i) - unit.getVektor().get(i),2);
        }

        return Math.sqrt(distance);
    }

    private int getGroupWithMinDistance(List<Centroid> centroidList, Unit unit){

        Map<Integer, Double> distancesToAllCentroid = new HashMap<>();

        for (int i = 0; i < centroidList.size(); i++) {
            distancesToAllCentroid.put(centroidList.get(i).getGroup(), Math.pow(distansEuq(centroidList.get(i),unit),2));
        }

        List<Integer> grups = distancesToAllCentroid.keySet().stream().toList();

        int minGroupDistance = grups.get(0);

        for (int i = 0; i < grups.size(); i++) {

            if (distancesToAllCentroid.get(grups.get(i)) < distancesToAllCentroid.get(minGroupDistance)){
                minGroupDistance = grups.get(i);
            }
        }
        return minGroupDistance;
    }

    private List<Centroid> findNewCertroids(Map<Integer,List<Unit>> unitMap){

        List<Centroid> newCentroids = new ArrayList<>();

        for (int i : unitMap.keySet()) {
            LinkedList<Double> tmpList = new LinkedList<>();

            for (Unit unit : unitMap.get(i)) {

                if (tmpList.size() == 0){
                    for (int j = 0; j < unit.getVektor().size(); j++) {
                        tmpList.add(0D);
                    }
                }

                for (int j = 0; j < unit.getVektor().size(); j++) {
                    double tmpSum = tmpList.get(j) + unit.getVektor().get(j);
                    tmpList.remove(j);
                    tmpList.add(j,tmpSum);
                }
            }


            for (int j = 0; j < tmpList.size(); j++) {
                double tmpDouble = tmpList.get(j) / unitMap.get(i).size();
                tmpList.remove(j);
                tmpList.add(j,tmpDouble);
            }
            newCentroids.add(new Centroid(i, tmpList));
        }

        return newCentroids;
    }

    private double calculateE(List<Centroid> centroids,Map<Integer,List<Unit>> unitMap){

        double E = 0;

        for (int i = 0; i < unitMap.size(); i++) {
            double sum = 0;
            for (int j = 0; j < unitMap.get(i).size(); j++) {
                    sum += Math.pow(distansEuq(centroids.get(i), unitMap.get(i).get(j)),2);
            }
            E += sum;
        }

        return E;
    }

    private Map<Integer, List<Unit>> divideIntoMap(){

        Map<Integer,List<Unit>> unitMap = new HashMap<>();

        for (int i = 0; i < parametrK; i++) {
            unitMap.put(i, new ArrayList<>());
            for (int j = 0; j < unitList.size(); j++) {
                if (i == unitList.get(j).getGrup()){
                    unitMap.get(i).add(unitList.get(j));
                }
            }
        }

        return unitMap;
    }

}
