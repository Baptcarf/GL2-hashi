package hashiGRP3.Logic;

public enum Direction {
    HAUT(0, 1), 
    BAS(0, -1), 
    DROITE(1, 0), 
    GAUCHE(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction directionOppose(Direction dir) {
        return switch (dir) {
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
}
