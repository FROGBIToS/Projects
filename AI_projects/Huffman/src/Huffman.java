import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Huffman {

    private PriorityQueue<HuffmanNode> queue;
    private boolean addInfo = false;

    public Huffman(List<String> strFromFile,boolean addInfo) {

        this.queue = new PriorityQueue<>(Integer.valueOf(strFromFile.get(0)),new HaffmanComparator());
        this.addInfo = addInfo;

        for (int i = 1; i < strFromFile.size(); i++) {
            String[] strings = strFromFile.get(i).split(" ");

            char znak = strings[0].charAt(0);
            int iloscWystapien = Integer.valueOf(strings[1]);

            queue.add(new HuffmanNode(znak,iloscWystapien,null,null));
        }
    }

    public void doTree(){

        HuffmanNode root = null;

        while (queue.size() > 1) {

            HuffmanNode leftNode = queue.poll();
            HuffmanNode rightNode = queue.poll();

            HuffmanNode graphFather = new HuffmanNode();

            graphFather.iloscWystapien = leftNode.iloscWystapien + rightNode.iloscWystapien;
            graphFather.znak = '*';
            graphFather.left = leftNode;
            graphFather.right = rightNode;
            root = graphFather;

            if (addInfo){
                System.out.println("Father -> " + graphFather.znak);
                System.out.println("Left -> " + leftNode.znak);
                System.out.println("Right -> " + rightNode.znak);
                System.out.println("-------");
            }

            queue.add(graphFather);
        }

        if (addInfo){
            System.out.println(root);
        }
        printBitsNodes(root,"");
    }

    public void printBitsNodes(HuffmanNode root, String s) {

        if (root.left == null && root.right == null && root.znak != '*') {

            root.bits = s;
            System.out.println(root.znak + " " + root.bits);

            return;
        }

        printBitsNodes(root.left, s + "0");
        printBitsNodes(root.right, s + "1");
    }
}


class HaffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode right, HuffmanNode left) {
        if (right.iloscWystapien == left.iloscWystapien){
            return  right.znak - left.znak;
        }
        return right.iloscWystapien - left.iloscWystapien;
    }
}