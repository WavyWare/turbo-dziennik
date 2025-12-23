package pl.zsgornik.model;

import pl.zsgornik.util.Util;

public record Nauczyciel(String fullName, String username, String passwordHash) {
    public Nauczyciel(String fullName, String username, String passwordHash) {
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = Util.hash(passwordHash);
    }

    @Override
    public String toString() {
        return fullName;
    }
}
