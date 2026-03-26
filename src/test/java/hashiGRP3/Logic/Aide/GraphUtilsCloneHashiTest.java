package hashiGRP3.Logic.Aide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class GraphUtilsCloneHashiTest {

    @Test
    public void testCloneHashiCopieLesEtats() {
        Hashi original = new Hashi();
        Ile haut = new Ile(new Coordonnees(1, 0), 2);
        Ile bas = new Ile(new Coordonnees(1, 2), 2);
        Ile gauche = new Ile(new Coordonnees(0, 1), 2);
        Ile droite = new Ile(new Coordonnees(2, 1), 2);

        original.ajouterIle(haut);
        original.ajouterIle(bas);
        original.ajouterIle(gauche);
        original.ajouterIle(droite);
        original.initialisationToutLesPonts();
        original.initialisationToutLesConflits();

        Pont pontVerticalOriginal = haut.getPont(Direction.BAS);
        pontVerticalOriginal.setEtatActuel(EtatDuPont.SIMPLE);
        pontVerticalOriginal.setEtatCorrect(EtatDuPont.DOUBLE);
        pontVerticalOriginal.setEstHypothese(true);
        original.setModeHypothese(true);
        original.setErreur(true);

        Hashi clone = GraphUtils.clonerHashi(original);

        assertNotSame(original, clone);
        assertEquals(original.getIles().size(), clone.getIles().size());
        assertEquals(original.getPonts().size(), clone.getPonts().size());
        assertTrue(clone.getHypothese());
        assertTrue(clone.EstEtatErreur());

        Ile hautClone = clone.getIle(1, 0);
        Ile basClone = clone.getIle(1, 2);
        assertNotNull(hautClone);
        assertNotNull(basClone);

        Pont pontVerticalClone = clone.getPont(hautClone, basClone);
        assertNotNull(pontVerticalClone);
        assertEquals(EtatDuPont.SIMPLE, pontVerticalClone.getEtatActuel());
        assertEquals(EtatDuPont.DOUBLE, pontVerticalClone.getEtatCorrect());
        assertTrue(pontVerticalClone.isEstHypothese());

        pontVerticalClone.setEtatActuel(EtatDuPont.DOUBLE);
        assertEquals(EtatDuPont.SIMPLE, pontVerticalOriginal.getEtatActuel());

        pontVerticalOriginal.setEtatActuel(EtatDuPont.VIDE);
        assertEquals(EtatDuPont.DOUBLE, pontVerticalClone.getEtatActuel());
    }

    @Test
    public void testCloneHashiConserveLesConflits() {
        Hashi original = new Hashi();
        Ile haut = new Ile(new Coordonnees(1, 0), 2);
        Ile bas = new Ile(new Coordonnees(1, 2), 2);
        Ile gauche = new Ile(new Coordonnees(0, 1), 2);
        Ile droite = new Ile(new Coordonnees(2, 1), 2);

        original.ajouterIle(haut);
        original.ajouterIle(bas);
        original.ajouterIle(gauche);
        original.ajouterIle(droite);
        original.initialisationToutLesPonts();
        original.initialisationToutLesConflits();

        Pont pontVerticalOriginal = haut.getPont(Direction.BAS);
        Pont pontHorizontalOriginal = gauche.getPont(Direction.DROITE);
        assertTrue(pontVerticalOriginal.getConflits().contains(pontHorizontalOriginal));

        Hashi clone = GraphUtils.clonerHashi(original);

        Pont pontVerticalClone = clone.getPont(clone.getIle(1, 0), clone.getIle(1, 2));
        Pont pontHorizontalClone = clone.getPont(clone.getIle(0, 1), clone.getIle(2, 1));

        assertNotNull(pontVerticalClone);
        assertNotNull(pontHorizontalClone);
        assertEquals(1, pontVerticalClone.getConflits().size());
        assertTrue(pontVerticalClone.getConflits().contains(pontHorizontalClone));
        assertFalse(pontVerticalClone.getConflits().contains(pontHorizontalOriginal));
    }
}
