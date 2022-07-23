
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyObjektss{

    private ArrayList<File> enemyImages = new ArrayList<>();
    private HashMap <Integer, File> dinoImages = new HashMap<Integer, File>();
    private HashMap <Integer, File> landImages = new HashMap<Integer, File>();

    public void downloadDinoImage(){
        try {
            dinoImages.put(1, new File("Data/IMAGES/DINOANIM1.png"));
            dinoImages.put(2, new File("Data/IMAGES/DINOANIM2.png"));
            dinoImages.put(3, new File("Data/IMAGES/DINOANIM3.png"));
            dinoImages.put(4, new File("Data/IMAGES/DINOANIMDOWN01.png"));
            dinoImages.put(5, new File("Data/IMAGES/DINOANIMDOWN02.png"));
            dinoImages.put(6, new File("Data/IMAGES/DINOANIMLOSE.png"));
            System.out.println("DINO IMAGE : DONE ");
        } catch (Exception e) {
            System.err.println("DINO IMAGE : FAIL ");
        }
    }

    public void downloadEnemiesImage(){
        try {
            enemyImages.add(new File("Data/IMAGES/KAKASZKA.png"));
            enemyImages.add(new File("Data/IMAGES/CHLOPCHORY.png"));
            enemyImages.add(new File("Data/IMAGES/CHLOPENDERMAN.png"));
            enemyImages.add(new File("Data/IMAGES/CHLOPFAJKA.png"));
            enemyImages.add(new File("Data/IMAGES/FIRE.png"));
            enemyImages.add(new File("Data/IMAGES/MANYCHLOPGREENBLOOD.png"));
            enemyImages.add(new File("Data/IMAGES/MANYCHLOPGREENBLOOD.png"));
            enemyImages.add(new File("Data/IMAGES/MANYCHLOPMAGIC.png"));
            enemyImages.add(new File("Data/IMAGES/MANYCHLOPOLENI.png"));
            System.out.println("ENEMIES IMAGE : DONE ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadLandImage(){
        try {
            landImages.put(1, new File("Data/IMAGES/OBLAKO.png"));
            landImages.put(2, new File("Data/IMAGES/OBLAKODOSC.png"));
            landImages.put(3, new File("Data/IMAGES/OBLAKOROZOWE.png"));
            landImages.put(4, new File("Data/IMAGES/OBLAKOSIRE.png"));
            landImages.put(5, new File("Data/IMAGES/OBLAKOZOWTE.png"));
            landImages.put(6, new File("Data/IMAGES/SKELETON1.png"));
            landImages.put(7, new File("Data/IMAGES/SKELETON2.png"));
            landImages.put(8, new File("Data/IMAGES/SKELETON3.png"));
            landImages.put(9, new File("Data/IMAGES/SKELETON4.png"));
            System.out.println("LAND IMAGE : DONE ");
        } catch (Exception e) {
            System.err.println("LAND IMAGE : FAIL ");
        }
    }


    public List<File> getEnemyImages() {
        return enemyImages;
    }
    public HashMap<Integer, File> getDinoImages() {
        return dinoImages;
    }
    public HashMap<Integer, File> getLandImages() {
        return landImages;
    }
}