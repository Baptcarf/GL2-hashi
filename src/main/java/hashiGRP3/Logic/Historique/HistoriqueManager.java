package hashiGRP3.Logic.Historique;

import java.util.ArrayDeque;
import java.util.Deque;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.General;
import hashiGRP3.Logic.Pont;

import hashiGRP3.DatabaseManager;

import hashiGRP3.Logic.Hashi;

/**
 * Gestionnaire d'historique pour les opérations
 * Utilise deux piles pour garder trace des commandes
 */
public class HistoriqueManager {

    private static class Action {
        final Pont pont;
        final EtatDuPont etatAvant;
        final EtatDuPont etatApres;

        Action(Pont pont, EtatDuPont etatAvant, EtatDuPont etatApres) {
            this.pont = pont;
            this.etatAvant = etatAvant;
            this.etatApres = etatApres;
        }
    }

    private final Deque<Action> undoStack = new ArrayDeque<>();
    private final Deque<Action> redoStack = new ArrayDeque<>();

    public void remplir() {
        General.getDb().remplirCoup(this, General.getIdUtilisateur(), General.getHashi());

    }

    /**
     * Enregistre une nouvelle action dans l'historique
     */
    public void ajouterAction(Pont pont, EtatDuPont avant, EtatDuPont apres) {
        undoStack.push(new Action(pont, avant, apres));
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }

    /**
     * Annule la dernière action (Ctrl+Z)
     * 
     * @return true si annulé, false sinon
     */
    public boolean undo() {
        if (undoStack.isEmpty())
            return false;

        Action action = undoStack.pop();
        action.pont.setEtatActuel(action.etatAvant);
        redoStack.push(action);
        return true;
    }

    /**
     * Rétablit la dernière action annulée (Ctrl+Y)
     * 
     * @return true si rétabli, false sinon
     */
    public boolean redo() {
        if (redoStack.isEmpty())
            return false;

        Action action = redoStack.pop();
        action.pont.setEtatActuel(action.etatApres);
        undoStack.push(action);
        return true;
    }

    /**
     * Vide tout l'historique
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}