package pl.zsgornik;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class DziennikLekcyjny {
    private List<Klasa> groups;
    private List<Nauczyciel> teachers;
    private List<Przedmiot> subjects;
    private List<Ocena> grades;
    private List<Lekcja> lessons;
    private static Nauczyciel loggedAs;

    public DziennikLekcyjny(ArrayList<Klasa> groups, ArrayList<Nauczyciel> teachers, ArrayList<Przedmiot> subjects, ArrayList<Ocena> grades) {
        this.groups = groups;
        this.teachers = teachers;
        this.subjects = subjects;
        this.grades = grades;
    }

    public void login(String username, String password) {
        if (loggedAs != null) {
            return;
        }

        Nauczyciel result = teachers.stream()
                .filter(x -> username.equals(x.getUsername()))
                .filter(x -> Util.hash(password).equals(x.getPasswordHash()))
                .findFirst()
                .orElseThrow();

        System.out.println("Logging in" + result.getUsername() + "...");
        loggedAs = result;
    }

    private double reportAverageGrade(Uczen student) {
        OptionalDouble avg = grades.stream()
                .filter(x -> x.getStudent().equals(student) && x.getStudent().hashCode() == student.hashCode())
                .mapToDouble(Ocena::getValue)
                .average();

        return avg.isPresent() ? avg.getAsDouble(): -1;
    }

    private double reportPresencePercent(Uczen student) {
        Klasa group = groups.stream()
                .filter(x -> x.getStudents().contains(student))
                .findFirst()
                .orElseThrow();

        int allLessons = Math.toIntExact(groups.stream()
                .filter(x -> x.equals(group) && x.hashCode() == group.hashCode())
                .count());

        int lessonsPresentByStudent;
        return 0;
    }
}
