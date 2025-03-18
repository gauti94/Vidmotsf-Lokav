package vidmot;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import vinnsla.Leikur;

import java.util.List;
import java.util.Random;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Stýriklasi fyrir viðmótið
 *
 *
 *****************************************************************************/
public class SnakesController {

    // fastar
    public static final String SLANGA = "slanga";
    public static final String STIGI = "stigi";
    public static final String LEIK_LOKID_LEIKMADUR = "Leik lokið - leikmaður ";
    public static final String LEIKUR_I_GANGI_NAESTI_GERIR_ = "Leikur í gangi næst gerir ";
    public static final String VANN = " vann ";

    // tilviksbreytur
    public Button fxTeningur; // teningahnappurinn
    public Label fxSkilabod; // Skilaboð um hver gerir og hver vinnur
    // skilaboð um slöngu eða stiga
    public Label fxSlangaStigi;
    public Button fxNyrLeikur; // Nýr leikur hnappurinn
    // til að ná í alla reitina
    public GridPane fxBord;

    // vinnslan
    private Leikur leikur = new Leikur(4, 6);

    /**
     * Frumstilling á viðmótshlutum og byrjar leikinn
     */
    public void initialize() {
        List<Node> reitir = fxBord.getChildren();

        // bindur gögn úr vinnslunni við skilaboðin um hver á að gera og hver er sigurvegari
        fxSkilabod.textProperty().bind(
                Bindings.when(leikur.leikLokidProperty())
                        // hlutar á getSigurveigariProperty
                        .then(Bindings.concat(LEIK_LOKID_LEIKMADUR, leikur.sigurvegariProperty(), VANN))
                        .otherwise(Bindings.concat(LEIKUR_I_GANGI_NAESTI_GERIR_, leikur.naestiLeikmadurProperty())));

        fxNyrLeikur.disableProperty().bind(leikur.leikLokidProperty().not());

        // bindur stöðuna á teningnum og nýr leikur hnappnum
        fxTeningur.disableProperty().bind(fxNyrLeikur.disableProperty().not());


        // bindur myndir leikmanna við reitina
        String[] leikmadurStill = {"leikmadur0", "leikmadur1"};
        leikur.getLeikmadur(0).getReiturProperty().addListener((obs, gamlaGildi, nyttGildi) -> {
            reitir.get(gamlaGildi.intValue() - 1).getStyleClass().remove(leikmadurStill[0]);
            reitir.get(nyttGildi.intValue() - 1).getStyleClass().add(leikmadurStill[0]);
        });

        leikur.getLeikmadur(1).getReiturProperty().addListener((obs, gamlaGildi, nyttGildi) -> {
            reitir.get(gamlaGildi.intValue() - 1).getStyleClass().remove(leikmadurStill[1]);
            reitir.get(nyttGildi.intValue() - 1).getStyleClass().add(leikmadurStill[1]);
        });

        // bindur myndir af teningunum við teninginn
        String[] teningaMyndir = {"one", "two", "three", "four", "five", "six"};
        leikur.getTeningur().talaProperty().addListener((obs, gamlaGildi, nyttGildi) -> {
            fxTeningur.getStyleClass().remove(teningaMyndir[gamlaGildi.intValue() - 1]);
            fxTeningur.getStyleClass().add(teningaMyndir[nyttGildi.intValue() - 1]);
        });

        // setja myndir af stiga eða slöngu á reitina
        setjaStigaSlanga();

        // setja random styleClass liti á reitina
        setjaLiti();

        // uppfæra stiga/snáka reitin með skilaboðum
        fxSlangaStigi.textProperty().bind(
                Bindings.when(leikur.uppNidurProperty().isEqualTo(0))
                        .then("Enginn slanga eða stigi")
                        .otherwise(Bindings.concat("Slanga eða stigi ",
                                leikur.uppNidurProperty())));
    }


    /**
     * Handler fyrir að kasta tening
     * @param actionEvent ónotað
     */
    public void teningurHandler(ActionEvent actionEvent) {
        leikur.leikaLeik();
    }

    /**
     * Handler fyrir að hefja leik
     * @param actionEvent ónotað
     */
    public void nyrLeikurHandler(ActionEvent actionEvent) {
        leikur.nyrLeikur();
    }

    // private aðferðir

    /**
     * Setja myndir fyrir slöngu eða stiga á reitina
     */
    private void setjaStigaSlanga() {
        List<Node> reitir = fxBord.getChildren();
        for (Node r : reitir) {
            int i = reitir.indexOf(r);
            int gildi = leikur.getSlongurStigar().lookup(i + 1);
            if (gildi != 0) {
                if (gildi < i + 1) {
                    r.getStyleClass().add(SLANGA);
                } else if (gildi > i + 1) {
                    r.getStyleClass().add(STIGI);
                }
            }
        }
    }

    /**
     * Setur liti á reitina af handahófi
     */
    private void setjaLiti() {
        final Random rand = new Random();
        List<Node> reitir = fxBord.getChildren();

        String[] litir = {"red", "yellow", "blue"};
        reitir.removeLast();  // síðasti reiturinn er Group og við fjarlægjum hann
        for (Object l : reitir) {
            ((Label) l).getStyleClass().add(litir[rand.nextInt(3)]);
        }
    }
}