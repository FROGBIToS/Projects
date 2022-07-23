import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends JPanel {
    private JLabel nameGame = new JLabel("DINO GAME");
    private JLabel newPlayer = new JLabel("Write who you are :");
    private JTextField inputPlayer = new JTextField(10);
    private Button startButton = new Button("Let's go");
    //-------------------------------------------------
    private boolean done;
    private boolean writing = true;
    private FileReader fr = null;
    private FileWriter writer = null;
    private List<String> playersList;
    private static String player;



    public MenuView(JFrame frameMenu) {
        setBackground(Color.yellow);
        setLayout(null);
        nameGame.setFont(new Font("Serif",Font.BOLD,35));
        nameGame.setBounds(10,35,300,35);
        newPlayer.setFont(new Font("Serif",Font.BOLD,15));
        newPlayer.setBounds(10,70,150,20);
        inputPlayer.setBounds(145,70,100,20);
        inputPlayer.setText("DEFFFFF");
        startButton.setBounds(450,150,200,100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fr = new FileReader("players.txt");
                    BufferedReader reader = new BufferedReader(fr);
                    String line = reader.readLine();
                    player = inputPlayer.getText();
                    playersList = new ArrayList<>();
                    while (line != null){
                        String tmpStr[] = line.split(";");
                        for (int i = 0; i < 1; i++) {
                            playersList.add(tmpStr[i]);
                        }
                        line = reader.readLine();
                    }
                    for (int i = 0; i < playersList.size(); i++){
                        if (player.equals(playersList.get(i))){
                            writing = false;
                        }
                    }
                    if (writing){
                        writer = new FileWriter("players.txt",true);
                        writer.write(player + ";0" + "\n");
                        writer.close();
                    }
                    done = true;
                    frameMenu.setVisible(false);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        add(nameGame);
        add(newPlayer);
        add(inputPlayer);
        add(startButton);
    }


    public boolean isDone() {
        return done;
    }
    public static String getPlayer() {
        return player;
    }
}
