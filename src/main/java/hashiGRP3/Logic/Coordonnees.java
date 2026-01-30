package hashiGRP3.Logic;

/**
 * Represente une paire de coordonnees (x, y) sur une grille.
 * Les coordonnées permettent de comparer, trier,
 * et vérifier l'alignement (même ligne ou colonne) entre deux points
 */
public class Coordonnees implements Comparable<Coordonnees>{
    /** abscisse colonne sur la grille commence a 0 */
    public final int x;
    /** ordonnee ligne sur la grille commence a 0 */
    public final int y;    

    /**
     * Construit un objet Coordonnees à partir de deux entiers
     * @param x abscisse (colonne)
     * @param y ordonnée (ligne)
     */
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Permet de trier des Coordonnees en comparant d'abord x, puis y si les x sont égaux.
     * @param autre autre Coordonnees a comparer
     * @return negatif si cette coordonnées est inférieur a autre, 
     *         positif si  cette coordonnées est supérieur a autre, 
     *         0 si égal
     */
    @Override
    public int compareTo(Coordonnees autre) {
        int comparaison = Integer.compare(this.x, autre.x);
        if (comparaison != 0) {
            return comparaison;
        }
        return Integer.compare(this.y, autre.y);
    }

    /**
     * Verifie si deux Coordonnees sont sur la meme ligne (meme y)
     * @param autre autre Coordonnees
     * @return true si meme ligne, false sinon
     */
    public boolean memeLigne(Coordonnees autre) {
        return this.y == autre.y;
    }

    /**
     * Verifie si deux Coordonnees sont sur la meme colonne (meme x)
     * @param autre autre Coordonnees
     * @return true si meme colonne, false sinon
     */
    public boolean memeColonne(Coordonnees autre) {
        return this.x == autre.x;
    }

    /**
     * Teste l'egalite entre deux Coordonnees
     * @param o objet a comparer
     * @return true si egal, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordonnees)) return false;
        Coordonnees c = (Coordonnees) o;
        return x == c.x && y == c.y;
    }

    /**
     * genere un hashcode base sur x et y pour utilisation en hashmap ou hashset
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    /**
     * Représentation lisible des coordonnées, sous la forme (x,y)
     * @return chaîne de caractères "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}