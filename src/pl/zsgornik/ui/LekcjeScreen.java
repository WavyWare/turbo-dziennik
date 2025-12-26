package pl.zsgornik.ui;

import java.time.LocalDate;
import java.util.List;

import pl.zsgornik.enums.StatusObecnosci;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class LekcjeScreen extends Screen {
    public LekcjeScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== LEKCJE ===");
        System.out.println("\n1. Lista lekcji");
        System.out.println("2. Dodaj lekcję");
        System.out.println("3. Zarejestruj obecności");
        System.out.println("4. Zmień obecności");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayLessons();
                break;
            case "2":
                addLesson();
                break;
            case "3":
                registerClassAttendance();
                break;
            case "4":
                Lekcja attendanceLesson = selectLesson();
                assert attendanceLesson != null;
                Obecnosc attendance = selectAttendance(attendanceLesson);
                if (attendance == null) {
                    return;
                }
                StatusObecnosci newStatus = StatusObecnosci.chooseType();
                attendance.setStatus(newStatus);
                pauseAndReturn();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }

    private void registerClassAttendance() {
        Lekcja lesson = LekcjeScreen.selectLesson();
        if (lesson == null) {
            pauseAndReturn("Nieprawidłowa opcja");
            return;
        }
        Klasa group = lesson.getGroup();
        List<Uczen> students = group.getStudents();

        for (Uczen u : students) {
            System.out.printf("\nTyp obecności dla %s", u);
            StatusObecnosci type = StatusObecnosci.chooseType();
            if (type == null) {
                pauseAndReturn("Nieprawidłowa opcja");
                break;
            }
            lesson.registerAttendance(u, type);
        }
        System.out.println("\nDodano obecności!");
        pauseAndReturn();
    }

    public static Lekcja selectLesson() {
        List<Lekcja> lessons  = dziennik.getLessons();

        if (lessons.isEmpty()) {
            System.out.println("Brak lekcji w systemie");
            return null;
        }

        System.out.println("\n=== WYBIERZ LEKCJE ===");
        for (int i = 0; i < lessons.size(); i++) {
            Lekcja lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson);
        }
        System.out.println("0. Anuluj");
        System.out.print("Wybierz numer: ");

        String input = menuManager.getScanner().nextLine();
        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy numer");
            return null;
        }

        if (choice == 0) {
            return null;
        }

        if (choice < 1 || choice > lessons.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return lessons.get(choice - 1);
    }

    private Obecnosc selectAttendance(Lekcja lesson) {
        List<Obecnosc> attendance;
        try {
            attendance = lesson.getAttendances();
        } catch (NullPointerException e) {
            System.out.println("Nieprawidłowa lekcja");
            return null;
        }

        if (attendance.isEmpty()) {
            System.out.println("Brak obecności na tej lekcji");
            return null;
        }

        System.out.println("\n=== WYBIERZ OBECNOSC ===");
        for (int i = 0; i < attendance.size(); i++) {
            Obecnosc newAttendance = attendance.get(i);
            System.out.println((i + 1) + ". " + newAttendance);
        }
        System.out.println("0. Anuluj");
        System.out.print("Wybierz numer: ");

        String input = menuManager.getScanner().nextLine();
        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy numer");
            return null;
        }

        if (choice == 0) {
            return null;
        }

        if (choice < 1 || choice > attendance.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return attendance.get(choice - 1);
    }

    private void displayLessons() {
        System.out.println("\n=== LISTA LEKCJI ===");
        List<Lekcja> lessons = dziennik.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("Brak lekcji w systemie.");
        } else {
            for (int i = 0; i < lessons.size(); i++) {
                System.out.println((i + 1) + ". " + lessons.get(i));
            }
        }
        pauseAndReturn();
    }

    private void addLesson() {
        System.out.println("\n=== DODAWANIE LEKCJI ===");
        
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            pauseAndReturn("Brak przedmiotów");
            return;
        }

        System.out.println("Dostępne przedmioty:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.print("Wybierz przedmiot: ");
        
        try {
            int subjectIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
                pauseAndReturn("Nieprawidłowy numer przedmioty");
                return;
            }

            Przedmiot subject = subjects.get(subjectIndex);
            Nauczyciel teacher = subject.getTeachers().isEmpty() ? null : subject.getTeachers().getFirst();

            List<Klasa> classes = dziennik.getGroups();
            if (classes.isEmpty()) {
                pauseAndReturn("Brak klas w systemie");
                return;
            }

            System.out.println("Dostępne klasy:");
            for (int i = 0; i < classes.size(); i++) {
                System.out.println((i + 1) + ". " + classes.get(i));
            }
            System.out.print("Wybierz klasę: ");
            
            int classIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (classIndex < 0 || classIndex >= classes.size()) {
                pauseAndReturn("Nieprawidłowy numer klasy");
                return;
            }

            Klasa klasa = classes.get(classIndex);
            Lekcja lesson = new Lekcja(subject, teacher, klasa, LocalDate.now());
            dziennik.addLesson(lesson);
            
            System.out.println("\nDodano lekcję: " + lesson);
            pauseAndReturn();
        } catch (NumberFormatException e) {
            pauseAndReturn("Nieprawidłowy format liczby");
        }
    }
}

