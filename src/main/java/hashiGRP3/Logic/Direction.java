package hashiGRP3.Logic;

public enum Direction {
    HAUT(0, -1), 
    BAS(0, 1), 
    DROITE(1, 0), 
    GAUCHE(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction directionOppose() {
        return switch (this) {
            case HAUT -> BAS;
            case BAS -> HAUT;
            case DROITE -> GAUCHE;
            case GAUCHE -> DROITE;
        };
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

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
