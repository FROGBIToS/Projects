package GUI;

import Workers.Dyrektor;
import Workers.Menedzer;
import Workers.Pisarz.Artykul;
import Workers.Pisarz.Pisarz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PisarzFrame extends JFrame {
    public PisarzFrame(Pisarz pisarz) {
        setTitle("Pisarz Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(650,650));
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new CircleBtn("Log out", Color.RED);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame myGUI = new MainFrame();
                myGUI.setVisible(true);
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel infoLabel = new JLabel("Główna informacja");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(infoLabel, BorderLayout.CENTER);

        JLabel pisarzLabel = new JLabel("Pisarz: " + pisarz.getImie() + ", " + pisarz.getNazwisko());
        pisarzLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(pisarzLabel, BorderLayout.EAST);

        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Data zatrudnienia:" + pisarz.getDataZatrudnienia().toString());
        JLabel surnameLabel = new JLabel("Pencja:" + String.valueOf(pisarz.getPensja()));
        JLabel countWrittenLabel = new JLabel("Ilość napisanych:" + String.valueOf(Artykul.getAllArtykulByPisarz(pisarz.getId()).size()));

        infoPanel.add(nameLabel);
        infoPanel.add(surnameLabel);
        infoPanel.add(countWrittenLabel);

        contentPane.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton articlesButton = new CircleBtn("Napisane artykuły", Color.YELLOW);

        buttonPanel.add(articlesButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        articlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PisarzArtykulyListGUI pisarzArtykykulyListGUI = new PisarzArtykulyListGUI(pisarz);
                pisarzArtykykulyListGUI.setVisible(true);
                dispose();
            }
        });
    }
}
