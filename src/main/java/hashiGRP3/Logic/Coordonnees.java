package hashiGRP3.Logic;

public class Coordonnees implements Comparable<Coordonnees>{
    public final int x, y;

    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Cette fonction sert à "trier" deux coordonnees pour qu'elles soient toujours dans le même ordre
    // On compare d'abord la position X, et ensuite Y si ils sont sur la meme colonne

    // Retourne un nombre négatif si cette coordonnée est avant l'autre (plus à gauche, puis plus en haut),
    // positif si elle est après, et 0 si coordo egales
    @Override
    public int compareTo(Coordonnees autre) {
        int comparaison = Integer.compare(this.x, autre.x);
        if (comparaison != 0) {
            return comparaison;
        }
        return Integer.compare(this.y, autre.y);
    }

    public boolean memeLigne(Coordonnees autre) {
        return this.y == autre.y;
    }

    public boolean memeColonne(Coordonnees autre) {
        return this.x == autre.x;
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