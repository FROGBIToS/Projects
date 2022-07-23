public class HuffmanNode {
    public char znak;
    public int  iloscWystapien;
    public HuffmanNode left;
    public HuffmanNode right;
    public String bits;


    public HuffmanNode() {
    }

    public HuffmanNode(char znak, int iloscWystapien, HuffmanNode left, HuffmanNode right) {
        this.znak = znak;
        this.iloscWystapien = iloscWystapien;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "znak=" + znak +
                ", iloscWystapien=" + iloscWystapien +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}