import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Land extends JPanel implements Runnable {

    List<UnitOfLand> unitOfLandListUP;
    List<UnitOfLand> unitOfLandListDOWN;
    //---------------------------------------
    private int landX;
    private int landY;
    private int poziom1;
    private int poziom2;
    private int poziom3;
    private int poziom4;
    private int poziom5;
    int poziomy[] = new int[5];
    //--------------------------
    Thread landStart;
    private MyObjektss myObjektssEnemies = DinoGameView.getMyObjektss();


    public Land() {
        landStart = new Thread(this::run);
        unitOfLandListUP = new ArrayList<>();
        for (int k = 0; k < 3; k++) {
            for (int i = 1; i < 6; i++) {
                try {
                    Land.UnitOfLand jednostka = new UnitOfLand();
                    jednostka.land = ImageIO.read(myObjektssEnemies.getLandImages().get(i));
                    unitOfLandListUP.add(jednostka);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //---------------
        unitOfLandListDOWN = new ArrayList<>();
        for (int k = 0; k < 4; k++) {
        for (int i = 6; i < 10; i++) {
            try {
                UnitOfLand jednostka = new UnitOfLand();
                jednostka.land = ImageIO.read(myObjektssEnemies.getLandImages().get(i));
                unitOfLandListDOWN.add(jednostka);
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
        }
    }


    public void paintLand(Graphics paintingOblako){
        for (int i = 0; i < 5; i++) {
        paintingOblako.drawImage(unitOfLandListUP.get(i).land, (int)ruchOblako(unitOfLandListUP.get(i),1),gdzieOblakoY(unitOfLandListUP.get(i)),null);
        }
        //----------------------------
        for (int i = 5; i < 10; i++) {
            paintingOblako.drawImage(unitOfLandListUP.get(i).land, (int)ruchOblako(unitOfLandListUP.get(i),2),gdzieOblakoY(unitOfLandListUP.get(i)),null);
        }
        //---------------------------
        for (int i = 10; i < 15; i++) {
            paintingOblako.drawImage(unitOfLandListUP.get(i).land, (int)ruchOblako(unitOfLandListUP.get(i),3),gdzieOblakoY(unitOfLandListUP.get(i)),null);
        }
        for (int i = 0; i < unitOfLandListDOWN.size(); i++) {
            paintingOblako.drawImage(unitOfLandListDOWN.get(i).land, (int) ruchDno(unitOfLandListDOWN.get(i),i), landY, null);
        }
    }

    public float ruchOblako(UnitOfLand unit, int id){
        if (DinoGameView.getGaming()){
        float x = unit.getPozycjaLandX();
        x -= 0.4 + DinoGameView.getScore()/1500;
        unit.setPozycjaLandX(x);
        if (unit.getPozycjaLandX() < -unit.land.getWidth()){
            switch (id){
                case 1:unit.setPozycjaLandX((float) (landX+Math.random()*200)); break;
                case 2:unit.setPozycjaLandX((float) (200+landX+Math.random()*400)); break;
                case 3:unit.setPozycjaLandX((float) (500+landX+Math.random()*500)); break;
            }
        }
        }
        return unit.getPozycjaLandX();
    }
    public int gdzieOblakoY(UnitOfLand unit){
        if (unit.getPozycjaLandX() >= landX){
             unit.setPozycjaLandY(poziomy[(int)(Math.random()*5)]);
        }
        return unit.getPozycjaLandY();
    }
    public float ruchDno(UnitOfLand unit, int i){
        if (DinoGameView.getGaming()){
        if(unit.getPozycjaLandX() + unit.land.getWidth() < 0){
            unit.setPozycjaLandX(landX + unit.land.getWidth()*i);
        }
        float x = unit.getPozycjaLandX();
        x -= 2 + DinoGameView.getScore()/330;
        unit.setPozycjaLandX(x);
    }
        return unit.getPozycjaLandX();
    }

    @Override
    public void run() {
        while (true){
            landY = DinoGameView.getLineY();
            landX = DinoGameView.getLineX();
            poziomy[0] = poziom1 = 0;
            poziomy[1] = poziom2 = (int) (landY/5 - Math.random()*landY/5);
            poziomy[2] = poziom3 = (int) (landY/5 - Math.random()*landY/5)+poziom2;
            poziomy[3] = poziom4 = (int) (landY/5 - Math.random()*landY/5)+poziom3;
            poziomy[4] = poziom5 = landY/5+poziom4-20;
        }
    }

    public void landStart(){
        landStart.start();
    }
    public void restart(){
        for (int i = 0; i < unitOfLandListUP.size(); i++) {
            unitOfLandListUP.get(i).setPozycjaLandX(-unitOfLandListUP.get(i).land.getWidth());
        }

        for (int i = 0; i < unitOfLandListDOWN.size(); i++) {
            unitOfLandListDOWN.get(i).setPozycjaLandX(landX + i*unitOfLandListDOWN.get(i).land.getWidth());
        }
    }


    public class UnitOfLand{
        BufferedImage land;
        private float pozycjaLandX = landX;
        private  int pozycjaLandY;

        public float getPozycjaLandX() {
            return pozycjaLandX;
        }
        public void setPozycjaLandX(float pozycjaLandX) {
            this.pozycjaLandX = pozycjaLandX;
        }
        public int getPozycjaLandY() {
            return pozycjaLandY;
        }
        public void setPozycjaLandY(int pozycjaLandY) {
            this.pozycjaLandY = pozycjaLandY;
        }
        public BufferedImage getLand() {
            return land;
        }
        public void setLand(BufferedImage land) {
            this.land = land;
        }
    }

}
