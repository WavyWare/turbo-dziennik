package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.enums.StatusObecnosci;
import pl.zsgornik.service.DziennikLekcyjny;

public class ObecnosciScreen extends Screen {
    public ObecnosciScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== OBECNOŚCI ===");
        System.out.println("\n1. Wyświetl obecności");
        System.out.println("2. Zarejestruj obecność");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayAttendance();
                break;
            case "2":
                registerAttendance();
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

    private void displayAttendance() {
        System.out.println("\n=== OBECNOŚCI ===");

        Lekcja lesson = LekcjeScreen.selectLesson();
        if (lesson == null) {
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("\nObecności na lekcji: " + lesson);
        List<Obecnosc> attendances = lesson.getAttendances();

        if (attendances.isEmpty()) {
            System.out.println("Brak zarejestrowanych obecności.");
        } else {
            for (Obecnosc attendance : attendances) {
                System.out.println("  " + attendance);
            }
        }

        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void registerAttendance () {
        System.out.println("\n=== REJESTROWANIE OBECNOŚCI ===");

        Lekcja lesson = LekcjeScreen.selectLesson();
        if (lesson == null) {
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        Uczen student = KlasyScreen.chooseStudent(lesson.getGroup());
        if (student == null) {
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        StatusObecnosci status = StatusObecnosci.chooseType();
        if (status == null) {
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        try {
            lesson.registerAttendance(student, status);
            System.out.println("\nZarejestrowano obecność: " + student.getFullName() + " - " + status.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println("\nBłąd: " + e.getMessage());
        }

        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}

