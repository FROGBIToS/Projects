package Workers;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import DB.MySQLConnector;
import Utils.Serializer;
import org.json.JSONArray;

public abstract class Pracownik extends Serializer implements Serializable{
    private int id;
    protected String imie;
    protected String nazwisko;
    protected String adresZamieszkania;
    protected String numerTelefonu;
    protected List<String> emails;
    protected LocalDate dataZatrudnienia;
    protected float pensja;
    public Pracownik(String imie, String nazwisko, String adresZamieszkania, String numerTelefonu, List<String> emails, LocalDate dataZatrudnienia, float pensja) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.numerTelefonu = numerTelefonu;
        this.emails = emails;
        this.dataZatrudnienia = dataZatrudnienia;
        this.pensja = pensja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getAdresZamieszkania() {
        return adresZamieszkania;
    }

    public void setAdresZamieszkania(String adresZamieszkania) {
        this.adresZamieszkania = adresZamieszkania;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public LocalDate getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(LocalDate dataZatrudnienia) {
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public float getPensja() {
        return pensja;
    }

    public void setPensja(float pensja) {
        this.pensja = pensja;
    }

    public void insertToDBPRACOWNIK() {
        try {
            MySQLConnector.connect();
            String insertQueryPracownik = "INSERT INTO Pracownik(imie, nazwisko, adres_zamieszkania, numer_telefonu, emails, data_zatrudnienia, pensja) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = MySQLConnector.getConnection().prepareStatement(insertQueryPracownik, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.imie);
            statement.setString(2, this.nazwisko);
            statement.setString(3, this.adresZamieszkania);
            statement.setString(4, this.numerTelefonu);

            JSONArray emailsJson = new JSONArray(emails);
            String emailsJsonString = emailsJson.toString();
            statement.setString(5,emailsJsonString);
            statement.setDate(6, Date.valueOf(this.dataZatrudnienia));
            statement.setFloat(7, this.pensja);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newIdPracownik = generatedKeys.getInt(1);
                this.setId(newIdPracownik);
            }
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteFromDBPRACOWNIK(int id) {
        try {
            MySQLConnector.connect();

            String deleteQueryPracownik = "DELETE FROM Pracownik WHERE id = ?";
            PreparedStatement statementPracownik = MySQLConnector.getConnection().prepareStatement(deleteQueryPracownik);
            statementPracownik.setInt(1, id);
            statementPracownik.executeUpdate();

            System.out.println("Pracownik with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return false;
        }
    }

    public void updateToDB() {
        try {
            MySQLConnector.connect();
            String updateQueryPracownik = "UPDATE Pracownik SET imie = ?, nazwisko = ?, adres_zamieszkania = ?, numer_telefonu = ?, emails = ?, data_zatrudnienia = ?, pensja = ? WHERE id = ?";
            PreparedStatement statementPracownik = MySQLConnector.getConnection().prepareStatement(updateQueryPracownik);
            statementPracownik.setString(1, this.imie);
            statementPracownik.setString(2, this.nazwisko);
            statementPracownik.setString(3, this.adresZamieszkania);
            statementPracownik.setString(4, this.numerTelefonu);

            JSONArray emailsJson = new JSONArray(emails);
            String emailsJsonString = emailsJson.toString();
            statementPracownik.setString(5, emailsJsonString);
            statementPracownik.setDate(6, Date.valueOf(this.dataZatrudnienia));
            statementPracownik.setFloat(7, this.pensja);
            statementPracownik.setInt(8, this.id);

            statementPracownik.executeUpdate();
            System.out.println("Pracownik with id " + this.id + " was updated in the DB.");
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
