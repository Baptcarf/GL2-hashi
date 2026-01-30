package hashiGRP3.Logic;

/**
 * Les directions possibles sur la grille
 * Chaque direction possède un delta de déplacement (dx, dy)
 * Permet d'obtenir la direction opposée ainsi que le delta pour chaque direction
 */
public enum Direction {
    /** Deplacement vers le haut (y - 1) */
    HAUT(0, -1), 
    /** Deplacement vers le bas (y + 1) */
    BAS(0, 1), 
    /** Deplacement vers la droite (x + 1) */
    DROITE(1, 0), 
    /** Deplacement vers la gauche (x - 1) */
    GAUCHE(-1, 0);

    /** Variation x pour cette direction */
    private final int dx;
    /** Variation y pour cette direction */
    private final int dy;

    /**
     * Construit une direction avec ses déplacements associés
     * @param dx variation x
     * @param dy variation y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Retourne la direction opposee
     * @return direction opposee
     */
    public Direction directionOppose() {
        return switch (this) {
            case HAUT -> BAS;
            case BAS -> HAUT;
            case DROITE -> GAUCHE;
            case GAUCHE -> DROITE;
        };
    }

    /**
     * Donne le delta en x de cette direction
     * @return dx
     */
    public int getDx() {
        return dx;
    }

    /**
     * Donne le delta en y de cette direction
     * @return dy
     */
    public int getDy() {
        return dy;
    }

    /**
     * Le nom de la direciton
     * @return "haut", "bas", "droite" ou "gauche"
     */
    @Override
    public String toString() {
        return switch (this) {
            case HAUT -> "haut";
            case BAS -> "bas";
            case DROITE -> "droite";
            case GAUCHE -> "gauche";
        };
    }
}