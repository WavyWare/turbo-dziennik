package pl.zsgornik;

import java.util.ArrayList;
import java.util.List;

public class Klasa {
    private final Nauczyciel supervisor;
    private List<Uczen> students;
    private Uczen classLeader;
    private String className;

    public Klasa(Nauczyciel supervisor, ArrayList<Uczen> students, Uczen classLeader, String className) {
        this(className, supervisor, students);
        this.classLeader = classLeader;
    }

    public Klasa(String className, Nauczyciel supervisor, ArrayList<Uczen> students) {
        this(className, supervisor);
        this.students = students;
    }

    public Klasa(String className, Nauczyciel supervisor) {
        this.className = className;
        this.supervisor = supervisor;
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
