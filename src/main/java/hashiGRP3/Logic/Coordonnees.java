package hashiGRP3.Logic;

public class Coordonnees {
    public final int x, y;

    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordonnees)) return false;
        Coordonnees c = (Coordonnees) o;
        return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    public int getX() {return x;}
    public int getY() {return y;}
}