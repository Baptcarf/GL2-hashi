package hashiGRP3;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hashiGRP3.Logic.*;

public class PontsTest {
    @Test //Test le cas où un pont essaie de relier une île à elle même
    public void PontReliantUneIleAElleMeme(){
        Ile ile1 = new Ile(new Coordonnees(3, 1), 2);
        assertThrows(IllegalArgumentException.class, () -> {
            new Pont(ile1, ile1, EtatDuPont.VIDE);
        });
    }

    @Test //Test le cas où un pont essaie de relier des îles en diagonales
    public void PontReliantsIlesEnDiagonales(){
        Ile ile1 = new Ile(new Coordonnees(3, 1), 2);
        Ile ile2 = new Ile(new Coordonnees(4, 2), 2);
        assertThrows(IllegalArgumentException.class, () -> {
            new Pont(ile1, ile2, EtatDuPont.VIDE);
        });
    }

    @Test //Test le coas où l'île la plus en bas à droite est donnée en premier argument
    public void PlusLoinDabbord(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile2, ile1, EtatDuPont.VIDE);
        assert(pont.getileA() == ile1 && pont.getileB() == ile2);
    }
}
