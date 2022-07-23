import javax.swing.*;

public class DinoWindowGame extends JFrame{

    private DinoGameView dinoGameView = new DinoGameView();

    public DinoWindowGame(){
        super("DINOGAME");
        setSize(700,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(dinoGameView);
        setVisible(false);
    }

    public void viewStart(){dinoGameView.viewStart();}
}
