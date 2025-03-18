package vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.HashMap;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Model klasi fyrir slöngur og stiga
 *
 *
 *****************************************************************************/
public class SlongurStigar {

    // vörpun frá reit yfir í slöngu (neikvætt) eða stiga (jákvætt
    private final HashMap<Integer, Integer> slongurStigar;

    private final IntegerProperty uppNidurProperty =
            new SimpleIntegerProperty(); // heiltala sem geymir offset-ið fyrir stiga eða slöngu

    /**
     * Smiður sem frumstillir hashmap fyrir slöngur og stiga. Harðkóðað, mætti lesa úr skrá
     */
    public SlongurStigar() {
        // frumstilla HashMap
        slongurStigar = new HashMap<>();
        slongurStigar.put(3, 6);
        slongurStigar.put(12, 5);
        slongurStigar.put(17, 20);
        slongurStigar.put(27, 21);
    }

    /**
     * Setur tölu í eiginleikann
     * @param tala núverandi reitur á borðinu
     * @return fjöldi reita sem klifrað/sigið er á eða reiturinn sjálfur
     */
    public int uppNidur(int tala) {
   //     System.out.println ("frá " + tala + " til " + slongurStigar.getOrDefault(tala, 0));
        uppNidurProperty.set(slongurStigar
                .getOrDefault(tala, 0)); // annað hvort upp/niður eða ekkert (0)
        return slongurStigar.getOrDefault(tala, tala); // fjöldi reita eða reiturinn sjálfur
    }

    /**
     * Skilar fjölda reita sem farið er upp eða niður eða 0 ef enginn stigi eða slanga
     * @return fjölda reita eða 0
     */
    public IntegerProperty uppNidurProperty() {
        return uppNidurProperty;
    }

    /**
     * Skilar stiga eða slöngu fyrir reit i
     * @param i númer reits
     * @return slanga eða stigi
     */
    public int lookup(int i) {
        return slongurStigar.getOrDefault(i, 0);
    }
}
