package pl.zsgornik;

import java.util.ArrayList;
import java.util.List;

public class Klasa {
    private final Nauczyciel supervisor;
    private final List<Uczen> students;
    private String className;

    public Klasa(String className, Nauczyciel supervisor) {
        this.className = className;
        this.supervisor = supervisor;
        this.students = new ArrayList<>();
    }

    public void addStudent(Uczen student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public Nauczyciel getSupervisor() {
        return supervisor;
    }

    public List<Uczen> getStudents() {
        return students;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
}
