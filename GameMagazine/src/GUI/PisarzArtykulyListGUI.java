package GUI;

import Utils.Status;
import Workers.Pisarz.Artykul;
import Workers.Pisarz.Pisarz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class PisarzArtykulyListGUI extends JFrame {

    public PisarzArtykulyListGUI(Pisarz pisarz) {
        setTitle("Napisane artykuły");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(650,650));
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton backButton = new CircleBtn("Powrót", Color.RED);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PisarzFrame pisarzGUI = new PisarzFrame(pisarz);
                pisarzGUI.setVisible(true);
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Artykuły");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel pisarzLabel = new JLabel("Pisarz: " + pisarz.getImie() + ", " + pisarz.getNazwisko());
        pisarzLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(pisarzLabel, BorderLayout.EAST);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        List<Artykul> artykuly = Artykul.getAllArtykulByPisarz(pisarz.getId());

        Collections.sort(artykuly, new Comparator<Artykul>() {
            @Override
            public int compare(Artykul a1, Artykul a2) {
                boolean isRozpatrywanie1 = a1.isRozpatrywanie();
                boolean isRozpatrywanie2 = a2.isRozpatrywanie();
                Status czyZaakceptowany1 = a1.getCzyZaakceptowany();
                Status czyZaakceptowany2 = a2.getCzyZaakceptowany();

                if (isRozpatrywanie1 && !isRozpatrywanie2) {
                    return -1;
                } else if (!isRozpatrywanie1 && isRozpatrywanie2) {
                    return 1;
                } else if (czyZaakceptowany1.getValue().equals("W trakcie") && !czyZaakceptowany2.getValue().equals("W trakcie")) {
                    return -1;
                } else if (!czyZaakceptowany1.getValue().equals("W trakcie") && czyZaakceptowany2.getValue().equals("W trakcie")) {
                    return 1;
                } else {
                    return a1.getDataNapisania().compareTo(a2.getDataNapisania());
                }
            }
        });


        for (Artykul artykul : artykuly) {
            JPanel buttonContainer = new JPanel(new BorderLayout());
            JButton buttonSend = new CircleBtn("Odesłać na sprawdzenie", Color.BLUE);
            JButton buttonDetails = new CircleBtn("Szczegóły", Color.GREEN);

            JLabel label = new JLabel(artykul.getNazwa() + "|" + artykul.getOpis() + "|" + artykul.getCzyZaakceptowany() + "|" + artykul.getDataNapisania().toString());

            JPanel buttonSubPanel = new JPanel();
            buttonSubPanel.setLayout(new BorderLayout());

            buttonDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArtykulDetailsGUI artykulDetailsGUI = new ArtykulDetailsGUI(artykul, pisarz);
                    artykulDetailsGUI.setVisible(true);
                }
            });

            buttonSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    artykul.odeslacNaSprawdzenie();
                    artykul.updateToDB();

                    JOptionPane.showMessageDialog(PisarzArtykulyListGUI.this, artykul.getNazwa() + " był wysłany na sprawdzenie");
                    dispose();
                    PisarzArtykulyListGUI newGUI = new PisarzArtykulyListGUI(pisarz);
                    newGUI.setVisible(true);


                }
            });

            buttonSubPanel.add(buttonDetails, BorderLayout.EAST);
            if (artykul.isRozpatrywanie()) {
                buttonSubPanel.add(buttonSend, BorderLayout.WEST);
            }

            buttonContainer.add(label, BorderLayout.CENTER);
            buttonContainer.add(buttonSubPanel, BorderLayout.EAST);
            buttonSend.setMaximumSize(new Dimension(buttonSend.getMaximumSize().width, 30));
            buttonDetails.setMaximumSize(new Dimension(buttonDetails.getMaximumSize().width, 30));
            buttonContainer.setMaximumSize(new Dimension(buttonContainer.getMaximumSize().width, 30));

            listPanel.add(buttonContainer);

        }

        JScrollPane scrollPane = new JScrollPane(listPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

    }
}

