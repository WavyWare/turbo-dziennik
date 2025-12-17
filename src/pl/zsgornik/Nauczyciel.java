package pl.zsgornik;

public class Nauczyciel {
    private String fullName;
    private static int nextId = 1;
    private final int id;

    public Nauczyciel(String fullName) {
        this.fullName = fullName;
        this.id = nextId++;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
