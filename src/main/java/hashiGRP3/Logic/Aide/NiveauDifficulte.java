package hashiGRP3.Logic.Aide;

/**
 * Representant les niveaux de difficulte pour les techniques d'aide
 * Chaque niveau possède une valeur entière associee permettant de trier
 * les techniques par ordre de complexité.
 */
public enum NiveauDifficulte {
    /** Niveau d'aide le plus simple*/
    FACILE(0),

    /** Niveau intermédiaire */
    INTERMEDIAIRE(1),

    /** Niveau avancé */
    DIFFICILE(2);

    /** La valeur de la difficulté */
    private final int value;

    /**
     * Construit un objet NiveauDifficulte
     * @param value le niveau de difficulte (0, 1, 2)
     */
    NiveauDifficulte(int value) {
        this.value = value;
    }

    /**
     * Retourne la valeur associée à ce niveau
     *
     * @return la valeur du niveau de difficulté
     */
    public int getValue() {
        return value;
    }
}