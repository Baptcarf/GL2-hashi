package hashiGRP3.Logic;

/**
 * Represente l'etat d'un pont entre deux iles.
 * Un pont peut etre vide, simple ou double.
 * Chaque etat possede une valeur numerique.
 */
public enum EtatDuPont {

    /** Aucun pont present */
    VIDE(0),
    /** Pont simple */
    SIMPLE(1),
    /** Pont double */
    DOUBLE(2);

    /** Valeur du pont */
    private final int value;

    /**
     * Construit un etat de pont avec sa valeur associee
     * 
     * @param value valeur de l'etat
     */
    EtatDuPont(int value) {
        this.value = value;
    }

    /**
     * Retourne la valeur numerique de l'etat du pont
     * 
     * @return valeur de l'etat (0, 1 ou 2)
     */
    public int getValue() {
        return value;
    }

    /**
     * La valeur du pont
     * 
     * @return "vide", "simple", "double"
     */
    @Override
    public String toString() {
        return switch (this) {
            case VIDE -> "VIDE";
            case SIMPLE -> "SIMPLE";
            case DOUBLE -> "DOUBLE";
        };
    }

    public static EtatDuPont fromValue(int value) {
        for (EtatDuPont etat : EtatDuPont.values()) {
            if (etat.getValue() == value) {
                return etat;
            }
        }
        return null;
    }
}