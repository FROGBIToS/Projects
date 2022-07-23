import java.util.List;

public class Main {

    public static void main(String[] args) {
        String pathToFile = args[0];
        List<String> stringList = Utils.readFile(pathToFile);

        for (String str : stringList) {
            System.out.println(str);
        }
        System.out.println("----------------------------------------");
        Huffman huf = new Huffman(stringList,false);

        huf.doTree();
    }
}
