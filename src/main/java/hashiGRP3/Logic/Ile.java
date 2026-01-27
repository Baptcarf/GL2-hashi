
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

    public void ajouterPonts(Direction direction, Pont pont) {
        this.directionPonts.put(direction, pont);
    }

    public int getNbPontsActuels() {
        int total = 0;
        for (Pont pont : directionPonts.values()) {
            total += pont.getEtat().getValue();
        }
        return total;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getNbPontsRequis() {return nbPontsRequis;}
    public Map<Direction, Pont> getDirectionPonts() {return directionPonts;}
    public Pont getPont(Direction direction) {return directionPonts.get(direction);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ile ile)) return false;
        return x == ile.x && y == ile.y;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}