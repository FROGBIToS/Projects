import java.util.LinkedList;

public class Unit {

    private int grup = -1;
    private LinkedList<Double> vektor;

    public Unit(LinkedList<Double> vektor) {
        this.vektor = vektor;
    }

    public Unit(int grup, LinkedList<Double> vektor) {
        this.grup = grup;
        this.vektor = vektor;
    }

    public int getGrup() {
        return grup;
    }

    public void setGrup(int grup) {
        this.grup = grup;
    }

    public LinkedList<Double> getVektor() {
        return vektor;
    }

    public void setVektor(LinkedList<Double> vektor) {
        this.vektor = vektor;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "grup=" + grup +
                ", vektor=" + vektor +
                '}';
    }
}