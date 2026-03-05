package hashiGRP3.Logic;

public enum Mode {
    HISTORIQUE,
    TEMPORAIRE,
    ERREUR;

    @Override
    public String toString() {
        return switch (this) {
            case HISTORIQUE -> "HISTORIQUE";
            case TEMPORAIRE -> "TEMPORAIRE";
            case ERREUR -> "ERREUR";
        };
    }
}
