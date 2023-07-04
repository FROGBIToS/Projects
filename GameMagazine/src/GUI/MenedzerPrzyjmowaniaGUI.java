package GUI;

import Akcje.Przyjmowanie;
import Workers.Menedzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenedzerPrzyjmowaniaGUI extends JFrame {

    public MenedzerPrzyjmowaniaGUI(Menedzer menedzer) {
        setTitle("Artykuły do przyjęcia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(650,650));
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton backButton = new CircleBtn("Powrót", Color.RED);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenedzerFrame menedzerFrame = new MenedzerFrame(menedzer);
                menedzerFrame.setVisible(true);
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Artykuły do przyjęcia");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel pisarzLabel = new JLabel("Menedżer: " + menedzer.getImie() + ", " + menedzer.getNazwisko());
        pisarzLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(pisarzLabel, BorderLayout.EAST);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        List<Przyjmowanie> przyjmowania = Przyjmowanie.getPrzyjmowanieByIdMenedzer(menedzer.getId());

        for (Przyjmowanie przyjmowanie : przyjmowania) {
            if (przyjmowanie.isRozpatrywanie()){
            JPanel buttonContainer = new JPanel(new BorderLayout());
            JButton buttonDetails = new CircleBtn("Szczegóły", Color.GREEN);

            JLabel label = new JLabel(przyjmowanie.getArtykul().getNazwa() + "|" + przyjmowanie.getArtykul().getDataNapisania());

            JPanel buttonSubPanel = new JPanel();
            buttonSubPanel.setLayout(new BorderLayout());

            buttonDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArtykulDetailsGUI artykulDetailsGUI = new ArtykulDetailsGUI(przyjmowanie, menedzer);
                    artykulDetailsGUI.setVisible(true);
                    dispose();
                }
            });

            buttonSubPanel.add(buttonDetails, BorderLayout.EAST);
            buttonContainer.add(label, BorderLayout.CENTER);
            buttonContainer.add(buttonSubPanel, BorderLayout.EAST);


            buttonDetails.setMaximumSize(new Dimension(buttonDetails.getMaximumSize().width, 30));
            buttonContainer.setMaximumSize(new Dimension(buttonContainer.getMaximumSize().width, 30));

            listPanel.add(buttonContainer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}