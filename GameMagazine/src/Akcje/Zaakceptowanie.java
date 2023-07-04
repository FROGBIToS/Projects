package Akcje;

import DB.MySQLConnector;
import Utils.Status;
import Workers.Dyrektor;
import Workers.Pisarz.Artykul;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Zaakceptowanie {
    private int id;
    private LocalDate zaakceptowieData;
    private Dyrektor dyrektor;
    private Artykul artykul;
    private boolean czyZaakceptowany;
    private boolean rozpatrywanie;
    private LocalDate dataZaakceptowania = LocalDate.of(2015, 3, 17);
    public Zaakceptowanie(Dyrektor dyrektor, Artykul artykul) {
        this.rozpatrywanie = true;
        this.dyrektor = dyrektor;
        this.artykul = artykul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getZaakceptowieData() {
        return zaakceptowieData;
    }

    public void setZaakceptowieData(LocalDate zaakceptowieData) {
        this.zaakceptowieData = zaakceptowieData;
    }

    public Dyrektor getDyrektor() {
        return dyrektor;
    }

    public void setDyrektor(Dyrektor dyrektor) {
        this.dyrektor = dyrektor;
    }

    public Artykul getArtykul() {
        return artykul;
    }

    public void setArtykul(Artykul artykul) {
        this.artykul = artykul;
    }

    public boolean isCzyZaakceptowany() {
        return czyZaakceptowany;
    }

    public void setCzyZaakceptowany(boolean czyZaakceptowany) {
        this.czyZaakceptowany = czyZaakceptowany;
    }

    public LocalDate getDataZaakceptowania() {
        return dataZaakceptowania;
    }

    public void setDataZaakceptowania(LocalDate dataZaakceptowania) {
        this.dataZaakceptowania = dataZaakceptowania;
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

            String insertQueryZaakceptowanie = "INSERT INTO Zaakceptowanie(dyrektor_id, artykul_id, czy_byl_zaakceptowany, rozpatrywanie, data_zaakceptowania) VALUES (?,?,?,?,?)";
            PreparedStatement statementZaakceptowanie = MySQLConnector.getConnection().prepareStatement(insertQueryZaakceptowanie, Statement.RETURN_GENERATED_KEYS);
            statementZaakceptowanie.setInt(1, this.dyrektor.getId());
            statementZaakceptowanie.setInt(2, this.artykul.getId());
            statementZaakceptowanie.setBoolean(3, this.czyZaakceptowany);
            statementZaakceptowanie.setBoolean(4, this.rozpatrywanie);
            statementZaakceptowanie.setDate(5, Date.valueOf(this.dataZaakceptowania));
            statementZaakceptowanie.executeUpdate();

            ResultSet generatedKeys = statementZaakceptowanie.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newIdZaakceptowanie = generatedKeys.getInt(1);
                this.setId(newIdZaakceptowanie);
            }

            System.out.println("This zaakceptowanie was added to the DB with id: " + this.getId());
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateToDB(){
        try {
            MySQLConnector.connect();
            String insertQueryDyrektor = "UPDATE Zaakceptowanie SET czy_byl_zaakceptowany = ?, data_zaakceptowania = ?, rozpatrywanie = ? where id = ?;";
            PreparedStatement statementDyrektor = MySQLConnector.getConnection().prepareStatement(insertQueryDyrektor, Statement.RETURN_GENERATED_KEYS);
            statementDyrektor.setBoolean(1, this.czyZaakceptowany);
            statementDyrektor.setString(2, String.valueOf(this.dataZaakceptowania));
            statementDyrektor.setBoolean(3, this.rozpatrywanie);
            statementDyrektor.setInt(4, this.getId());
            statementDyrektor.executeUpdate();
            System.out.println("This zaakceptowanie was updated in the DB with id: " + this.getId());
            MySQLConnector.disconnect();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFromDB(int id) {
        try {
            MySQLConnector.connect();
            String deleteQueryZaakceptowanie = "DELETE FROM Zaakceptowanie WHERE id = ?";
            PreparedStatement statementZaakceptowanie = MySQLConnector.getConnection().prepareStatement(deleteQueryZaakceptowanie);
            statementZaakceptowanie.setInt(1, id);
            statementZaakceptowanie.executeUpdate();

            System.out.println("Zaakceptowanie with id " + id + " was deleted from the DB.");
            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Zaakceptowanie> getZaakceptowanieByIdDyrektor(int idDyrektor) {
        List<Zaakceptowanie> zaakceptowania = new ArrayList<>();

        try {
            MySQLConnector.connect();

            String selectQueryZaakceptowanie = "SELECT * FROM Zaakceptowanie WHERE dyrektor_id = ?";
            PreparedStatement statementZaakceptowanie = MySQLConnector.getConnection().prepareStatement(selectQueryZaakceptowanie);
            statementZaakceptowanie.setInt(1, idDyrektor);
            ResultSet resultSet = statementZaakceptowanie.executeQuery();

            while (resultSet.next()) {
                int zaakceptowanieId = resultSet.getInt("id");
                LocalDate zaakceptowanieData = resultSet.getDate("data_zaakceptowania").toLocalDate();
                boolean czyZaakceptowany = resultSet.getBoolean("czy_byl_zaakceptowany");
                boolean rozpatrywanie = resultSet.getBoolean("rozpatrywanie");

                int dyrektorId = resultSet.getInt("dyrektor_id");
                Dyrektor dyrektor = Dyrektor.getDyrektorById(dyrektorId);

                int artykulId = resultSet.getInt("artykul_id");
                Artykul artykul = Artykul.getArtykulById(artykulId);

                Zaakceptowanie zaakceptowanie = new Zaakceptowanie(dyrektor, artykul);
                zaakceptowanie.setId(zaakceptowanieId);
                zaakceptowanie.setZaakceptowieData(zaakceptowanieData);
                zaakceptowanie.setCzyZaakceptowany(czyZaakceptowany);
                zaakceptowanie.setRozpatrywanie(rozpatrywanie);

                zaakceptowania.add(zaakceptowanie);
            }

            MySQLConnector.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return zaakceptowania;
    }


    public void zaakceptuj(boolean status){
        this.setRozpatrywanie(false);
        this.setCzyZaakceptowany(status);
        this.artykul.setCzyZaakceptowany(status ? Status.TRUE : Status.FALSE);
        this.updateToDB();
        this.artykul.updateToDB();
    }
}
