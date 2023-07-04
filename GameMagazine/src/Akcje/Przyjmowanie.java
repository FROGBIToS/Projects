package Akcje;

import DB.MySQLConnector;
import Utils.Status;
import Workers.Dyrektor;
import Workers.Pisarz.Artykul;
import Workers.Menedzer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Przyjmowanie {
    private int id;
    private LocalDate dataPrzyjecia;
    private Menedzer menedzer;
    private Artykul artykul;
    private boolean czyPrzyjety;
    private boolean rozpatrywanie;

    public Przyjmowanie(Menedzer menedzer, Artykul artykul) {
        this.dataPrzyjecia = LocalDate.now();
        rozpatrywanie = true;
        this.menedzer = menedzer;
        this.artykul = artykul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataPrzyjecia() {
        return dataPrzyjecia;
    }

    public void setDataPrzyjecia(LocalDate dataPrzyjecia) {
        this.dataPrzyjecia = dataPrzyjecia;
    }

    public Menedzer getMenedzer() {
        return menedzer;
    }

    public void setMenedzer(Menedzer menedzer) {
        this.menedzer = menedzer;
    }

    public Artykul getArtykul() {
        return artykul;
    }

    public void setArtykul(Artykul artykul) {
        this.artykul = artykul;
    }

    public boolean isCzyPrzyjety() {
        return czyPrzyjety;
    }

    public void setCzyPrzyjety(boolean czyPrzyjety) {
        this.czyPrzyjety = czyPrzyjety;
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

            String insertQueryPrzyjmowanie = "INSERT INTO Przyjmowanie(artykul_id,menedzer_id, data_przyjecia, czy_byl_przyjety, rozpatrywanie) VALUES (?,?,?,?,?)";
            PreparedStatement statementPrzyjmowanie = MySQLConnector.getConnection().prepareStatement(insertQueryPrzyjmowanie, Statement.RETURN_GENERATED_KEYS);
            statementPrzyjmowanie.setInt(1, this.artykul.getId());
            statementPrzyjmowanie.setInt(2, this.menedzer.getId());
            statementPrzyjmowanie.setDate(3, Date.valueOf(this.dataPrzyjecia));
            statementPrzyjmowanie.setBoolean(4, this.czyPrzyjety);
            statementPrzyjmowanie.setBoolean(5, this.rozpatrywanie);
            statementPrzyjmowanie.executeUpdate();

            ResultSet generatedKeys = statementPrzyjmowanie.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newIdPrzyjmowanie = generatedKeys.getInt(1);
                this.setId(newIdPrzyjmowanie);
            }

            System.out.println("This przyjmowanie was added to the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateToDB() {
        try {
            MySQLConnector.connect();
            String updateQueryPrzyjmowanie = "UPDATE Przyjmowanie SET artykul_id = ?, menedzer_id = ? , data_przyjecia = ?, czy_byl_przyjety = ?, rozpatrywanie = ? WHERE id = ?";
            PreparedStatement statementPrzyjmowanie = MySQLConnector.getConnection().prepareStatement(updateQueryPrzyjmowanie);
            statementPrzyjmowanie.setInt(1, this.artykul.getId());
            statementPrzyjmowanie.setInt(2, this.menedzer.getId());
            statementPrzyjmowanie.setDate(3, Date.valueOf(this.dataPrzyjecia));
            statementPrzyjmowanie.setBoolean(4, this.czyPrzyjety);
            statementPrzyjmowanie.setBoolean(5, this.rozpatrywanie);
            statementPrzyjmowanie.setInt(6, this.id);
            statementPrzyjmowanie.executeUpdate();
            System.out.println("Przyjmowanie with id " + this.id + " was updated in the DB.");
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();
            String deleteQueryPrzyjmowanie = "DELETE FROM Przyjmowanie WHERE id = ?";
            PreparedStatement statementPrzyjmowanie = MySQLConnector.getConnection().prepareStatement(deleteQueryPrzyjmowanie);
            statementPrzyjmowanie.setInt(1, id);
            statementPrzyjmowanie.executeUpdate();
            System.out.println("Przyjmowanie with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Przyjmowanie> getPrzyjmowanieByIdMenedzer(int idMenedzer) {
        List<Przyjmowanie> przyjmowania = new ArrayList<>();

        try {
            MySQLConnector.connect();

            String selectQueryPrzyjmowanie = "SELECT * FROM Przyjmowanie WHERE menedzer_id = ?";
            PreparedStatement statementPrzyjmowanie = MySQLConnector.getConnection().prepareStatement(selectQueryPrzyjmowanie);
            statementPrzyjmowanie.setInt(1, idMenedzer);
            ResultSet resultSet = statementPrzyjmowanie.executeQuery();

            while (resultSet.next()) {
                int przyjmowanieId = resultSet.getInt("id");
                LocalDate przyjmowanieData = resultSet.getDate("data_przyjecia").toLocalDate();
                boolean czyPrzyjety = resultSet.getBoolean("czy_byl_przyjety");
                boolean rozpatrywanie = resultSet.getBoolean("rozpatrywanie");

                int menedzerId = resultSet.getInt("menedzer_id");
                Menedzer menedzer = Menedzer.getMenedzerById(menedzerId);

                int artykulId = resultSet.getInt("artykul_id");
                Artykul artykul = Artykul.getArtykulById(artykulId);

                Przyjmowanie przyjmowanie = new Przyjmowanie(menedzer, artykul);
                przyjmowanie.setId(przyjmowanieId);
                przyjmowanie.setDataPrzyjecia(przyjmowanieData);
                przyjmowanie.setCzyPrzyjety(czyPrzyjety);
                przyjmowanie.setRozpatrywanie(rozpatrywanie);

                przyjmowania.add(przyjmowanie);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return przyjmowania;
    }

    public void przyjac(boolean status){
        this.setRozpatrywanie(false);
        this.setCzyPrzyjety(status);
        this.artykul.setCzyPrzyjety(status ? Status.TRUE : Status.FALSE);
        this.updateToDB();
        this.artykul.updateToDB();

        if (status){
            odeslacNaZaakceptowanie();
        }else{
            this.artykul.setCzyZaakceptowany(Status.FALSE);
            this.artykul.updateToDB();
        }
    }

    private void odeslacNaZaakceptowanie(){
        Dyrektor dyrektor = Dyrektor.getDyrektorWolny();
        Zaakceptowanie zaakceptowanie = new Zaakceptowanie(dyrektor, this.getArtykul());
        zaakceptowanie.insertToDB();
    }

}
