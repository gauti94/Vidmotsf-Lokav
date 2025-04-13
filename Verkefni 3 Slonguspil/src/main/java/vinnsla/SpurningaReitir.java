package vinnsla;

import java.util.HashSet;

/**
 * Klasi sem bætir inn custom spurningareitnum á reiti 10, 19 og 14
 */
public class SpurningaReitir {
    private final HashSet<Integer> spurningaReitir;

    public SpurningaReitir() {
        spurningaReitir = new HashSet<>();
        spurningaReitir.add(10);
        spurningaReitir.add(19);
        spurningaReitir.add(14);
    }

    /**
     * Aðferð sem tjékkar hvort núverandi reitur er spurningareitur
     * @param reitur
     * @return
     */
    public boolean erSpurningaReitur(int reitur) {
        return spurningaReitir.contains(reitur);
    }
}
