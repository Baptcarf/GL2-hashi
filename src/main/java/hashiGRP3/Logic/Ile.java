package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.Map;

public class Ile {

    private static int compteurId = 0;
    private final int id;                           // Identifiant unique de l'île

    private final Coordonnees coordonnees;          // Coordonnées de l'ile relative à la grille, (0, 0) en haut à gauche
    private final int nbPontsRequis;                // Nombre de ponts à connecter à cette île
    private Map<Direction, Pont> directionPonts;    // Liaison d'une direction à un pont 

    public Ile(Coordonnees coordonnees, int nbPontsRequis) {
        this.id = ++compteurId;
        this.coordonnees = coordonnees;
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

    public boolean memeLigne(Ile autre) {
        return this.coordonnees.memeLigne(autre.coordonnees);
    }

    public boolean memeColonne(Ile autre) {
        return this.coordonnees.memeColonne(autre.coordonnees);
    }

    public int comparePositionDesIles(Ile autre) {
        return this.coordonnees.compareTo(autre.coordonnees);
    }

    public Coordonnees getCoordonnees() {return coordonnees;}

    public int getNbPontsRequis() {return nbPontsRequis;}
    
    public Map<Direction, Pont> getDirectionPonts() {return directionPonts;}
    public Pont getPont(Direction direction) {return directionPonts.get(direction);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ile ile)) return false;
        return id == ile.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(coordonnees, id);
    }

    @Override
    public String toString() {
        return "Ile n°" + id + "|" + coordonnees + ", (" + getNbPontsActuels() + "/" + nbPontsRequis + ") ponts connecté\n"  
                + directionPonts;
    }

    
}