package vinnsla;

import javafx.beans.property.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
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
            new Leikmadur[]{new Leikmadur("Bára"), new Leikmadur("Gísli")};

    private final SimpleBooleanProperty leikLokid = new SimpleBooleanProperty(); // er leik lokið

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
        MAXREITUR = radir * dalkar;
    }


    // APInn fyrir viðmótið, þ.e. aðferðir sem controllerar kalla á
    /**
     * kastar tening, færir leikmann, setur næsta leikmann
     *
     * @return skilar true ef leik er lokið
     */
    public boolean leikaLeik() {
        // kasta
        teningur.kasta();

        // færir leikmanninn samkvæmt teningi og slöngum og stigum.
        if (faeraLeikmann()) {
            leikLokid.setValue(true);
            sigurvegariProperty.set(getLeikmadur().getNafn());
            return true;
        }

        // næsti leikmaður á að gera
        setNaesti();
        return false;
    }

    /**
     * Færir leikmann á næsta reit samkvæmt teningi. Fer upp stiga eða niður snák.
     * @return skilar true ef leikmaður komst í mark
     */
    private boolean faeraLeikmann() {

        // færa eftir að tening hefur verið kastað.
        getLeikmadur().faera(teningur.getTala(), MAXREITUR);

        // er stigi eða snákur
        int nyrReitur = slongurStigar.uppNidur(getLeikmadur().getReitur());

        // færa í samræmi við stiga eða snák
        if (nyrReitur !=
                getLeikmadur().getReitur()) {
            System.out.println("slanga eða stigi " + nyrReitur);
            getLeikmadur().setReitur(nyrReitur);
        }

        // er leikmaður kominn í mark
        return erImarki();
    }

    /**
     * Hefur nýjan leik. Leikmenn settir á reit eitt
     */
    public void nyrLeikur() {
        leikLokid.setValue(false);
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
            if (leikur.leikaLeik()) {
                System.out.println(leikur.getLeikmadur() + "kominn í mark");
                return;
            }

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


