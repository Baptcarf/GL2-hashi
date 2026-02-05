package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente une île dans le jeu Hashi.
 * Une île est caractérisée par ses coordonnées sur la grille, le nombre de ponts requis
 * pour la connecter, et les ponts déjà connectés dans différentes directions.
 */
public class Ile {

    private static int compteurId = 0;
    private final int id;                           // Identifiant unique de l'île

    private final Coordonnees coordonnees;          // Coordonnées de l'ile relative à la grille, (0, 0) en haut à gauche
    private final int nbPontsRequis;                // Nombre de ponts à connecter à cette île
    private Map<Direction, Pont> directionPonts;    // Liaison d'une direction à un pont 


     /**
     * Construit une île avec des coordonnées et un nombre de ponts requis
     * 
     * @param coordonnees Coordonnées de l'île
     * @param nbPontsRequis Nombre de ponts requis pour cette île
     * @throws IllegalArgumentException si le nombre de ponts requis est négatif ou supérieur à 8
     * @throws IllegalArgumentException si les coordonnées sont négatives
     */
    public Ile(Coordonnees coordonnees, int nbPontsRequis) {
        if (nbPontsRequis < 0) {
            throw new IllegalArgumentException("Le nombre de ponts requis ne peut pas être négatif");
        } else if (nbPontsRequis > 8) {
            throw new IllegalArgumentException("Le nombre de ponts requis ne peut pas être supérieur à 8");
        }

        if (coordonnees.x <0 || coordonnees.y < 0) {
            throw new IllegalArgumentException("Les coordonnées ne peuvent pas être négatives");
        }
        this.id = ++compteurId;
        this.coordonnees = coordonnees;
        this.nbPontsRequis = nbPontsRequis;
        this.directionPonts = new HashMap<>();
    }

    /**
     * Ajoute un pont dans la direction donnée.
     * 
     * @param direction la direction dans laquelle ajouter le pont
     * @param pont le pont à ajouter
     */
    public void ajouterPonts(Direction direction, Pont pont) { //ajoute un pont à la direction donnée
        this.directionPonts.put(direction, pont);
    }

    /**
     * Calcule et retourne le nombre total de ponts actuellement connectés à cette île.
     * Le nombre est calculé en sommant les valeurs des états actuels de tous les ponts connectés.
     * 
     * @return le nombre de ponts actuellement connectés à cette île
     */
    public int getNbPontsActuels() {
        int total = 0;
        for (Pont pont : directionPonts.values()) {
            total += pont.getEtatActuel().getValue();
        }
        return total;
    }

    /**
     * Vérifie si cette île et une autre île sont situées sur la même ligne.
     * 
     * @param autre l'autre île à comparer
     * @return true si les deux îles sont sur la même ligne, false sinon
     */
    public boolean memeLigne(Ile autre) {
        return this.coordonnees.memeLigne(autre.coordonnees);
    }

    /**
     * Vérifie si cette île et une autre île sont situées sur la même colonne.
     * 
     * @param autre l'autre île à comparer
     * @return true si les deux îles sont sur la même colonne, false sinon
     */
    public boolean memeColonne(Ile autre) {
        return this.coordonnees.memeColonne(autre.coordonnees);
    }

    /**
    * Compare la position de cette île avec une autre île.
    * 
    * @param autre l'île à comparer
    * @return un entier négatif, zéro ou positif selon que cette île est respectivement avant, égale ou après l'autre île dans l'ordre défini
    */
    public int comparePositionDesIles(Ile autre) {
        return this.coordonnees.compareTo(autre.coordonnees);
    }

    /**
     * Retourne les coordonnées de cette île sur la grille.
     * 
     * @return les coordonnées de cette île
     */
    public Coordonnees getCoordonnees() {return coordonnees;}

    /**
     * Retourne le nombre de ponts requis pour connecter cette île.
     * 
     * @return le nombre de ponts requis pour cette île
     */
    public int getNbPontsRequis() {return nbPontsRequis;}
    
    /**
     * Retourne la map associant chaque direction à un pont connecté.
     * 
     * @return la map des directions et des ponts connectés à cette île
     */
    public Map<Direction, Pont> getDirectionPonts() {return directionPonts;}
    
    /**
     * @param direction la direction du pont à récupérer
     * @return le pont connecté dans la direction donnée, ou null s'il n'y en a pas
     */
    public Pont getPont(Direction direction) {return directionPonts.get(direction);}

    /**
     * Vérifie l'égalité entre cette île et un autre objet.
     * Deux îles sont égales si elles ont le même identifiant unique.
     * 
     * @param o l'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ile ile)) return false;
        return id == ile.id;
    }

    /**
     * Retourne le code de hachage de cette île basé sur ses coordonnées et son identifiant.
     * 
     * @return le code de hachage de cette île
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(coordonnees, id);
    }

    /**
     * Retourne une représentation textuelle de cette île.
     * Inclut l'identifiant, les coordonnées, le nombre de ponts connectés sur requis,
     * et la liste des ponts par direction.
     * 
     * @return une chaîne de caractères représentant cette île
     */
    @Override
    public String toString() {
        return "Ile n°" + id + "|" + coordonnees + ", (" + getNbPontsActuels() + "/" + nbPontsRequis + ") ponts connecté\n"  
                + directionPonts;
    }

    
}