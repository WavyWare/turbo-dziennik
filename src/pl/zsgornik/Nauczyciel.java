package pl.zsgornik;

public class Nauczyciel {
    private String fullName;
    private static int nextId = 1;
    private final int id;
    private String username;
    private String passwordHash;

    public Nauczyciel(String fullName) {
        this.id = nextId++;
        this.fullName = fullName;
        this.username = null;
        this.passwordHash = null;
    }

    public Nauczyciel(String fullName, String username, String password) {
        this.id = nextId++;
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = Util.hash(password);
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

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
