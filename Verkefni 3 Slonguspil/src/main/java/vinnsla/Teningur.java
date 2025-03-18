package vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Model klasi fyrir tening
 *
 *
 *****************************************************************************/

public class Teningur {

    private static final int MAX = 6; // fjöldi hliða á teningi
    private final IntegerProperty talaProperty =
            new SimpleIntegerProperty(MAX); // teningshliðin eftir síðasta kast
    private final Random rand = new Random(); // random hlutur - búin til einu sinni


    /**
     * get aðferð fyrir talaProperty
     *
     * @return property sem inniheldur teningshliðina eftir síðasta kast
     */
    public IntegerProperty talaProperty() {
        return talaProperty;
    }


    /**
     * Kastar tening þannig að fundinn sé tala af handahófi á bilinu 1 til MAX+1
     */
    public void kasta() {

        talaProperty.set(rand.nextInt(1, MAX+1));
    }

    /**
     * skilar streng sem lýsir hlutnum
     * @return strengur með innihaldi hlutarins
     */
    @Override
    public String toString() {
        return "Teningur{" +
                "talaProperty=" + talaProperty +
                '}';
    }

    /**
     * skilar teningshliðinni eftir síðasta kast
     * @return teningshliðin (heiltala)
     */
    public int getTala() {
        return talaProperty.get();
    }

    /**
     * Test forrit fyrir klasann
     * @param args
     */
    public static void main (String [] args) {
        Teningur t = new Teningur();
        System.out.println (t);
    }
}
