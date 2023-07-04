package Workers;

import Akcje.Przyjmowanie;
import DB.MySQLConnector;
import Workers.Pisarz.Pisarz;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menedzer extends Pracownik implements Serializable{
    private int id;
    private List<Przyjmowanie> przyjmowania;
    public Menedzer(String imie, String nazwisko, String adresZamieszkania, String numerTelefonu, List<String> emails, LocalDate dataZatrudnienia, float pensja) {
        super(imie, nazwisko, adresZamieszkania, numerTelefonu, emails, dataZatrudnienia, pensja);
        this.przyjmowania = new ArrayList();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public List getPrzyjmowania() {
        return przyjmowania;
    }
    public void insertToDB() {
        try {
            this.insertToDBPRACOWNIK();
            MySQLConnector.connect();
            String insertQueryDyrektor = "INSERT INTO Menedżer(id) VALUES (?)";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(insertQueryDyrektor, Statement.RETURN_GENERATED_KEYS);
            statementDyrektor.setInt(1, this.getId());
            statementDyrektor.executeUpdate();

            System.out.println("This menedźer was added to the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateToDB() {
        try {
            super.updateToDB();
            MySQLConnector.connect();
            String updateQueryMenedzer = "UPDATE Menedżer SET ... WHERE id = ?";
            PreparedStatement statementMenedzer = MySQLConnector.getConnection().prepareStatement(updateQueryMenedzer);
            statementMenedzer.setInt(1, this.getId());
            statementMenedzer.executeUpdate();
            System.out.println("This manager was updated in the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();
            String deleteQueryMenedzer = "DELETE FROM Menedżer WHERE id = ?";
            PreparedStatement statementMenedzer = MySQLConnector.getConnection().prepareStatement(deleteQueryMenedzer);
            statementMenedzer.setInt(1, id);
            statementMenedzer.executeUpdate();

            System.out.println("Manager with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
            Pracownik.deleteFromDBPRACOWNIK(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Menedzer getMenedzerById(int id) {
        Menedzer menedzer = null;

        try {
            MySQLConnector.connect();

            String selectQueryMenedzer = "SELECT * FROM Menedżer M JOIN Pracownik P on P.id = M.id WHERE M.id = ?";
            PreparedStatement statementMenedzer = MySQLConnector.getConnection().prepareStatement(selectQueryMenedzer);
            statementMenedzer.setInt(1, id);
            ResultSet resultSet = statementMenedzer.executeQuery();

            if (resultSet.next()) {
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String adres_zamieszkania = resultSet.getString("adres_zamieszkania");
                String numer_telefonu = resultSet.getString("numer_telefonu");
                List<String> emails = Arrays.asList(resultSet.getString("emails").split(","));
                LocalDate data_zatrudnienia = resultSet.getDate("data_zatrudnienia").toLocalDate();
                float pensja = resultSet.getFloat("pensja");

                menedzer = new Menedzer(imie, nazwisko, adres_zamieszkania, numer_telefonu, emails, data_zatrudnienia, pensja);
                menedzer.setId(id);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return menedzer;
    }

    public static Menedzer getMenedzerForPrzyjmowanie(){
        try {
            MySQLConnector.connect();
            CallableStatement statement = MySQLConnector.getConnection().prepareCall("{CALL GetManagerWithLeastAcceptances()}");

            ResultSet resultSet = statement.executeQuery();

            int id = 1;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            MySQLConnector.disconnect();

            return getMenedzerById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
