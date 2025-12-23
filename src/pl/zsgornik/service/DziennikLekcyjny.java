package pl.zsgornik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import pl.zsgornik.model.*;
import pl.zsgornik.util.Tuple;
import pl.zsgornik.util.Util;

import javax.security.auth.Subject;

public class DziennikLekcyjny {
    private final List<Klasa> groups;
    private final List<Nauczyciel> teachers;
    private final List<Przedmiot> subjects;
    private final List<Ocena> grades;
    private final List<Lekcja> lessons;
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
            loggedAs = teachers.stream()
                    .filter(x -> username.equals(x.getUsername()))
                    .filter(x -> Util.hash(password).equals(x.getPasswordHash()))
                    .findFirst()
                    .orElseThrow();
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

    private Klasa getGroupByStudent(Uczen student) {
        return groups.stream()
                .filter(x -> x.getStudents().contains(student))
                .findFirst()
                .orElseThrow();
    }

    public List<Tuple<Przedmiot, Double>> getAverageGradeBySubjects(Uczen student) {
        List<Tuple<Przedmiot, Double>> result = new ArrayList<>();
        Klasa group = getGroupByStudent(student);

        List<Przedmiot> groupSubjects = lessons.stream()
                .filter(x -> x.getGroup().equals(group))
                .map(Lekcja::getSubject)
                .distinct()
                .toList();

        for (Przedmiot subject : groupSubjects) {
            OptionalDouble avg = grades.stream()
                    .filter(x -> x.getStudent().equals(student))
                    .filter(x -> x.getLesson().getSubject().equals(subject))
                    .mapToDouble(Ocena::getValue)
                    .average();

            if (avg.isPresent()) {
                result.add(new Tuple<>(subject, avg.getAsDouble()));
            } else {
                result.add(new Tuple<>(subject, -1.0));
            }
        }
        return result;
    }


    public void printStudentReport(Uczen student) {
        Klasa group = getGroupByStudent(student);
        System.out.println("----------------------------------------");
        System.out.println("RAPORT DLA UCZNIA: " + student.getFullName());
        System.out.println("KLASA: " + group.getClassName());
        System.out.println("----------------------------------------");

        double avgTotal = reportAverageGrade(student);
        if (avgTotal > 0) {
            System.out.printf("Srednia ocen ogolna: %.2f%n", avgTotal);
        } else {
            System.out.println("Srednia ocen ogolna: Brak ocen");
        }

        double presence = reportPresencePercent(student);
        if (presence >= 0) {
            System.out.printf("Frekwencja: %.2f%%%n", presence);
        } else {
            System.out.println("Frekwencja: Brak danych (brak lekcji)");
        }

        System.out.println("\nOCENY Z PRZEDMIOTOW:");
        List<Tuple<Przedmiot, Double>> subjectsData = getAverageGradeBySubjects(student);
        
        if (subjectsData.isEmpty()) {
            System.out.println("  Brak przedmiotow przypisanych do klasy (brak odbytych lekcji).");
        } else {
            for (Tuple<Przedmiot, Double> entry : subjectsData) {
                String subjectName = entry.getFirst().toString();
                Double avg = entry.getSecond();
                
                if (avg == -1.0) {
                    System.out.println("  " + subjectName + " - Brak ocen");
                } else {
                    System.out.printf("  %s - %.2f%n", subjectName, avg);
                }
            }
        }
        System.out.println("----------------------------------------");
    }


}
