
package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.Map;

public class Ile {

    public enum Direction {
        HAUT, BAS, DROITE, GAUCHE;
    }

    private final int x, y;                         // Coordonnées de l'ile relative a la grille, (0, 0) en haut a gauche
    private final int nbPontsRequis;                // Nombre de ponts à connecter a cette ile
    private Map<Direction, Pont> directionPonts;    // Liaison d'une direction a un ponts 

    public Ile(int x, int y, int nbPontsRequis) {
        this.x = x;
        this.y = y;
        this.nbPontsRequis = nbPontsRequis;
        this.directionPonts = new HashMap<>();
    }
    
}