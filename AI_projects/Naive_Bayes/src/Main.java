import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String trainPath = args[0];
        String testPath = args[1];

        Bayes b = new Bayes(trainPath);
        b.testAlgorithm(testPath);

        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Put your text -> :");
            String str = sc.nextLine();

            System.out.println(b.getAnswer(str));
        }
    }
}
