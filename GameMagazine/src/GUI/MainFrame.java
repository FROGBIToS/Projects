package GUI;

import DB.MySQLConnector;
import Workers.Dyrektor;
import Workers.Menedzer;
import Workers.Pisarz.Pisarz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    public MainFrame() {

        setTitle("Loginnig");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        JButton loginButton = new CircleBtn("Login", Color.GREEN);


        setLayout(new GridLayout(3, 2, 10, 10));
        BorderFactory.createEmptyBorder(10, 10, 10, 10);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                List<String> answer = checkWhoIt(username, password);
                if (answer.get(1) == null){
                    JOptionPane.showMessageDialog(MainFrame.this, "Username or password is incorect", "Fail", JOptionPane.ERROR_MESSAGE);
                }else{
                    switch (answer.get(1)){
                        case "pisarz":
                            Pisarz pisarz = Pisarz.getPisarzById(Integer.parseInt(answer.get(0)));
                            openPisarzGUI(pisarz);
                            break;
                        case "menedzer":
                            Menedzer menedzer = Menedzer.getMenedzerById(Integer.parseInt(answer.get(0)));
                            openMenedzerGUI(menedzer);
                            break;
                        case "dyrektor":
                            Dyrektor dyrektor = Dyrektor.getDyrektorById(Integer.parseInt(answer.get(0)));
                            openDyrektorGUI(dyrektor);
                            break;
                    }
                }
            }
        });
        pack();
    }


    private void openPisarzGUI(Pisarz pisarz) {
        PisarzFrame pisarzGUI = new PisarzFrame(pisarz);
        pisarzGUI.setVisible(true);
        dispose();
    }

    private void openMenedzerGUI(Menedzer menedzer) {
        MenedzerFrame menedzerGUI = new MenedzerFrame(menedzer);
        menedzerGUI.setVisible(true);
        dispose();
    }

    private void openDyrektorGUI(Dyrektor dyrektor) {
        DyrektorFrame dyrektorGUI = new DyrektorFrame(dyrektor);
        dyrektorGUI.setVisible(true);
        dispose();
    }
    public static List<String> checkWhoIt(String username, String password) {
        List<String> employeeData = new ArrayList<>();

        try {
            MySQLConnector.connect();

            String procedureCall = "{CALL GetEmployeeRole(?, ?, ?, ?)}";
            CallableStatement statement = MySQLConnector.getConnection().prepareCall(procedureCall);

            statement.setString(1, username);
            statement.setString(2, password);

            statement.registerOutParameter(3, Types.VARCHAR);
            statement.registerOutParameter(4, Types.INTEGER);

            statement.execute();

            String employeeRole = statement.getString(3);
            int employeeId = statement.getInt(4);

            employeeData.add(String.valueOf(employeeId));
            employeeData.add(employeeRole);

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(employeeData);

        return employeeData;
    }

}