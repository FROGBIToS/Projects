public class Main {
    public static void main(String[] args) {
        int parametrK = Integer.parseInt(args[0]);
        final String pathFile = args[1];

        KMeans kMeans = new KMeans(pathFile, parametrK);
        kMeans.workKMeans();

    }
}