package hashiGRP3.Logic.Historique;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Pont;

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
        Action(Pont pont, EtatDuPont etatAvant, EtatDuPont etatApres, EnumSet<Mode> modes) {
            this.pont = pont;
            this.etatAvant = etatAvant;
            this.etatApres = etatApres;
            this.modes = modes;
        }

        public EnumSet<Mode> getModes() {
            return modes;
        }

        public Boolean isMode(Mode mode) {
            return modes.contains(mode);
        }

        public Boolean retireTemporaire(){
            if (modes.contains(Mode.TEMPORAIRE)) {
                modes.remove(Mode.TEMPORAIRE);
                return true;
            } else {
                return false;
            }
        }
    }

    private final Deque<Action> undoStack = new ArrayDeque<>();
    private final Deque<Action> redoStack = new ArrayDeque<>();
    
    /**
     * Enregistre une nouvelle action dans l'historique
     */
    public void ajouterAction(Pont pont, EtatDuPont avant, EtatDuPont apres, EnumSet<Mode> modes) {
        undoStack.push(new Action(pont, avant, apres, modes));
        redoStack.clear(); // Nouvelle action = on perd le futur (redo)
    }
    
    /**
     * Annule la dernière action (Ctrl+Z)
     * @return true si annulé, false sinon
     */
    public boolean undo() {
        if (undoStack.isEmpty()) return false;
        
        Action action = undoStack.pop();
        action.pont.setEtatActuel(action.etatAvant);
        redoStack.push(action);
        return true;
    }
    
    /**
     * Rétablit la dernière action annulée (Ctrl+Y)
     * @return true si rétabli, false sinon
     */
    public boolean redo() {
        if (redoStack.isEmpty()) return false;
        
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