package hashiGRP3.Logic;

import java.util.ArrayList;
import java.util.List;

public class Pont {

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private final Ile ileA, ileB;
    private EtatDuPont etat;         // Représente la valeur entre les ponts
    private final Orientation orientation;    // HORIZONTAL ou VERTICAL
    private List<Pont> conflits;            // La liste des ponts qui, si il sont placé, empeche le faite de posé celui ci

    public Pont(Ile ileA, Ile ileB, EtatDuPont EtatDuPont, Orientation orientation) {
        this.ileA = ileA;
        this.ileB = ileB;
        this.etat = EtatDuPont;
        this.orientation = orientation;
        this.conflits = new ArrayList<>();
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
}