package pl.zsgornik.model;

import pl.zsgornik.enums.StatusObecnosci;

public class Obecnosc {
    private final Uczen student;
    private final Lekcja lesson;
    private StatusObecnosci status;

    public Obecnosc(Uczen student, Lekcja lesson, StatusObecnosci status) {
        this.student = student;
        this.lesson = lesson;
        this.status = status;
    }

    public Uczen getStudent() {
        return student;
    }

    public StatusObecnosci getStatus() {
        return status;
    }

    public void setStatus(StatusObecnosci status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %s", student.getFullName(), lesson, status.getFullName());
    }
}
