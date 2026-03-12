package hashiGRP3.Logic.Aide;

public enum NiveauDifficulte {
    FACILE(0),
    INTERMEDIAIRE(1),
    DIFFICILE(2);

    private final int value;

    NiveauDifficulte(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}