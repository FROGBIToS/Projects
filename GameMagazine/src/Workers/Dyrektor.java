package Workers;

import DB.MySQLConnector;
import org.json.JSONArray;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Dyrektor extends Pracownik implements Serializable{

    private int id;
    private String kodDyrektora;

    public Dyrektor(String imie, String nazwisko, String adresZamieszkania, String numerTelefonu, List<String> emails, LocalDate dataZatrudnienia, float pensja, String kodDyrektora) {
        super(imie, nazwisko, adresZamieszkania, numerTelefonu, emails, dataZatrudnienia, pensja);
        this.kodDyrektora = kodDyrektora;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getKodDyrektora() {
        return kodDyrektora;
    }

    public void setKodDyrektora(String kodDyrektora) {
        this.kodDyrektora = kodDyrektora;
    }

    public void insertToDB() {
        try {
            this.insertToDBPRACOWNIK();
            MySQLConnector.connect();
            String insertQueryDyrektor = "INSERT INTO Dyrektor(id, kod_dyrektora) VALUES (?,?)";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(insertQueryDyrektor, Statement.RETURN_GENERATED_KEYS);
            statementDyrektor.setInt(1, this.getId());
            statementDyrektor.setString(2, this.kodDyrektora);
            statementDyrektor.executeUpdate();

    System.out.println("This dyrektor was added to the DB with id: " + this.getId());
    MySQLConnector.disconnect();

    } catch (SQLException e) {
        throw new RuntimeException(e);
        }
    }

    public void updateToDB(){
        try {
            MySQLConnector.connect();
            String insertQueryDyrektor = "update Dyrektor set kod_dyrektora = ? where id = ?;";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(insertQueryDyrektor, Statement.RETURN_GENERATED_KEYS);
            statementDyrektor.setString(1, this.kodDyrektora);
            statementDyrektor.setInt(2, this.getId());
            statementDyrektor.executeUpdate();
            System.out.println("This dyrektor was updated in the DB with id: " + this.getId());
            MySQLConnector.disconnect();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();

            String deleteQueryDyrektor = "DELETE FROM Dyrektor WHERE id = ?";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(deleteQueryDyrektor);
            statementDyrektor.setInt(1, id);
            statementDyrektor.executeUpdate();

            System.out.println("This dyrektor was deleted from the DB with id: " + id);
            MySQLConnector.disconnect();
            Pracownik.deleteFromDBPRACOWNIK(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Dyrektor getDyrektorById(int id) {
        try {
            MySQLConnector.connect();

            String selectQueryDyrektor = "SELECT * FROM Pracownik JOIN Dyrektor ON Pracownik.id = Dyrektor.id WHERE Dyrektor.id = ?";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(selectQueryDyrektor);
            statementDyrektor.setInt(1, id);
            ResultSet resultSet = statementDyrektor.executeQuery();

            Dyrektor dyrektor = null;
            if (resultSet.next()) {
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String adres_zamieszkania = resultSet.getString("adres_zamieszkania");
                String numer_telefonu = resultSet.getString("numer_telefonu");
                List<String> emails = Arrays.asList(resultSet.getString("emails").split(","));
                LocalDate data_zatrudnienia = resultSet.getDate("data_zatrudnienia").toLocalDate();
                float pensja = resultSet.getFloat("pensja");
                String kodDyrektora = resultSet.getString("kod_dyrektora");
                dyrektor = new Dyrektor(imie, nazwisko, adres_zamieszkania, numer_telefonu, emails, data_zatrudnienia, pensja, kodDyrektora);
                dyrektor.setId(id);
            }

            MySQLConnector.disconnect();
            return dyrektor;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Dyrektor getDyrektorWolny(){
        try {
            MySQLConnector.connect();
            CallableStatement statement = MySQLConnector.getConnection().prepareCall("{CAll GetDirectorWithLeastApprovals()}");

            ResultSet resultSet = statement.executeQuery();

            int id = 1;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            MySQLConnector.disconnect();

            return getDyrektorById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Dyrektor{" +
                "id=" + id +
                ", kodDyrektora='" + kodDyrektora + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", adresZamieszkania='" + adresZamieszkania + '\'' +
                ", numerTelefonu='" + numerTelefonu + '\'' +
                ", emails=" + emails +
                ", dataZatrudnienia=" + dataZatrudnienia +
                ", pensja=" + pensja +
                '}';
    }
}
