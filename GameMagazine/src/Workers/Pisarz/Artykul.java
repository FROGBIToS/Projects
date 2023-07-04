package Workers.Pisarz;

import Akcje.Przyjmowanie;
import Akcje.Zaakceptowanie;
import DB.MySQLConnector;
import Utils.Status;
import Workers.Dyrektor;
import Workers.Menedzer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;;

public class Artykul{
    private int idArtykulu;
    private String nazwa;
    private String opis;
    private String tekst;
    private LocalDate dataNapisania;
    private Status czyZaakceptowany;
    private Status czyPrzyjety;
    private final Pisarz pisarz;
    private boolean rozpatrywanie;
    private static int minIloscZnakow = 1000;

    Artykul(String nazwa, String opis,String tekst,  LocalDate dataNapisania, Pisarz pisarz) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.tekst = tekst;
        this.dataNapisania = dataNapisania;
        this.czyZaakceptowany = Status.FALSE;
        this.czyPrzyjety = Status.FALSE;
        this.pisarz = pisarz;
        this.rozpatrywanie = true;
    }

    public int getId(){
        return this.idArtykulu;
    }

    public void setId(int idArtykulu) {
        this.idArtykulu = idArtykulu;
    }

    public Pisarz getPisarz() {
        return pisarz;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public LocalDate getDataNapisania() {
        return dataNapisania;
    }

    public void setDataNapisania(LocalDate dataNapisania) {
        this.dataNapisania = dataNapisania;
    }

    public Status getCzyZaakceptowany() {
        return czyZaakceptowany;
    }

    public void setCzyZaakceptowany(Status czyZaakceptowany) {
        this.czyZaakceptowany = czyZaakceptowany;
    }

    public Status getCzyPrzyjety() {
        return czyPrzyjety;
    }

    public void setCzyPrzyjety(Status czyPrzyjety) {
        this.czyPrzyjety = czyPrzyjety;
    }

    public static int getMinIloscZnakow() {
        return minIloscZnakow;
    }

    public static void setMinIloscZnakow(int minIloscZnakow) {
        Artykul.minIloscZnakow = minIloscZnakow;
    }

    public boolean isRozpatrywanie() {
        return rozpatrywanie;
    }

    public void setRozpatrywanie(boolean rozpatrywanie) {
        this.rozpatrywanie = rozpatrywanie;
    }

    public void insertToDB() {
        try {
            MySQLConnector.connect();

            String insertQueryArtykul = "INSERT INTO Artykul(nazwa, pisarz_id, opis, tekst, data_napisania, czy_byl_zaakceptowany, czy_byl_przyjety, rozpatrywanie, min_ilosc_znakow) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statementArtykul = MySQLConnector.getConnection().prepareStatement(insertQueryArtykul, Statement.RETURN_GENERATED_KEYS);
            statementArtykul.setString(1, this.nazwa);
            statementArtykul.setInt(2, this.pisarz.getId());
            statementArtykul.setString(3, this.opis);
            statementArtykul.setString(4, this.tekst);
            statementArtykul.setDate(5, Date.valueOf(this.dataNapisania));
            statementArtykul.setString(6, this.czyZaakceptowany.getValue());
            statementArtykul.setString(7, this.czyPrzyjety.getValue());
            statementArtykul.setBoolean(8, this.isRozpatrywanie());
            statementArtykul.setInt(9, this.minIloscZnakow);
            statementArtykul.executeUpdate();

            ResultSet generatedKeys = statementArtykul.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newIdArtykul = generatedKeys.getInt(1);
                this.setId(newIdArtykul);
            }

            System.out.println("This artykul was added to the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateToDB() {
        try {
            MySQLConnector.connect();
            String updateQueryArtykul = "UPDATE Artykul SET czy_byl_zaakceptowany = ?, czy_byl_przyjety = ?, rozpatrywanie = ? WHERE id = ?";
            PreparedStatement statementArtykul = MySQLConnector.getConnection().prepareStatement(updateQueryArtykul);
            statementArtykul.setString(1, this.czyZaakceptowany.getValue());
            statementArtykul.setString(2, this.czyPrzyjety.getValue());
            statementArtykul.setBoolean(3, this.isRozpatrywanie());
            statementArtykul.setInt(4, this.getId());
            statementArtykul.executeUpdate();
            System.out.println("This artykul was updated in the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();
            String deleteQueryArtykul = "DELETE FROM Artykul WHERE id = ?";
            PreparedStatement statementArtykul = MySQLConnector.getConnection().prepareStatement(deleteQueryArtykul);
            statementArtykul.setInt(1, id);
            statementArtykul.executeUpdate();

            System.out.println("Artykul with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Artykul getArtykulById(int id) {
        Artykul artykul = null;

        try {
            MySQLConnector.connect();

            String selectQueryArtykul = "SELECT * FROM Artykul WHERE id = ?";
            PreparedStatement statementArtykul = MySQLConnector.getConnection().prepareStatement(selectQueryArtykul);
            statementArtykul.setInt(1, id);
            ResultSet resultSet = statementArtykul.executeQuery();

            if (resultSet.next()) {
                String nazwa = resultSet.getString("nazwa");
                String opis = resultSet.getString("opis");
                String tekst = resultSet.getString("tekst");
                LocalDate dataNapisania = resultSet.getDate("data_napisania").toLocalDate();
                Status czyZaakceptowany = Status.fromValue(resultSet.getString("czy_byl_zaakceptowany"));
                Status czyPrzyjety = Status.fromValue(resultSet.getString("czy_byl_przyjety"));
                boolean rozpatrywanie = resultSet.getBoolean("rozpatrywanie");

                int pisarzId = resultSet.getInt("pisarz_id");
                Pisarz pisarz = Pisarz.getPisarzById(pisarzId);

                artykul = new Artykul(nazwa, opis, tekst, dataNapisania, pisarz);
                artykul.setId(id);
                artykul.setCzyZaakceptowany(czyZaakceptowany);
                artykul.setCzyPrzyjety(czyPrzyjety);
                artykul.setRozpatrywanie(rozpatrywanie);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return artykul;
    }

    public static List<Artykul> getAllArtykulByPisarz(int idPisarz){
        List<Artykul> artykuls = new ArrayList<>();

        try {
            MySQLConnector.connect();

            String selectQueryZaakceptowanie = "SELECT * FROM Artykul WHERE pisarz_id = ?";
            PreparedStatement statementZaakceptowanie = MySQLConnector.getConnection().prepareStatement(selectQueryZaakceptowanie);
            statementZaakceptowanie.setInt(1, idPisarz);
            ResultSet resultSet = statementZaakceptowanie.executeQuery();

            while (resultSet.next()) {
                int artykulId = resultSet.getInt("id");
                String nazwa = resultSet.getString("nazwa");
                String opis = resultSet.getString("opis");
                String tekst = resultSet.getString("tekst");
                LocalDate dataNapisania = resultSet.getDate("data_napisania").toLocalDate();
                Status czyZaakceptowany = Status.fromValue(resultSet.getString("czy_byl_zaakceptowany"));
                Status czyPrzyjety = Status.fromValue(resultSet.getString("czy_byl_przyjety"));
                boolean rozpatrywanie = resultSet.getBoolean("rozpatrywanie");

                Pisarz pisarz = Pisarz.getPisarzById(idPisarz);

                Artykul artykul = new Artykul(nazwa,opis,tekst,dataNapisania,pisarz);
                artykul.setId(artykulId);
                artykul.setCzyZaakceptowany(czyZaakceptowany);
                artykul.setCzyPrzyjety(czyPrzyjety);
                artykul.setRozpatrywanie(rozpatrywanie);

                artykuls.add(artykul);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return artykuls;
    }

    public void odeslacNaSprawdzenie(){
        Menedzer menedzer = Menedzer.getMenedzerForPrzyjmowanie();
        if (menedzer != null){
            Przyjmowanie przyjmowanie = new Przyjmowanie(menedzer, this);
            przyjmowanie.insertToDB();

            this.setCzyPrzyjety(Status.W_TRAKCIE);
            this.setCzyZaakceptowany(Status.W_TRAKCIE);
            this.updateToDB();
            this.setRozpatrywanie(false);

        }else{
            System.out.println("Nie ma wolnych menedzerow");
        }
    }
}

