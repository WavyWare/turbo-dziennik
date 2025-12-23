package pl.zsgornik.model;

import pl.zsgornik.util.Util;

public class Nauczyciel {
    private final String fullName;
    private static int nextId = 1;
    private final int id;
    private final String username;
    private final String passwordHash;

    public Nauczyciel(String fullName, String username, String password) {
        this.id = nextId++;
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = Util.hash(password);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
