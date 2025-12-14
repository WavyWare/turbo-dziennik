package pl.zsgornik;

public enum TypPrzedmiotu {
    POLSKI("Język polski"),
    MATEMATYKA("Matematyka"),
    ANGIELSKI("Język angielski"),
    FIZYKA("Fizyka"),
    NIEMIECKI("Język niemiecki"),
    HISTORIA("Historia"),
    BIOLOGIA("Biologia"),
    GEOGRAFIA("Geografia");

    private final String name;
    TypPrzedmiotu(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
