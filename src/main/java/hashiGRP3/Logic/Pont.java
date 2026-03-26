//Att
package hashiGRP3.Logic;

//imports
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* Représente un pont reliant deux iles
* Un pont possede une orientation, un état actuel, et un état correcte 
* et peut être en conflit avec d'autres ponts.
*/
public class Pont {

    /** Les orientations possibles d'un pont.*/
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    /** île reliée par le pont */
    private final Ile ileA;
    /** île reliée par le pont */
    private final Ile ileB;
    /** Etat actuel du pont */
    private EtatDuPont etatActuel;
    /** Etat correct du pont */
    private EtatDuPont etatCorrect = EtatDuPont.VIDE; 
    /** Orientation du pont*/
    private final Orientation orientation;
    /** Liste des ponts qui peuvent renter en conflit avec ce pont */
    private List<Pont> conflits;
    /** Flag qui sert à savoir si on est en mode hypothèse*/
    private boolean estHypothese = false;

    /**
     * Construit un pont entre deux ile
     * Les iles doivent être alignées horizontalement ou verticalement
     * 
     * L'ordre des iles est normalisé pour que A=B soit la meme chose que B=A a la création
     * La normalisation se fait en plaçant l'ile la plus à gauche (ou la plus haute)
     * en premier, selon l'ordre défini par comparePositionDesIles
     * 
     * @param ileA Premiere île
     * @param ileB Deuxieme île
     * @param EtatDuPont Etat initial du pont
     * @throws IllegalArgumentException si les iles sont identiques ou pas alignés
     */
    public Pont(Ile ileA, Ile ileB, EtatDuPont EtatDuPont) {
        if (ileA.equals(ileB)) {
            throw new IllegalArgumentException("Une ile ne peut pas etre lié a elle meme");
        }

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
    
    /**
     * Ajoute un pont a la liste des conflits de ce pont
     * @param pont Pont en conflit
     */
    public void ajouterConflit(Pont pont) {
        this.conflits.add(pont);
    }

    /**
     * Vérifie si un pont peut être ajouté sans entrer en conflit avec d'autres ponts existants
     * @return true si il est possible de rajouter un pont, false si un pont empeche le placement
     */
    public boolean pontEstPossible() {
        int total = 0;
        for (Pont pont : conflits) {
            total += pont.etatActuel.getValue();
        }
        return total == 0;
    }

    /** 
     * Getter sur la première ile.
     * @return Première île reliée 
     */
    public Ile getileA() {
	    return ileA;
    }
    
    /** 
     * Getter sur la seconde ile.
     * @return Deuxième île reliée 
     */
    public Ile getileB() {
	    return ileB;
    }
    
    /** 
     * Getter qui retourne l'etat du pont.
     * @return Etat actuel du pont 
     */
    public EtatDuPont getEtatActuel() {
	    return etatActuel;
    }

    /** 
     * Getter 
     * @return Etat correct du pont 
     */
    public EtatDuPont getEtatCorrect() {
	    return etatCorrect;
    }

    /** 
     * Getter 
     * @return True si le pont est dans l'etat correcte 
     */
    public boolean estCorrect(){
	    return getEtatActuel() == getEtatCorrect();
    }

    /** 
     * Getter 
     * @return Orientation du pont 
     */
    public Orientation getOrientation() {
	    return orientation;
    }

    /** 
     * Getter 
     * @return Liste des ponts en conflit 
     */
    public List<Pont> getConflits() {
	    return conflits;
    }

    /**
     * Modifie l'état actuel du pont
     * @param etat Nouvel état
     */
    public void setEtatActuel(EtatDuPont etat) {
	    this.etatActuel = etat;
    }

    /**
     * Modifie l'état correct du pont
     * @param etat Nouvel état correct
     */
    public void setEtatCorrect(EtatDuPont etat) {
	    this.etatCorrect = etat;
    }

    /**
     * Cycle l'état actuelle du pont : VIDE - SIMPLE - DOUBLE - VIDE
     * Si le pont est vide, on doit verifier si il est possible de mettre un pont
     */
    public void cycler() {
        this.etatActuel = switch (this.etatActuel) {
            case VIDE -> pontEstPossible() ? EtatDuPont.SIMPLE : EtatDuPont.VIDE;
            case SIMPLE -> EtatDuPont.DOUBLE;
            case DOUBLE -> EtatDuPont.VIDE;
        };
    }

    /**
     * Renvoie si le mode est en hypothèse
     * @return true si le mode est hypothèse.
     */
    public boolean isEstHypothese() { 
	    return estHypothese; 
    }

    /**
     * Active ou désactive le mode hypothèse.
     * @param h Un boolean.
     */
    public void setEstHypothese(boolean h) { 
	    this.estHypothese = h; 
    }

    /**
     * Deux ponts sont egaux s'ils relient les mêmes îles, mais on se fiche de l'ordre de la connection A=B ou B=A
     * @param o Objet à comparer
     * @return true si les ponts relient les memes iles, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pont pont)) return false;
        return ileA.equals(pont.ileA) && ileB.equals(pont.ileB);
    }

    /**
     * Hashcode base sur les deux iles
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(ileA, ileB);
    }

    /**
     * Représentation en texte du pont.
     * @return une string uqi decrit le pont
     */
    @Override
    public String toString() {
        return ileA.getCoordonnees() + 
               (orientation == Orientation.HORIZONTAL ? "-H-" : "-V-" ) + 
               ileB.getCoordonnees() +
               " [" + etatCorrect + "]";    
    }
}
