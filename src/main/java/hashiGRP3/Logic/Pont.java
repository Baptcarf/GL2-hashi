package hashiGRP3.Logic;

import java.util.ArrayList;
import java.util.List;

public class Pont {

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum EtatDuPont {
        VIDE,
        SIMPLE,
        DOUBLE
    }

    private final Ile ileSource, ileCible;
    private final EtatDuPont poids;         // Représente la valeur entre les ponts
    private final Orientation direction;    // HORIZONTAL ou VERTICAL
    private List<Pont> conflits;            // La liste des ponts qui, si il sont placé, empeche le faite de posé celui ci


    public Pont(Ile ileSource, Ile ileCible, EtatDuPont EtatDuPont, Orientation direction) {
        this.ileSource = ileSource;
        this.ileCible = ileCible;
        this.poids = EtatDuPont;
        this.direction = direction;
        this.conflits = new ArrayList<>();
    }
}