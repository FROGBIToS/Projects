import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class Dino extends JPanel implements Runnable, KeyListener {

    private BufferedImage imageDino = null;
    private int numerAnimacjiDino = 1;
    //------------------------------------------
    private static float gravity  = 0.1f;
    private float pryrostY;
    private float dinoY;
    private float pozycjaDino = 0;
    //------------------------------------------
    private Thread start;
    private MyObjektss myObjektssDino = DinoGameView.getMyObjektss();

    public Dino() {
        start = new Thread(this::run);
    }


    public void paintDino(Graphics paintingDino){
        paintingDino.drawImage(imageDino, DinoGameView.getLineX()-DinoGameView.getLineX() + 100, (int) pozycjaDino, null);
    }

    @Override
    public void run() {

        new Thread(() -> {
            try {
                while (true) {
                        if(numerAnimacjiDino < 3){  //zwykla Animacja
                            numerAnimacjiDino++;
                        }else{
                            numerAnimacjiDino = 1;
                        }
                        if(pozycjaDino < DinoGameView.getLineY()-43) {  //jump Animacja
                            this.numerAnimacjiDino = 3;
                        }
                        if(!DinoGameView.getGaming()){      //dead Animacja
                            this.numerAnimacjiDino = 6;
                        }

                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    if (!DinoGameView.getGaming()){
                        pryrostY = 0;
                        gravity = 0;
                    }
                    if (pozycjaDino+43 >=  DinoGameView.getLineY()){
                        pryrostY = 0;
                    }else {
                                pryrostY += gravity;
                                dinoY += pryrostY;
                                if (pozycjaDino+imageDino.getHeight() >=  DinoGameView.getLineY()-10 && pryrostY > 0){
                                pryrostY = 1;
                            }
                    }

                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true){
            try {
                imageDino = ImageIO.read(myObjektssDino.getDinoImages().get(numerAnimacjiDino));
                pozycjaDino = DinoGameView.getLineY() - imageDino.getHeight() + dinoY;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (DinoGameView.getGaming()){
        if (e.getKeyCode() == 38 || e.getKeyCode() == 32){
            if (pryrostY == 0){
                pryrostY = -4f;
                dinoY += pryrostY;
            }
        }else
        if (e.getKeyCode() == 40 || e.getKeyCode() == 17){
            pryrostY = 4f;
        }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void dinoStart(){
        start.start();
    }
    public void restart(){
        pozycjaDino = DinoGameView.getLineY()-imageDino.getHeight()+dinoY;
        gravity = 0.1f;
    }
    public float getPozycjaDino() {
        return pozycjaDino;
    }
}