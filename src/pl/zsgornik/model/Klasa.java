package pl.zsgornik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Klasa klasa = (Klasa) o;
        return Objects.equals(supervisor, klasa.supervisor) && Objects.equals(students, klasa.students) && Objects.equals(classLeader, klasa.classLeader) && Objects.equals(className, klasa.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supervisor, students, classLeader, className);
    }

    @Override
    public String toString() {
        return className;
    }
}
