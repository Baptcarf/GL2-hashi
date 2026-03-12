package hashiGRP3.Logic.Historique;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Pont;

/**
 * Gestionnaire d'historique pour les opérations de pose de ponts.
 * <p>
 * Cette classe implémente le pattern "Command" de manière simplifiée en utilisant
 * deux piles (LIFO) pour permettre les fonctionnalités d'annulation (undo) 
 * et de rétablissement (redo).
 * </p>
 * * @author Groupe 3
 * @version 1.0
 */
public class HistoriqueManager {
    
    /**
     * Représente une action élémentaire sur un pont pour l'historique.
     * Stocke l'état avant et après pour permettre la réversibilité.
     */

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
        private EnumSet<Mode> modes;

        /**
         * Crée une nouvelle instance d'action.
         * * @param pont Le pont concerné par l'action.
         * @param etatAvant L'état du pont avant modification.
         * @param etatApres L'état du pont après modification.
         * @param modes L'ensemble des modes associés à cette action (ex: TEMPORAIRE).
         */
        Action(Pont pont, EtatDuPont etatAvant, EtatDuPont etatApres, EnumSet<Mode> modes) {
            this.pont = pont;
            this.etatAvant = etatAvant;
            this.etatApres = etatApres;
            this.modes = modes;
        }

        /**
         * @return L'ensemble des modes actifs pour cette action.
         */
        public EnumSet<Mode> getModes() {
            return modes;
        }

        /**
         * Vérifie si l'action possède un mode spécifique.
         * * @param mode Le mode à vérifier.
         * @return {@code true} si le mode est présent, {@code false} sinon.
         */
        public Boolean isMode(Mode mode) {
            return modes.contains(mode);
        }

        /**
         * Retire le mode TEMPORAIRE de l'action si celui-ci est présent.
         * * @return {@code true} si le mode a été retiré, {@code false} s'il n'était pas présent.
         */
        public Boolean retireTemporaire(){
            if (modes.contains(Mode.TEMPORAIRE)) {
                modes.remove(Mode.TEMPORAIRE);
                return true;
            } else {
                return false;
            }
        }
    }

    /** Pile des actions annulables. */
    private final Deque<Action> undoStack = new ArrayDeque<>();
    
    /** Pile des actions rétablissables. */
    private final Deque<Action> redoStack = new ArrayDeque<>();
    
    /**
     * Enregistre une nouvelle action dans l'historique.
     * <p>
     * L'action est ajoutée à la pile {@code undoStack} et la pile {@code redoStack} 
     * est vidée car une nouvelle branche d'actions est créée.
     * </p>
     * * @param pont Le pont qui a été modifié.
     * @param avant L'état initial du pont.
     * @param apres Le nouvel état du pont.
     * @param modes Les modes applicables à cette action.
     */
    public void ajouterAction(Pont pont, EtatDuPont avant, EtatDuPont apres, EnumSet<Mode> modes) {
        undoStack.push(new Action(pont, avant, apres, modes));
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }
    
    /**
     * Annule la dernière action effectuée.
     * <p>
     * L'état du pont est restauré à sa valeur précédente ({@code etatAvant}) 
     * et l'action est transférée vers la pile de rétablissement.
     * </p>
     * * @return {@code true} si une action a été annulée, {@code false} si l'historique est vide.
     */
    public boolean undo() {
        if (undoStack.isEmpty()) return false;
        

        Action(Pont pont, EtatDuPont etatAvant, EtatDuPont etatApres) {
            this.pont = pont;
            this.etatAvant = etatAvant;
            this.etatApres = etatApres;
        }
    }

    private final Deque<Action> undoStack = new ArrayDeque<>();
    private final Deque<Action> redoStack = new ArrayDeque<>();

    public void remplir() {
        General.getDb().remplirCoup(this, General.getIdUtilisateur(), General.getHashi(), General.getId_grille());

    }

    /**
     * Enregistre une nouvelle action dans l'historique
     */
    public void ajouterAction(Pont pont, EtatDuPont avant, EtatDuPont apres) {
        undoStack.push(new Action(pont, avant, apres));
        General.getDb().addCoup(General.getIdUtilisateur(), General.getId_grille(), pont.getileA().getId(),
                pont.getileB().getId(), avant.getValue(), apres.getValue());
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }

    public void ajouterActionNotSave(Pont pont, EtatDuPont avant, EtatDuPont apres) {
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
     * Rétablit la dernière action précédemment annulée.
     * <p>
     * L'état du pont est remis à sa valeur "future" ({@code etatApres}) 
     * et l'action est remise dans la pile d'annulation.
     * </p>
     * * @return {@code true} si une action a été rétablie, {@code false} si la pile redo est vide.
     */
    public boolean redo() {
        if (redoStack.isEmpty()) return false;
        
        Action action = redoStack.pop();
        action.pont.setEtatActuel(action.etatApres);
        undoStack.push(action);
        return true;
    }
    
    /**
     * Vide intégralement l'historique (undo et redo).
     * Utile lors de la réinitialisation d'une grille ou du chargement d'une nouvelle partie.
    public boolean isEmpty() {
        return undoStack.isEmpty() && redoStack.isEmpty();
    }
        
    public boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }

    public boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }

    /**
     * Vide tout l'historique
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}