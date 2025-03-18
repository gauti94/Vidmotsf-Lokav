package vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Leikmadur {

    private final  SimpleStringProperty  leikmadur = new SimpleStringProperty();
    private final IntegerProperty reiturProperty =
            new SimpleIntegerProperty(2); // 2 til að listener taki eftir breytingu í 1


    public Leikmadur(String leikmadur) {
        this.leikmadur.setValue (leikmadur);
    }

    /**
     * Færir leikmann á reit reitur en markið er í reit max
     * @param reitur reitur sem leikmaður er færður á
     * @param max mark-reitur á borðinu
     */
    public void faera (int reitur, int max) {
        this.reiturProperty.set (Math.min(max, this.reiturProperty.get()+reitur));
    }

    public int getReitur () {
        return reiturProperty.get();
    }

    public IntegerProperty getReiturProperty() {
        return reiturProperty;
    }

    @Override
    public String toString() {
        return "Leikmadur{" +
                "leikmadur=" + leikmadur.get() +
                ", reiturProperty=" + reiturProperty.get() +
                '}';
    }

    public void setReitur(int reitur) {
        reiturProperty.set(reitur);
    }

    public String getNafn() {
        return leikmadur.get();
    }
}
