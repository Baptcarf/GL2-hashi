package hashiGRP3.Logic;

import java.util.ArrayList;
import java.util.List;

public class Pont {

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private final Ile ileSource, ileCible;
    private EtatDuPont etat;         // Représente la valeur entre les ponts
    private final Orientation direction;    // HORIZONTAL ou VERTICAL
    private List<Pont> conflits;            // La liste des ponts qui, si il sont placé, empeche le faite de posé celui ci

    public Pont(Ile ileSource, Ile ileCible, EtatDuPont EtatDuPont, Orientation direction) {
        this.ileSource = ileSource;
        this.ileCible = ileCible;
        this.etat = EtatDuPont;
        this.direction = direction;
        this.conflits = new ArrayList<>();
    }
    
    public void ajouterConflit(Pont pont) {
        this.conflits.add(pont);
    }
    
    public Ile getIleSource() {return ileSource;}
    public Ile getIleCible() {return ileCible;}
    public EtatDuPont getEtat() {return etat;}
    public Orientation getDirection() {return direction;}
    public List<Pont> getConflits() {return conflits;}

    public void setEtat(EtatDuPont etat) {this.etat = etat;}
}