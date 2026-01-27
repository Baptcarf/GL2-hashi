package hashiGRP3.Logic;

public enum EtatDuPont {

    VIDE(0),
    SIMPLE(1),
    DOUBLE(2);

    private final int value;

    EtatDuPont(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}