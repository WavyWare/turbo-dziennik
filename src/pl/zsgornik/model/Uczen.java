package pl.zsgornik.model;

import java.util.Objects;

public class Uczen {
    private final String fullName;

    public Uczen(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Uczen uczen = (Uczen) o;
        return Objects.equals(fullName, uczen.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }
}
