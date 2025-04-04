package vinnsla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import vidmot.SnakesApplication;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Model klasi fyrir leikinn. Er framhlið fyrir vinnsluna
 *
 *
 *****************************************************************************/

public class Leikur {

    public static final String I_GANGI = "í gangi";
    private int MAXREITUR = 0;
    private int naesti = 0; // næsti leikmaður sem á að gera

    private final Teningur teningur = new Teningur(); // Model hlutur fyrir tening
    // model hlutur fyrir slöngur og stiga
    private final SlongurStigar slongurStigar = new SlongurStigar();
    private final Leikmadur[] leikmenn =    // harðkóðaðir leikmenn, má lesa inn seinna
            new Leikmadur[]{new Leikmadur(SnakesApplication.getNafnLeikmanns1()), new Leikmadur(SnakesApplication.getNafnLeikmanns2())};

    private final SimpleBooleanProperty leikLokid = new SimpleBooleanProperty(); // er leik lokið
    private final SimpleBooleanProperty teningurVirkur = new SimpleBooleanProperty(); // er teningur virkur

    // Er fundinn sigurvegari eða er leikur í gangi. Gæti verið annað en strengur
    private final SimpleStringProperty sigurvegariProperty = new SimpleStringProperty(I_GANGI);

    // Næsti leikmaður sem á að gera. Gæti líka verið SimpleObjectProperty fyrir Leikmann
    private final SimpleStringProperty naestiLeikmadurProperty =
            new SimpleStringProperty(leikmenn[0].getNafn());
    /**
     * Smiður - setur hámarksfjölda reita á borði
     *
     * @param radir  fjöldi raða á borði
     * @param dalkar fjöldi dálka á borði
     */
    public Leikur(int radir, int dalkar) {
        teningurVirkur.setValue(true);
        MAXREITUR = radir * dalkar;
    }


    // APInn fyrir viðmótið, þ.e. aðferðir sem controllerar kalla á
    /**
     * kastar tening, færir leikmann, setur næsta leikmann
     * Opnar alert glugga ef spilari lendir á snák eða stiga og segir
     * hvert hann fer
     */
    public void leikaLeik() {
        teningurVirkur.setValue(false);
        // kasta
        teningur.kasta();

        // færir leikmanninn samkvæmt teningi og slöngum og stigum.
        int fjoldiSkrefa = teningur.getTala();

        Timeline timalina = new Timeline();
        for (int i = 1; i <= fjoldiSkrefa; i++) {
            KeyFrame skref = new KeyFrame(Duration.millis(300*i),e -> {
                getLeikmadur().faera(1, MAXREITUR);
            });
            timalina.getKeyFrames().add(skref);
        }

        timalina.setOnFinished(e -> {
            Platform.runLater(() -> {
                int nyrReitur = slongurStigar.uppNidur(getLeikmadur().getReitur());
                if (nyrReitur != getLeikmadur().getReitur()) {
                    int gamliReitur = getLeikmadur().getReitur();
                    int breyting = nyrReitur - gamliReitur;
                    String tegund = (breyting > 0) ? "Stigi" : "Slanga";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(tegund);
                    alert.setHeaderText(tegund + "!!");
                    alert.setContentText(getLeikmadur().getNafn() + ", þú ferð frá reit " + gamliReitur + " til " + nyrReitur);
                    alert.showAndWait();
                    getLeikmadur().setReitur(nyrReitur);
                }

                if (erImarki()) {
                    leikLokid.setValue(true);
                    sigurvegariProperty.set(getLeikmadur().getNafn());
                } else {
                    teningurVirkur.set(true);
                    setNaesti();
                }
            });
        });

        timalina.play();
    }

    /**
     * Hefur nýjan leik. Leikmenn settir á reit eitt
     */
    public void nyrLeikur() {
        leikLokid.setValue(false);
        teningurVirkur.setValue(true);
        leikmenn[0].setReitur(1);
        leikmenn[1].setReitur(1);
    }

    // get og set aðferðir

    /**
     * get aðferð fyrir leikmann númer i
     * @param i 0 eða 1
     * @return leikmaður
     */
    public Leikmadur getLeikmadur(int i) {
        return leikmenn[i];
    }

    /**
     * Skilar næsta leikmanni
     * @return leikmaður
     */
    public Leikmadur getLeikmadur() {
        return leikmenn[naesti];
    }

    /**
     * Skilar teningnum
     * @return teningurinn
     */
    public Teningur getTeningur() {
        return teningur;
    }

    /**
     * Skilar property fyrir næsta leikmann
     * @return næsti leikmaður
     */
    public SimpleStringProperty naestiLeikmadurProperty() {
        return naestiLeikmadurProperty;
    }

    /**
     * skilar leik lokið property
     * @return leik lokið
     */
    public BooleanProperty leikLokidProperty() {
        return leikLokid;
    }

    /**
     * Skilar true ef teningurinn er virkur
     * @return teningurVirkur
     */
    public BooleanProperty teningurVirkur() {
        return teningurVirkur;
    }

    /**
     * Skilar sigurvegara property
     * @return sigurvegara
     */
    public SimpleStringProperty sigurvegariProperty() {
        return sigurvegariProperty;
    }

    /**
     * Skilar offseti á stiga eða slöngu
     * @return offset
     */
    public IntegerProperty uppNidurProperty() {
        return slongurStigar.uppNidurProperty();
    }


    // private hjálparaðferðir
    /**
     * @return segir til um hvort leikmaður er á lokareiti
     */
    private boolean erImarki() {
        return getLeikmadur().getReitur() == MAXREITUR;
    }

    /**
     * setur þann leikmann sem á að gera næst
     */
    private void setNaesti() {
        naesti = (naesti + 1) % leikmenn.length;
        naestiLeikmadurProperty.set(leikmenn[naesti].getNafn());
    }

    /**
     * Prófanaaðferð fyrir þennan klasa, inntak og úttak á console
     * @param args ónotað
     */

    public static void main(String[] args) {
        Leikur leikur = new Leikur(4, 6);
        leikur.nyrLeikur();
        System.out.println(leikur.getLeikmadur(0));
        System.out.println(leikur.getLeikmadur(1));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Á næsti leikmaður að gera? ");
        String svar = scanner.next();
        while ("j".equalsIgnoreCase(svar)) {
            System.out.println();
            System.out.println("leikmaður á að gera " + leikur.getLeikmadur());
            leikur.leikaLeik();

            System.out.println(leikur.getTeningur());
            System.out.println(leikur.getLeikmadur(0));
            System.out.println(leikur.getLeikmadur(1));

            System.out.print("Á næsti leikmaður að gera?");
            svar = scanner.next();
        }
    }


    public SlongurStigar getSlongurStigar() {
        return slongurStigar;
    }
}


