package vinnsla;

import java.util.HashSet;

public class SpurningaReitir {
    private final HashSet<Integer> spurningaReitir;

    public SpurningaReitir() {
        spurningaReitir = new HashSet<>();
        spurningaReitir.add(10);
        spurningaReitir.add(19);
        spurningaReitir.add(14);
    }
    public boolean erSpurningaReitur(int reitur) {
        return spurningaReitir.contains(reitur);
    }
}
