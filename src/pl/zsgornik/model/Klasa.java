package pl.zsgornik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Klasa {
    private Nauczyciel supervisor;
    private final List<Uczen> students;
    private Uczen classLeader;
    private String className;

    public Klasa(String className, Nauczyciel supervisor) {
        this.className = className;
        this.supervisor = supervisor;
        this.students = new ArrayList<>();
    }

    public Klasa(String className) {
        this.className = className;
        this.students = new ArrayList<>();
    }

    public void addStudent(Uczen student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public boolean removeStudent(Uczen student) {
        if (classLeader != null && classLeader.equals(student)) {
            classLeader = null;
        }
        return students.remove(student);
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassLeader(Uczen classLeader) {
        this.classLeader = classLeader;
    }

    public void setSupervisor(Nauczyciel supervisor) {
        this.supervisor = supervisor;
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
