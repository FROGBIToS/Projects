import javax.swing.*;

public class MenuFrame extends JFrame {

    private MenuView mv = new MenuView(this);

    public MenuFrame(){
        super("MENU");
        setSize(700,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mv);
    }

    public MenuView getMv() {
        return mv;
    }
}
