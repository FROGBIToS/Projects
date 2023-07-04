package GUI;

import javax.swing.*;
import java.awt.*;

class CircleBtn extends JButton {
    private Color color;
    public CircleBtn(String text, Color color) {
        super(text);
        this.color=color;


        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            int newRed = Math.max(red-100, 1);
            int newGreen = Math.max(green-100, 1);
            int newBlue = Math.max(blue-100, 1);

            Color newColor = new Color(newRed, newGreen, newBlue);

            g.setColor(newColor);
        }else {
            g.setColor(color);
        }

        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13, 13);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13,13);
    }
}