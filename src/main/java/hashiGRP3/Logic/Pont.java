package hashiGRP3.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pont {

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private final Ile ileA, ileB;
    private EtatDuPont etatActuel;                    // Représente la valeur entre les ponts
    private EtatDuPont etatCorrect = EtatDuPont.VIDE;                    // Représente les valeurs finales correctes du pont

    private final Orientation orientation;      // HORIZONTAL ou VERTICAL
    private List<Pont> conflits;                // La liste des ponts qui, s'ils sont placé, empêche le fait de poser celui ci

    public Pont(Ile ileA, Ile ileB, EtatDuPont EtatDuPont) {
        if (ileA.equals(ileB)) {
            throw new IllegalArgumentException("Une ile ne peut pas etre lié a elle meme");
        }

        // Dans le jeu, un pont entre ile A et ile B est exactement le même que entre B et A
        // Pour eviter les doublons (ponts identiques mais inversés) on met toujours l'ile la plus à gauche (ou la plus haute) en premier
        Ile premier, second;
        if ((ileA.comparePositionDesIles(ileB)) < 0) {
            premier = ileA;
            second = ileB;
        } else {
            premier = ileB;
            second = ileA;
        }
        this.ileA = premier;
        this.ileB = second;

        this.etatActuel = EtatDuPont;
        this.conflits = new ArrayList<>();

        if (ileA.memeLigne(ileB)) {
            this.orientation = Orientation.HORIZONTAL;
        } else if (ileA.memeColonne(ileB)) {
            this.orientation = Orientation.VERTICAL;
        } else {
            throw new IllegalArgumentException("Les iles doivent être aligné verticalement ou horizontalement");
        }
    }
    
    public void ajouterConflit(Pont pont) {
        this.conflits.add(pont);
    }

    public Ile getileA() {return ileA;}
    public Ile getileB() {return ileB;}
    public EtatDuPont getEtatActuel() {return etatActuel;}
    public EtatDuPont getEtatCorrect() {return etatCorrect;}
    public Orientation getOrientation() {return orientation;}
    public List<Pont> getConflits() {return conflits;}

    public void setEtatActuel(EtatDuPont etat) {this.etatActuel = etat;}
    public void setEtatCorrect(EtatDuPont etat) {this.etatCorrect = etat;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pont pont)) return false;
        return ileA.equals(pont.ileA) && ileB.equals(pont.ileB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ileA, ileB);
    }

    @Override
    public String toString() {
        return "Pont{" +
                "ileA=" + ileA.getCoordonnees() +
                ", ileB=" + ileB.getCoordonnees() +
                ", etat=" + etatActuel +
                ", orientation=" + orientation;    }
    }