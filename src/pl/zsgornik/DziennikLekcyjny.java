package pl.zsgornik;

import java.util.List;
import java.util.OptionalDouble;

public class DziennikLekcyjny {
    private List<Klasa> group;
    private List<Nauczyciel> teachers;
    private List<Przedmiot> subjects;
    private List<Ocena> grades;
    private static Nauczyciel loggedAs;

    public DziennikLekcyjny(List<Klasa> group, List<Nauczyciel> teachers, List<Przedmiot> subjects, List<Ocena> grades) {
        this.group = group;
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


}
