package pl.zsgornik.enums;

public enum TypOceny {
    CELUJACA(6),
    BARDZO_DOBRA(5),
    DOBRA(4),
    DOSTATECZNA(3),
    DOPUSZCZAJACA(2),
    NIEDOSTATECZNA(1);

    private final int value;

    TypOceny(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
