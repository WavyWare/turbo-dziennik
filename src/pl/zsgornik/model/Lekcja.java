package pl.zsgornik.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pl.zsgornik.enums.StatusObecnosci;

public class Lekcja {
    private final Przedmiot subject;
    private final Nauczyciel teacher;
    private final Klasa group;
    private final LocalDate date;
    private final List<Obecnosc> attendances;

    public Lekcja(Przedmiot subject, Nauczyciel teacher, Klasa group, LocalDate date) {
        this.subject = subject;
        this.teacher = teacher;
        this.group = group;
        this.date = date;
        this.attendances = new ArrayList<>();
    }

    public void registerAttendance(Uczen student, StatusObecnosci status) {
        if (!group.getStudents().contains(student)) {
            throw new IllegalArgumentException("Uczeń " + student.getFullName() + " nie należy do klasy " + group.getClassName());
        }

        Obecnosc existing = findAttendance(student);
        if (existing != null) {
            existing.setStatus(status);
        } else {
            attendances.add(new Obecnosc(student, this, status));
        }
    }

    public Obecnosc findAttendance(Uczen student) {
        for (Obecnosc attendance : attendances) {
            if (attendance.getStudent().equals(student)) {
                return attendance;
            }
        }
        return null;
    }

    public Przedmiot getSubject() {
        return subject;
    }

    public Nauczyciel getTeacher() {
        return teacher;
    }

    public Klasa getGroup() {
        return group;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Obecnosc> getAttendances() {
        return attendances;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", subject, date, group);
    }
}
