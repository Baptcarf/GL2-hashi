//Attribut au paquet
package hashiGRP3.Logic.Historique;

//Imports
import java.util.ArrayDeque;
import java.util.Deque;

import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.General;
import hashiGRP3.Logic.Pont;
import hashiGRP3.DatabaseManager;

/**
 * Gestionnaire d'historique pour les opérations,
 * Utilise deux piles pour garder trace des commandes.
 */
public class HistoriqueManager {

	/* Classe d'action à enregistrer */
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

    //Var
    private final Deque<Action> undoStack = new ArrayDeque<>();
    private final Deque<Action> redoStack = new ArrayDeque<>();

    /** Rempli la bdd des coups */
    public void remplir() {
        General.getDb().remplirCoup(this, General.getIdUtilisateur(), General.getHashi(), General.getId_grille());
    }

    /**
     * Enregistre une nouvelle action dans l'historique
     * @param pont Le pont sur lequel l'action a été faite.
     * @param avant L'etat du pont avant l'action.
     * @param apres L'etat du pont après l'action.
     */
    public void ajouterAction(Pont pont, EtatDuPont avant, EtatDuPont apres) {
        undoStack.push(new Action(pont, avant, apres));
        General.getDb().addCoup(General.getIdUtilisateur(), General.getId_grille(), pont.getileA().getId(),
                pont.getileB().getId(), avant.getValue(), apres.getValue());
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }

    /**
     * Enregistre une nouvelle action dans l'historique sans l'ajouté à la BDD.
     * @param pont Le pont sur lequel l'action a été faite.
     * @param avant L'etat du pont avant l'action.
     * @param apres L'etat du pont après l'action.
     */
    public void ajouterActionNotSave(Pont pont, EtatDuPont avant, EtatDuPont apres) {
        undoStack.push(new Action(pont, avant, apres));
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }

    /**
     * Annule la dernière action (Ctrl+Z)
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
     * Renvoie si l'historique est vide ou non.
     * @return bool
     */
    public boolean isEmpty() {
        return undoStack.isEmpty() && redoStack.isEmpty();
    }
   
    /**
     * Renvoie si l'historique des anciens mouvements est vide.
     * @return bool
     */
    public boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * Renvoie si l'historique des mouvements annulés est vide.
     * @return bool
     */
    public boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }

    /** Vide tout l'historique */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
