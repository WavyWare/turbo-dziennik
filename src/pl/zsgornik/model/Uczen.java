package pl.zsgornik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Uczen {
    private final String fullName;
    private final List<Uwaga> behaviouralNotes;

    public Uczen(String fullName) {
        this.fullName = fullName;
        this.behaviouralNotes = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public List<Uwaga> getBehaviouralNotes() {
        return behaviouralNotes;
    }

    public void pushNote(Uwaga note) {
        behaviouralNotes.add(note);
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
