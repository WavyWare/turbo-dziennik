package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class OcenyScreen extends Screen {
    public OcenyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== OCENY ===");
        System.out.println("\n1. Lista ocen");
        System.out.println("2. Dodaj ocenę");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayGrades();
                break;
            case "2":
                addGrade();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                System.out.println("\nNieprawidłowa opcja");
                System.out.println("Naciśnij Enter aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
        }
    }

    private void addGrade() {
        System.out.println("\n=== DODAWANIE OCENY ===");

        Lekcja lesson = findLastTeacherLesson();
        if (lesson == null) {
            System.out.println("Brak lekcji prowadzonych przez tego nauczyciela.");
            pauseAndReturn();
            return;
        }

        Uczen student = KlasyScreen.chooseStudent(lesson.getGroup());
        if (student == null) {
            pauseAndReturn();
            return;
        }

        System.out.println("Podaj wartość oceny (od 1 do 6)");
        double gradeValue = menuManager.getScanner().nextDouble();
        if (gradeValue < 1 || gradeValue > 6) {
            pauseAndReturn();
            return;
        }
        System.out.println("Podaj komentarz oceny (może być pusty)");
        String comment = menuManager.getScanner().nextLine();

        Ocena newGrade;
        if (comment.isEmpty()) {
             newGrade = new Ocena(gradeValue, lesson, student);
        } else {
            newGrade = new Ocena(gradeValue, lesson, student, comment);
        }
        dziennik.pushGrade(newGrade);
        System.out.println("\nDodano ocenę: " + student.getFullName() +
                " - " + gradeValue + " (" + comment + ")");
        pauseAndReturn();
    }

    private Lekcja findLastTeacherLesson() {
        List<Lekcja> lessons = dziennik.getLessons();
        List<Lekcja> teacherLessons = lessons.stream()
                .filter(x -> x.getTeacher().equals(DziennikLekcyjny.getLoggedAs()))
                .toList();
        Lekcja latestLesson = teacherLessons.getFirst();

        for (Lekcja lesson: teacherLessons) {
            if (latestLesson.getDate().isBefore(lesson.getDate())) {
                latestLesson = lesson;
            }
        }
        return latestLesson;
    }

    private void displayGrades() {
        System.out.println("\n=== LISTA OCEN ===");
        List<Ocena> grades = dziennik.getGrades();
        if (grades.isEmpty()) {
            System.out.println("Brak ocen w systemie");
        } else {
            for (int i = 0; i < grades.size(); i++) {
                Ocena grade = grades.get(i);
                System.out.println((i + 1) + ". " + grade.getStudent().getFullName() + 
                    " - " + grade.getValue() + " (" + grade.getComment() + ")");
            }
        }
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}

