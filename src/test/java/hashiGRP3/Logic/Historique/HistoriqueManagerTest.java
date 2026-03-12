package hashiGRP3.Logic.Historique;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

import java.util.EnumSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Les EnumSets sont temporaires (j'en avait marre de voir les erreurs)
public class HistoriqueManagerTest {

    private HistoriqueManager historique;
    private Pont pont;

    @BeforeEach
    public void setUp() {
        historique = new HistoriqueManager();

        // Deux îles alignées horizontalement
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(2, 0), 1);

        pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
    }

    @Test
    public void undo_empty_shouldReturnFalse() {
        assertFalse(historique.undo());
    }

    @Test
    public void redo_empty_shouldReturnFalse() {
        assertFalse(historique.redo());
    }

    @Test
    public void ajouterAction_thenUndo_shouldRestoreEtatAvant() {

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));

        pont.setEtatActuel(EtatDuPont.SIMPLE);

        assertTrue(historique.undo());
        assertEquals(EtatDuPont.VIDE, pont.getEtatActuel());
    }

    @Test
    public void ajouterAction_thenUndo_thenRedo_shouldRestoreEtatApres() {

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));

        pont.setEtatActuel(EtatDuPont.SIMPLE);

        assertTrue(historique.undo());
        assertEquals(EtatDuPont.VIDE, pont.getEtatActuel());

        assertTrue(historique.redo());
        assertEquals(EtatDuPont.SIMPLE, pont.getEtatActuel());
    }

    @Test
    public void clear_shouldEmptyUndoAndRedoStacks() {

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));

        historique.undo(); // met une action dans redoStack

        historique.clear();

        assertFalse(historique.undo());
        assertFalse(historique.redo());
    }

    @Test
    public void ajouterNouvelleAction_shouldClearRedoStack() {

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));
        historique.undo();

        // Nouvelle action → doit effacer redo
        historique.ajouterAction(pont, EtatDuPont.SIMPLE, EtatDuPont.DOUBLE, EnumSet.of(Mode.HISTORIQUE));

        assertFalse(historique.redo());
    }

    @Test
    public void multipleActions_shouldRespectLIFO() {

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));
        pont.setEtatActuel(EtatDuPont.SIMPLE);

        historique.ajouterAction(pont, EtatDuPont.SIMPLE, EtatDuPont.DOUBLE, EnumSet.of(Mode.HISTORIQUE));
        pont.setEtatActuel(EtatDuPont.DOUBLE);

        // Undo 1 → SIMPLE
        assertTrue(historique.undo());
        assertEquals(EtatDuPont.SIMPLE, pont.getEtatActuel());

        // Undo 2 → VIDE
        assertTrue(historique.undo());
        assertEquals(EtatDuPont.VIDE, pont.getEtatActuel());

        // Redo 1 → SIMPLE
        assertTrue(historique.redo());
        assertEquals(EtatDuPont.SIMPLE, pont.getEtatActuel());

        // Redo 2 → DOUBLE
        assertTrue(historique.redo());
        assertEquals(EtatDuPont.DOUBLE, pont.getEtatActuel());
    }

    @Test
    public void multiplePonts_shouldHandleIndependently() {

        Ile ile3 = new Ile(new Coordonnees(0, 1), 1);
        Ile ile4 = new Ile(new Coordonnees(2, 1), 1);

        Pont pont2 = new Pont(ile3, ile4, EtatDuPont.VIDE);

        historique.ajouterAction(pont, EtatDuPont.VIDE, EtatDuPont.SIMPLE, EnumSet.of(Mode.HISTORIQUE));
        historique.ajouterAction(pont2, EtatDuPont.VIDE, EtatDuPont.DOUBLE, EnumSet.of(Mode.HISTORIQUE));

        pont.setEtatActuel(EtatDuPont.SIMPLE);
        pont2.setEtatActuel(EtatDuPont.DOUBLE);

        // Undo doit annuler pont2 d'abord (LIFO)
        assertTrue(historique.undo());
        assertEquals(EtatDuPont.VIDE, pont2.getEtatActuel());

        // Puis pont1
        assertTrue(historique.undo());
        assertEquals(EtatDuPont.VIDE, pont.getEtatActuel());
    }
}
