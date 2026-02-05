package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.Map;

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
        if (nbPontsRequis < 0) { //renvoi une exception si le nombre de ponts requis est négatif
            throw new IllegalArgumentException("Le nombre de ponts requis ne peut pas être négatif");
        } else if (nbPontsRequis > 8) { //renvoi une exception si le nombre de ponts requis est supérieur à 8
            throw new IllegalArgumentException("Le nombre de ponts requis ne peut pas être supérieur à 8");
        }

        if (coordonnees.x <0 || coordonnees.y < 0) { //renvoi une exception si au moins une des 2 coordonnées est négatives
            throw new IllegalArgumentException("Les coordonnées ne peuvent pas être négatives");
        }
        this.id = ++compteurId;
        this.coordonnees = coordonnees;
        this.nbPontsRequis = nbPontsRequis;
        this.directionPonts = new HashMap<>();
    }

    public void ajouterPonts(Direction direction, Pont pont) { //ajoute un pont à la direction donnée
        this.directionPonts.put(direction, pont);
    }

    /** @return le nombre de ponts actuellement connectés à cette île */
    public int getNbPontsActuels() {
        int total = 0;
        for (Pont pont : directionPonts.values()) {
            total += pont.getEtatActuel().getValue();
        }
        return total;
    }

    /**@return true si les deux îles sont sur la même ligne, false sinon */
    public boolean memeLigne(Ile autre) {
        return this.coordonnees.memeLigne(autre.coordonnees);
    }

    /**@return true si les deux îles sont sur la même colonne, false sinon */
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

    /**@return les coordonnées de cette île */
    public Coordonnees getCoordonnees() {return coordonnees;}

    /**@return le nombre de ponts requis pour cette île */
    public int getNbPontsRequis() {return nbPontsRequis;}
    
    /**@return la map des directions et des ponts connectés à cette île */
    public Map<Direction, Pont> getDirectionPonts() {return directionPonts;}
    
    /**
     * @param direction la direction du pont à récupérer
     * @return le pont connecté dans la direction donnée, ou null s'il n'y en a pas
     */
    public Pont getPont(Direction direction) {return directionPonts.get(direction);}

    /* Redéfinition de equals */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ile ile)) return false;
        return id == ile.id;
    }
    /* Redéfinition de hashCode */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(coordonnees, id);
    }

    /* Redéfinition de toString */
    @Override
    public String toString() {
        return "Ile n°" + id + "|" + coordonnees + ", (" + getNbPontsActuels() + "/" + nbPontsRequis + ") ponts connecté\n"  
                + directionPonts;
    }

    
}