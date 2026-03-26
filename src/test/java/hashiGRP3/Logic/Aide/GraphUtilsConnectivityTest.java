package hashiGRP3.Logic.Aide;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class GraphUtilsConnectivityTest {

    @Test
    public void composanteConnexeTrouveLesIlesConnectees() {
        Hashi hashi = new Hashi();
        Ile a = new Ile(new Coordonnees(0, 0), 1);
        Ile b = new Ile(new Coordonnees(0, 2), 1);
        Ile c = new Ile(new Coordonnees(2, 2), 1);

        hashi.ajouterIle(a);
        hashi.ajouterIle(b);
        hashi.ajouterIle(c);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        Pont pa = a.getPont(Direction.BAS);
        Pont pb = b.getPont(Direction.DROITE);

        // Activer A-B seulement
        pa.setEtatActuel(EtatDuPont.SIMPLE);

        Set<Ile> compA = GraphUtils.composanteConnexe(a, hashi);
        assertEquals(2, compA.size(), "A et B doivent être dans la même composante");
        assertTrue(compA.contains(a));
        assertTrue(compA.contains(b));
        assertFalse(compA.contains(c));
    }

    @Test
    public void estConnexeDetecteQuandToutesLesIlesSontReliees() {
        Hashi hashi = new Hashi();
        Ile a = new Ile(new Coordonnees(0, 0), 1);
        Ile b = new Ile(new Coordonnees(0, 2), 1);
        Ile c = new Ile(new Coordonnees(0, 4), 1);

        hashi.ajouterIle(a);
        hashi.ajouterIle(b);
        hashi.ajouterIle(c);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        Pont pab = a.getPont(Direction.BAS);
        Pont pbc = b.getPont(Direction.BAS);

        // Relier A-B et B-C
        pab.setEtatActuel(EtatDuPont.SIMPLE);
        pbc.setEtatActuel(EtatDuPont.SIMPLE);

        assertTrue(GraphUtils.estConnexe(hashi));

        // Déconnecter C
        pbc.setEtatActuel(EtatDuPont.VIDE);
        assertFalse(GraphUtils.estConnexe(hashi));
    }
}
