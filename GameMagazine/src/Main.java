import Akcje.Przyjmowanie;
import Akcje.Zaakceptowanie;
import GUI.MainFrame;
import GUI.PisarzFrame;
import Workers.Dyrektor;
import Workers.Menedzer;
import Workers.Pisarz.Artykul;
import Workers.Pisarz.Pisarz;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        Dyrektor dyrektor = new Dyrektor
//                ("Jan",
//                        "Kowalski",
//                        "ul. Testowa 1",
//                        "123456789",
//                        List.of("mainDyrektor@gg.com","jankowal@gg.com","weeks@trx.com"),
//                        LocalDate.of(2008,2,20),
//                        256,
//                        "KD2321");
//
//        dyrektor.insertToDB();
//        Menedzer manager = new Menedzer
//                ("Piotr",
//                        "Sezam",
//                        "ul. Testowa 2",
//                        "143534572345",
//                        List.of("menegerpiotr@gg.com","piotrsez@gg.com"),
//                        LocalDate.of(2012,8,13),
//                        93);
//        manager.insertToDB();
//        Pisarz pisarz_bez = new Pisarz("Kren",
//                "Hnen",
//                "ul. Wiejska 1",
//                "+48123456789",
//                List.of("pisarzkren@gg.com","krenhnen@gg.com"),
//                LocalDate.of(2018, 1, 1),
//                10);
//
//        pisarz_bez.insertToDB();
//
//        Pisarz pisarz_bez = Pisarz.getPisarzById(3);
//
//        pisarz_bez.addArtykul("Ciemna noc", "Ale był potęcjał....", "k;l';skfdgjdfgljsdl;fgjsldfkjgs;ldfkgjs;ldfkgj ", LocalDate.now());
//        pisarz_bez.addArtykul("Ostatni żwnirz", "Dałno temu ktoś..", "asdfsdfdfdfdfdfdfdfdfdfdfdfasdfasdfasdfasdf ", LocalDate.now());
//        pisarz_bez.addArtykul("Dobrze spać", "Wszyscy lubimy spać..", "gjgjgjhyktkfkfasd/f; ajk f ", LocalDate.now());



        SwingUtilities.invokeLater(() -> {
            MainFrame myGUI = new MainFrame();
            myGUI.setVisible(true);
        });
    }
}