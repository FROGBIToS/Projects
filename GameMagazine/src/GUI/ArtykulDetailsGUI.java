package GUI;

import Akcje.Przyjmowanie;
import Akcje.Zaakceptowanie;
import Workers.Dyrektor;
import Workers.Menedzer;
import Workers.Pisarz.Artykul;
import Workers.Pracownik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtykulDetailsGUI extends JFrame {
    JPanel contentPane = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel();

    public ArtykulDetailsGUI(Object object, Pracownik pracownik) {
        setTitle("Szczegóły artykułu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(650,650));
        setLocationRelativeTo(null);

        switch (object.getClass().getSimpleName()) {
            case "Artykul":
                addInfoToGui((Artykul) object);
                break;
            case "Przyjmowanie":
                Przyjmowanie przyjmowanie = (Przyjmowanie) object;
                addInfoToGui(przyjmowanie.getArtykul());
                addButtonsManager(przyjmowanie, (Menedzer) pracownik);
                break;
            case "Zaakceptowanie":
                System.out.println("ZAAKCEPTOWANIE DETAILS");
                Zaakceptowanie zaakceptowanie = (Zaakceptowanie) object;
                addInfoToGui(zaakceptowanie.getArtykul());
                addButtonsDyrektor(zaakceptowanie, (Dyrektor) pracownik);
                break;
        }
    }

    private void addInfoToGui(Artykul artykul) {
        setContentPane(contentPane);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Tytuł: " + artykul.getNazwa());
        JLabel descriptionLabel = new JLabel("Opis: " + artykul.getOpis());
        JLabel acceptedLabel = new JLabel("Czy zaakceptowany: " + artykul.getCzyZaakceptowany());
        JLabel przyjetyLabel = new JLabel("Czy przyjety: " + artykul.getCzyPrzyjety());
        JLabel dateLabel = new JLabel("Data napisania: " + artykul.getDataNapisania());
        JLabel pisarzLabel = new JLabel("Pisarz: " + artykul.getPisarz().getImie() + " " + artykul.getPisarz().getNazwisko());
        JLabel tekstLabel = new JLabel("Tekst: " + artykul.getTekst());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Artykuł"));
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(titleLabel);
        infoPanel.add(dateLabel);
        infoPanel.add(pisarzLabel);
        infoPanel.add(acceptedLabel);
        infoPanel.add(przyjetyLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(tekstLabel);

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void addButtonsManager(Przyjmowanie przyjmowanie, Menedzer menedzer) {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel managerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        managerPanel.add(new JLabel("Menedżer: " + menedzer.getImie() + " " + menedzer.getNazwisko()));
        topPanel.add(managerPanel, BorderLayout.EAST);

        JButton backButton = new CircleBtn("Powrót", Color.RED);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenedzerPrzyjmowaniaGUI menedzerPrzyjmowaniaGUI = new MenedzerPrzyjmowaniaGUI(menedzer);
                menedzerPrzyjmowaniaGUI.setVisible(true);
                dispose();
            }
        });

        JPanel buttonBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBackPanel.add(backButton);
        topPanel.add(buttonBackPanel, BorderLayout.WEST);

        JButton acceptButton = new CircleBtn("   Przyjąć   ", Color.GREEN);
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                przyjmowanie.przyjac(true);
                przyjmowanie.updateToDB();
                JOptionPane.showMessageDialog(ArtykulDetailsGUI.this, przyjmowanie.getArtykul().getNazwa() + " był przyjęty");
                MenedzerPrzyjmowaniaGUI menedzerPrzyjmowaniaGUI = new MenedzerPrzyjmowaniaGUI(menedzer);
                menedzerPrzyjmowaniaGUI.setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(acceptButton);

        JButton rejectButton = new CircleBtn("Nie przyjąć", Color.RED);
        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                przyjmowanie.przyjac(false);
                przyjmowanie.updateToDB();
                JOptionPane.showMessageDialog(ArtykulDetailsGUI.this, przyjmowanie.getArtykul().getNazwa() + " nie był przyjęty");
                MenedzerPrzyjmowaniaGUI menedzerPrzyjmowaniaGUI = new MenedzerPrzyjmowaniaGUI(menedzer);
                menedzerPrzyjmowaniaGUI.setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(rejectButton, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        downPanel.add(buttonPanel, BorderLayout.EAST);

        contentPane.add(downPanel, BorderLayout.SOUTH);

    }

    private void addButtonsDyrektor(Zaakceptowanie zaakceptowanie, Dyrektor dyrektor) {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel managerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        managerPanel.add(new JLabel("Dyrektor: " + dyrektor.getImie() + " " + dyrektor.getNazwisko()));
        topPanel.add(managerPanel, BorderLayout.EAST);

        JButton acceptButton = new CircleBtn("   Zaakceptować   ", Color.GREEN);
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zaakceptowanie.zaakceptuj(true);
                zaakceptowanie.updateToDB();
                JOptionPane.showMessageDialog(ArtykulDetailsGUI.this, zaakceptowanie.getArtykul().getNazwa() + " był zaakceptowany");
                DyrektorZaakceptowaniaNoweGUI dyrektorZaakceptowaniaNoweGUI = new DyrektorZaakceptowaniaNoweGUI(dyrektor);
                dyrektorZaakceptowaniaNoweGUI.setVisible(true);
                dispose();

            }
        });
        buttonPanel.add(acceptButton);

        JButton rejectButton = new CircleBtn("Nie zaakceptować", Color.RED);
        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zaakceptowanie.zaakceptuj(false);
                zaakceptowanie.updateToDB();
                JOptionPane.showMessageDialog(ArtykulDetailsGUI.this, zaakceptowanie.getArtykul().getNazwa() + " nie był zaakceptowany");
                DyrektorZaakceptowaniaNoweGUI dyrektorZaakceptowaniaNoweGUI = new DyrektorZaakceptowaniaNoweGUI(dyrektor);
                dyrektorZaakceptowaniaNoweGUI.setVisible(true);
                dispose();
            }
        });
        buttonPanel.add(rejectButton);

        JButton backButton = new CircleBtn("Powrót", Color.red);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DyrektorZaakceptowaniaNoweGUI dyrektorZaakceptowaniaNoweGUI = new DyrektorZaakceptowaniaNoweGUI(dyrektor);
                dyrektorZaakceptowaniaNoweGUI.setVisible(true);
                dispose();
            }
        });
        JPanel buttonBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBackPanel.add(backButton);
        topPanel.add(buttonBackPanel, BorderLayout.WEST);

        JPanel downPanel = new JPanel(new BorderLayout());
        downPanel.add(buttonPanel, BorderLayout.EAST);

        contentPane.add(downPanel, BorderLayout.SOUTH);
    }
}

