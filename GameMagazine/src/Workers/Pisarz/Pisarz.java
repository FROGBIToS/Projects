package Workers.Pisarz;
import DB.MySQLConnector;
import Utils.Status;
import Workers.Pracownik;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Pisarz extends Pracownik implements Serializable{
    private String portfolio;
    private List<Artykul> artykuly = new ArrayList<>();
    public Pisarz(String imie, String nazwisko, String adresZamieszkania, String numerTelefonu, List<String> emails, LocalDate dataZatrudnienia, float pensja) {
        super(imie, nazwisko, adresZamieszkania, numerTelefonu, emails, dataZatrudnienia, pensja);
        this.portfolio = null;
    }

    public void addArtykul(String nazwa, String opis,String tekst,  LocalDate dataNapisania){
        Artykul artykul = new Artykul(nazwa, opis,tekst,dataNapisania,this);
        artykuly.add(artykul);
        artykul.insertToDB();
    }

    public Artykul getArtykul(int id){
        return artykuly.get(id);
    }

    public Optional<String> getPortfolio() {
        return Optional.ofNullable(portfolio);
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public void showPortfolio(){
        System.out.println(getPortfolio().orElse("Portfolio jest puste"));
    }

    public void insertToDB() {
        try {
            this.insertToDBPRACOWNIK();
            MySQLConnector.connect();
            String insertQueryDyrektor = "INSERT INTO Pisarz(id, portfolio) VALUES (?,?)";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(insertQueryDyrektor, Statement.RETURN_GENERATED_KEYS);
            statementDyrektor.setInt(1, this.getId());
            statementDyrektor.setString(2, this.getPortfolio().orElse(null));
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
            String updateQueryPisarz = "UPDATE Pisarz SET portfolio = ? WHERE id = ?";
            PreparedStatement statementPisarz = MySQLConnector.getConnection().prepareStatement(updateQueryPisarz);
            statementPisarz.setString(1, this.getPortfolio().orElse(null));
            statementPisarz.setInt(2, this.getId());
            statementPisarz.executeUpdate();

            System.out.println("This pisarz was updated in the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();
            String deleteQueryPisarz = "DELETE FROM Pisarz WHERE id = ?";
            PreparedStatement statementPisarz = MySQLConnector.getConnection().prepareStatement(deleteQueryPisarz);
            statementPisarz.setInt(1, id);
            statementPisarz.executeUpdate();

            System.out.println("Pisarz with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
            Pracownik.deleteFromDBPRACOWNIK(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Pisarz getPisarzById(int id) {
        Pisarz pisarz = null;
        try {
            MySQLConnector.connect();

            String selectQueryPisarz = "SELECT * FROM Pisarz Pi JOIN Pracownik Pr on Pr.id = Pi.id WHERE Pi.id = ?";
            PreparedStatement statementPisarz = MySQLConnector.getConnection().prepareStatement(selectQueryPisarz);
            statementPisarz.setInt(1, id);
            ResultSet resultSet = statementPisarz.executeQuery();

            if (resultSet.next()) {
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String adres_zamieszkania = resultSet.getString("adres_zamieszkania");
                String numer_telefonu = resultSet.getString("numer_telefonu");
                List<String> emails = Arrays.asList(resultSet.getString("emails").split(","));
                LocalDate data_zatrudnienia = resultSet.getDate("data_zatrudnienia").toLocalDate();
                float pensja = resultSet.getFloat("pensja");
                String portfolio = resultSet.getString("portfolio");

                pisarz = new Pisarz(imie, nazwisko, adres_zamieszkania, numer_telefonu, emails, data_zatrudnienia, pensja);
                pisarz.setId(id);
                pisarz.setPortfolio(portfolio);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pisarz;
    }

    @Override
    public String toString() {
        return "Pisarz{" +
                "portfolio='" + portfolio + '\'' +
                ", artykuly=" + artykuly +
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
