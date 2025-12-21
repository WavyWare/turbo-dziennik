package pl.zsgornik;

import java.util.ArrayList;
import java.util.List;

public class Przedmiot {
    private final List<Nauczyciel> teachers;
    private TypPrzedmiotu type;

    public Przedmiot(TypPrzedmiotu type) {
        this.type = type;
        this.teachers = new ArrayList<>();
    }

    public void addTeacher(Nauczyciel teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public List<Nauczyciel> getTeachers() {
        return teachers;
    }

    public TypPrzedmiotu getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
