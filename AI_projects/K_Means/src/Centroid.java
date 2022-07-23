import java.util.LinkedList;

public class Centroid{
    private int group;
    private LinkedList<Double> vektor;


    public Centroid(LinkedList<Double> vektor) {
        this.vektor = vektor;
    }

    public Centroid(int group, LinkedList<Double> vektor) {
        this.group = group;
        this.vektor = vektor;
    }

    public Centroid(Unit unit){
        this.group = unit.getGrup();
        this.vektor = unit.getVektor();
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public LinkedList<Double> getVektor() {
        return vektor;
    }

    public void setVektor(LinkedList<Double> vektor) {
        this.vektor = vektor;
    }

    @Override
    public String toString() {
        return "Centroid{" +
                "group=" + group +
                ", vektor=" + vektor +
                '}';
    }
}
