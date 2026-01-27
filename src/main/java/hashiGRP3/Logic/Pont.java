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
    private EtatDuPont etat;         // Représente la valeur entre les ponts
    private final Orientation orientation;    // HORIZONTAL ou VERTICAL
    private List<Pont> conflits;            // La liste des ponts qui, si il sont placé, empeche le faite de posé celui ci

    public Pont(Ile ileA, Ile ileB, EtatDuPont EtatDuPont) {
        if (ileA.equals(ileB)) {
            throw new IllegalArgumentException("Une ile ne peut pas etre lié a elle meme");
        }

        // Dans le jeu, un pont entre ile A et ile B est exactement le meme que entre B et A
        // Pour evité les doublons (ponts identique mais inversé) on met toujours l'ile la plus a gauche (ou la plus haute) en premier
        if (comparePositionDesIles(ileA, ileB) < 0) {
            this.ileA = ileA;
            this.ileB = ileB;
        } else {
            this.ileA = ileB;
            this.ileB = ileA;
        }

        this.etat = EtatDuPont;
        this.conflits = new ArrayList<>();

        if (ileA.getY() == ileB.getY()) {
            this.orientation = Orientation.HORIZONTAL;
        } else if (ileA.getX() == ileB.getX()) {
            this.orientation = Orientation.VERTICAL;
        } else {
            throw new IllegalArgumentException("Les iles doivent etre aligné verticalement ou horizontalement");
        }
    }
 
    // Cette fonction sert à "trier" deux iles pour qu'elles soient toujours dans le même ordre
    // On compare d'abord la position X, et ensuite Y si ils sont sur la meme colonne.
    // -1 si l'ile A doit etre placé avant l'ile B. 
    // 1 si l'ile A doit etre placé apres l'ile B.
    // 0 si c'est les meme iles.
    private int comparePositionDesIles(Ile ileA, Ile ileB) {
        if (ileA.getX() != ileB.getX()) {
            if (ileA.getX() < ileB.getX()) {
                return -1;
            } else {
                return 1;
            }
            
        } else {
            if (ileA.getY() < ileB.getY()) {
                return -1;
            } else if (ileA.getY() > ileB.getY()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void ajouterConflit(Pont pont) {
        this.conflits.add(pont);
    }

    public Ile getileA() {return ileA;}
    public Ile getileB() {return ileB;}
    public EtatDuPont getEtat() {return etat;}
    public Orientation getOrientation() {return orientation;}
    public List<Pont> getConflits() {return conflits;}

    public void setEtat(EtatDuPont etat) {this.etat = etat;}

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
}