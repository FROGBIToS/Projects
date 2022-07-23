import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DinoGameView extends JPanel implements Runnable {

    private static Dino dino;
    private float pozXdinoHead;
    private float pozXdinoBack;
    private float pozYdino;
    //++++++++++++++++++++++++
    private static Enemies enemies;
    private float pozXenemy1Head;
    private float pozXenemy1Back;
    private float pozXenemy2Head;
    private float pozXenemy2Back;
    private float pozXenemy3Head;
    private float pozXenemy3Back;
    private float pozYenemy1;
    private float pozYenemy2;
    private float pozYenemy3;
    //+++++++++++++++++++++++++
    private Land land;
    //---------------------
    private static int lineY;
    private static int lineX;
    private static boolean gaming = true;
    private static float score;
    private String previousRekord;
    private String rekord;
    private boolean newRekord = false;
    private boolean forRekording = true;
    private boolean forButtons = true;
    //---------------------
    private Thread viewStart;
    private static MyObjektss myObjektss = new MyObjektss();

    public DinoGameView() {
        setBackground(Color.LIGHT_GRAY);
        //----------------------------------
        myObjektss.downloadEnemiesImage();
        myObjektss.downloadDinoImage();
        myObjektss.downloadLandImage();
        //----------------------------------
        dino = new Dino();
        addKeyListener(dino);
        setFocusable(true);
        //----------------------------------
        enemies = new Enemies();
        //----------------------------------
        land = new Land();
        //----------------------------------
        viewStart = new Thread(this::run);
    }

    public void paint(Graphics dinoGraphics){
        if (gaming){
            super.paint(dinoGraphics);
            Font scoreFont = new Font("Courier New", 1, lineY/10);
            dinoGraphics.setFont(scoreFont);
            dinoGraphics.drawString("score :" + (int)score, 0,30);
            dinoGraphics.setColor(new Color(0, 0, 0));
            score += 0.1;
            dino.paintDino(dinoGraphics);
            land.paintLand(dinoGraphics);
            enemies.paintEnemies(dinoGraphics);
            dinoGraphics.drawLine(0, lineY, lineX,lineY);
        }else{
            super.paint(dinoGraphics);
            Font endFont = new Font("Courier New", 3 ,30);
            dinoGraphics.setFont(endFont);
            dinoGraphics.drawString("GAME OVER", 100,75);
            Font scoreFont = new Font("Courier New", 3 ,20);
            dinoGraphics.setFont(scoreFont);
            if (newRekord){
                dinoGraphics.drawString(MenuView.getPlayer()+", your score -> "+(int)score, 100, 105);
                dinoGraphics.drawString(MenuView.getPlayer()+", NEW REKORD!!!!!! -> " + rekordPlayer(), 100, 145);
                dinoGraphics.drawString(MenuView.getPlayer()+", previous rekord -> " + previousRekord, 100, 125);
            }else{
                dinoGraphics.drawString(MenuView.getPlayer()+", your score -> "+(int)score, 100, 105);
                dinoGraphics.drawString(MenuView.getPlayer()+", your rekord -> " + rekordPlayer(), 100, 125);
            }
            setBackground(Color.RED);
            buttonsRestart();
            dino.paintDino(dinoGraphics);
            enemies.paintEnemies(dinoGraphics);
            dinoGraphics.drawLine(0, lineY, lineX,lineY);
        }

    }

    public void buttonsRestart(){
        if (forButtons){
            JButton againButton = new JButton();
            againButton.setText("AGAIN");
            againButton.setBounds(400,20,70,60);
            againButton.setVisible(true);
            add(againButton);
            againButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DinoGameView.gaming = true;
                    enemies.restart();
                    land.restart();
                    dino.restart();
                    score = 0;
                    againButton.setVisible(false);
                    forButtons = true;
                    forRekording = true;
                    newRekord = false;
                    setBackground(Color.LIGHT_GRAY);
                }
            });
            forButtons = false;
        }
    }

    public void viewStart(){
        if (gaming){
            viewStart.start();
            dino.dinoStart();
            enemies.enemiesStart();
            land.landStart();
        }
    }

    public String rekordPlayer(){
        if (forRekording){
        try {
            FileReader fr = new FileReader("players.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            List<String> playersList = new ArrayList<>();
            while (line != null){
                String tmpTab[] = line.split(";");
                if (score > Long.parseLong(tmpTab[1]) && tmpTab[0].equals(MenuView.getPlayer())){
                    previousRekord = tmpTab[1];
                    tmpTab[1] = String.valueOf((int)score);
                    newRekord = true;
                    rekord = tmpTab[1];
                }else if(tmpTab[0].equals(MenuView.getPlayer())){
                    rekord = tmpTab[1];
                }
                playersList.add(tmpTab[0] +";" + tmpTab[1]);
                line = reader.readLine();
            }
            FileWriter fw = new FileWriter("players.txt",false);
            for (int i = 0; i < playersList.size(); i++) {
                fw.write(playersList.get(i) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        forRekording = false;
        }
        return rekord;
    }

    @Override
    public void run() {
        while(true){
            try {
                lineY = this.getHeight() - this.getHeight()/9;
                lineX = this.getWidth();
                pozXdinoHead = lineX-lineX + 130;
                pozXdinoBack = lineX-lineX + 110;
                pozYdino = dino.getPozycjaDino() + 43;
                pozXenemy1Head = enemies.unitOfEnemies.get(0).getPozycjaEnemies();
                pozXenemy2Head = enemies.unitOfEnemies.get(1).getPozycjaEnemies();
                pozXenemy3Head = enemies.unitOfEnemies.get(2).getPozycjaEnemies();
                pozXenemy1Back = enemies.unitOfEnemies.get(0).getPozycjaEnemies() + enemies.unitOfEnemies.get(0).buff.getWidth();
                pozXenemy2Back = enemies.unitOfEnemies.get(1).getPozycjaEnemies()+ enemies.unitOfEnemies.get(1).buff.getWidth();
                pozXenemy3Back = enemies.unitOfEnemies.get(2).getPozycjaEnemies()+ enemies.unitOfEnemies.get(2).buff.getWidth();
                pozYenemy1 = lineY - enemies.unitOfEnemies.get(0).buff.getHeight();
                pozYenemy2 = lineY - enemies.unitOfEnemies.get(1).buff.getHeight();
                pozYenemy3 = lineY - enemies.unitOfEnemies.get(2).buff.getHeight();
                if (pozXdinoHead >= pozXenemy1Head && pozXdinoBack <= pozXenemy1Back && pozYdino >= pozYenemy1){
                    gaming = false;
                }else if(pozXdinoHead >= pozXenemy2Head && pozXdinoBack <= pozXenemy2Back && pozYdino >= pozYenemy2){
                    gaming = false;
                }else if(pozXdinoHead >= pozXenemy3Head && pozXdinoBack <= pozXenemy3Back && pozYdino >= pozYenemy3){
                    gaming = false;
                }
                if (gaming){

                }
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getLineY() {
        return lineY;
    }
    public static int getLineX() {
        return lineX;
    }
    public static float getScore() {
        return score;
    }
    public static boolean getGaming() {
        return gaming;
    }
    public static MyObjektss getMyObjektss() {
        return myObjektss;
    }
}