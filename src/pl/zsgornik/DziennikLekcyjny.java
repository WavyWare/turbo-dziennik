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
        this.lessons = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        if (loggedAs != null) {
            return false;
        }

        try {
            Nauczyciel result = teachers.stream()
                    .filter(x -> username.equals(x.getUsername()))
                    .filter(x -> Util.hash(password).equals(x.getPasswordHash()))
                    .findFirst()
                    .orElseThrow();

            loggedAs = result;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        loggedAs = null;
    }

    public static Nauczyciel getLoggedAs() {
        return loggedAs;
    }

    public List<Klasa> getGroups() {
        return groups;
    }

    public List<Nauczyciel> getTeachers() {
        return teachers;
    }

    public void pushGroup(Klasa group) {
        groups.add(group);
    }

    public List<Przedmiot> getSubjects() {
        return subjects;
    }

    public List<Ocena> getGrades() {
        return grades;
    }

    public void pushGrade(Ocena grade) {
        grades.add(grade);
    }

    public void pushSubject(Przedmiot subject) {
        subjects.add(subject);
    }

    public List<Lekcja> getLessons() {
        return lessons;
    }

    public void pushTeacher(Nauczyciel teacher) {
        teachers.add(teacher);
        loggedAs = teacher;
    }
    public void addLesson(Lekcja lesson) {
        lessons.add(lesson);
    }

    public double reportAverageGrade(Uczen student) {
        OptionalDouble avg = grades.stream()
                .filter(x -> x.getStudent().equals(student) && x.getStudent().hashCode() == student.hashCode())
                .mapToDouble(Ocena::getValue)
                .average();

        return avg.isPresent() ? avg.getAsDouble(): -1;
    }

    public double reportPresencePercent(Uczen student) {
        int allLessons = 0;
        int presentLessons = 0;

        for (Lekcja lesson : lessons) {
            if (lesson.getGroup().getStudents().contains(student)) {
                allLessons++;
                Obecnosc attendance = lesson.findAttendance(student);
                if (attendance != null) {
                    Boolean wasPresent = attendance.getStatus().getWasPresent();
                    if (Boolean.TRUE.equals(wasPresent)) {
                        presentLessons++;
                    }
                }
            }
        }

        if (allLessons == 0) {
            return -1;
        }

        return (presentLessons * 100.0) / allLessons;
    }
}
