import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Enemies extends JPanel implements Runnable {

    List<UnitOfEnemy> unitOfEnemies;
    //-----------------------------------
    private int spawnY;
    int spawnX;
    private boolean spawnNumberEnemy0 = true;
    private boolean spawnNumberEnemy1= false;
    private boolean spawnNumberEnemy2 = false;
    int jakiNumer0 = 0;
    int jakiNumer1 = 1;
    int jakiNumer2 = 2;
    //----------------------------------
    private Thread enemiesStart;
    private MyObjektss myObjektssEnemies = DinoGameView.getMyObjektss();

    public Enemies(){
        enemiesStart = new Thread(this::run);
        unitOfEnemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            try {
                UnitOfEnemy jednostka = new UnitOfEnemy();
                jednostka.buff = ImageIO.read(myObjektssEnemies.getEnemyImages().get(i));
                unitOfEnemies.add(jednostka);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void paintEnemies(Graphics paintingEnemies){
        if (spawnNumberEnemy0){
            paintingEnemies.drawImage(unitOfEnemies.get(jakiNumer0).buff, (int) ruchEnemies(unitOfEnemies.get(jakiNumer0),1), spawnY - unitOfEnemies.get(jakiNumer0).buff.getHeight(), null);
        }
        if (spawnNumberEnemy1){
            paintingEnemies.drawImage(unitOfEnemies.get(jakiNumer1).buff, (int) ruchEnemies(unitOfEnemies.get(jakiNumer1),2), spawnY - unitOfEnemies.get(jakiNumer1).buff.getHeight(), null);
        }
        if (spawnNumberEnemy2){
            paintingEnemies.drawImage(unitOfEnemies.get(jakiNumer2).buff, (int) ruchEnemies(unitOfEnemies.get(jakiNumer2),3), spawnY - unitOfEnemies.get(jakiNumer2).buff.getHeight(), null);
        }
    }

    public float ruchEnemies(UnitOfEnemy unit, int id ){
        if (DinoGameView.getGaming()){
            if(unit.getPozycjaEnemies() <= -100){
                unit.setPozycjaEnemies(spawnX);
                try {
                    unit.setBuff(ImageIO.read(myObjektssEnemies.getEnemyImages().get((int)(Math.random()*9))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (spawnX/3 == (int)unit.getPozycjaEnemies() && id == 1 || spawnX/3+1 == (int)unit.getPozycjaEnemies() && id == 1){
                spawnNumberEnemy1 = true;
            }
            if (spawnX/3 == (int)unit.getPozycjaEnemies() && id == 2 || spawnX/3+1 == (int)unit.getPozycjaEnemies() && id == 2){
                spawnNumberEnemy2 = true;
            }
            float x = unit.getPozycjaEnemies();
            x -= 2 + DinoGameView.getScore()/330;
            unit.setPozycjaEnemies(x);
        }
        return unit.getPozycjaEnemies();
    }

    @Override
    public void run() {
        while (true){
            spawnY = DinoGameView.getLineY();
            spawnX = DinoGameView.getLineX();
        }
    }
    public void enemiesStart() {
        enemiesStart.start();
    }

    public void restart(){
        for (int i = 0; i < unitOfEnemies.size(); i++) {
            unitOfEnemies.get(i).setPozycjaEnemies(-unitOfEnemies.get(i).buff.getWidth());
        }
        spawnNumberEnemy1 = false;
        spawnNumberEnemy2 = false;

    }

    public class UnitOfEnemy{
        BufferedImage buff;
        private float pozycjaEnemies = - 90;


        public BufferedImage getBuff() {
            return buff;
        }
        public void setBuff(BufferedImage buff) {
            this.buff = buff;
        }
        public float getPozycjaEnemies() {
            return pozycjaEnemies;
        }
        public void setPozycjaEnemies(float pozycjaEnemies) {
            this.pozycjaEnemies = pozycjaEnemies;
        }


    }
}