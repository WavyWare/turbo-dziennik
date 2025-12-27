package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.Lekcja;
import pl.zsgornik.model.Ocena;
import pl.zsgornik.model.Uczen;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class OcenyScreen extends Screen {
    public OcenyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\nturbo dziennik - OCENY");
        System.out.println("\n1. Lista ocen");
        System.out.println("2. Dodaj ocenę");
        System.out.println("3. Usuń ocenę");
        System.out.println("4. Zmień wartość");
        System.out.println("5. Zmień komentarz");
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
            case "3":
                Ocena deleteGrade = selectionHelper.selectGrade();
                if (deleteGrade != null) {
                    dziennik.getGrades().remove(deleteGrade);
                    pauseAndReturn("Usunięto ocenę");
                }
                break;
            case "4":
                Ocena valueGrade = selectionHelper.selectGrade();
                if (valueGrade == null) {
                    break;
                }
                System.out.print("Podaj nową wartość oceny: ");
                double value = menuManager.getScanner().nextDouble();
                if (value < 1 || value > 6) {
                    pauseAndReturn("Wartość musi być między 1 a 6");
                    break;
                }
                valueGrade.setValue(value);
                pauseAndReturn("Pomyślnie dokonano zmian");
                break;
            case "5":
                Ocena commentGrade = selectionHelper.selectGrade();
                if (commentGrade == null) {
                    break;
                }
                System.out.print("Podaj nowy komentarz: ");
                String comment = menuManager.getScanner().nextLine();
                if (comment.isEmpty()) {
                    pauseAndReturn("Komentarz nie może być pusty");
                    break;
                }
                commentGrade.setComment(comment);
                pauseAndReturn("Pomyślnie dokonano zmian");
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }


    private void addGrade() {
        System.out.println("\nturbo dziennik - DODAWANIE OCENY");

        Lekcja lesson = findLastTeacherLesson();
        if (lesson == null) {
            System.out.println("Brak lekcji prowadzonych przez tego nauczyciela.");
            pauseAndReturn();
            return;
        }

        Uczen student = selectionHelper.chooseStudent(lesson.getGroup());
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
        
        if (teacherLessons.isEmpty()) {
            return null;
        }
        
        Lekcja latestLesson = teacherLessons.getFirst();

        for (Lekcja lesson: teacherLessons) {
            if (latestLesson.getDate().isBefore(lesson.getDate())) {
                latestLesson = lesson;
            }
        }
        return latestLesson;
    }

    private void displayGrades() {
        System.out.println("\nturbo dziennik - LISTA OCEN");
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
        pauseAndReturn();
    }
}

