package pl.zsgornik;

import java.util.List;

public class ObecnosciScreen extends Screen {
    public ObecnosciScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== ZARZĄDZANIE OBECNOŚCIAMI ===");
        System.out.println("\n1. Wyświetl obecności dla lekcji");
        System.out.println("2. Zarejestruj obecności");
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
                System.out.println("\n✗ Nieprawidłowa opcja! Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
        }
    }

    private void displayAttendance() {
        System.out.println("\n=== OBECNOŚCI ===");
        List<Lekcja> lessons = dziennik.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("Brak lekcji w systemie.");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("Dostępne lekcje:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i));
        }
        System.out.print("Wybierz lekcję (numer): ");

        try {
            int lessonIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (lessonIndex < 0 || lessonIndex >= lessons.size()) {
                System.out.println("✗ Nieprawidłowy numer lekcji!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            Lekcja lesson = lessons.get(lessonIndex);
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
        } catch (NumberFormatException e) {
            System.out.println("✗ Nieprawidłowy format liczby!");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
        }
    }

    private void registerAttendance() {
        System.out.println("\n=== REJESTROWANIE OBECNOŚCI ===");
        List<Lekcja> lessons = dziennik.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("Brak lekcji w systemie.");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("Dostępne lekcje:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i));
        }
        System.out.print("Wybierz lekcję (numer): ");

        try {
            int lessonIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (lessonIndex < 0 || lessonIndex >= lessons.size()) {
                System.out.println("✗ Nieprawidłowy numer lekcji!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            Lekcja lesson = lessons.get(lessonIndex);
            List<Uczen> students = lesson.getGroup().getStudents();
            
            if (students.isEmpty()) {
                System.out.println("Brak uczniów w klasie.");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            System.out.println("\nUczniowie w klasie:");
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i).getFullName());
            }
            System.out.print("Wybierz ucznia (numer): ");
            
            int studentIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (studentIndex < 0 || studentIndex >= students.size()) {
                System.out.println("✗ Nieprawidłowy numer ucznia!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            System.out.println("\nStatusy obecności:");
            StatusObecnosci[] statuses = StatusObecnosci.values();
            for (int i = 0; i < statuses.length; i++) {
                System.out.println((i + 1) + ". " + statuses[i].getFullName());
            }
            System.out.print("Wybierz status (numer): ");
            
            int statusIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (statusIndex < 0 || statusIndex >= statuses.length) {
                System.out.println("✗ Nieprawidłowy numer statusu!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            Uczen student = students.get(studentIndex);
            StatusObecnosci status = statuses[statusIndex];
            
            try {
                lesson.registerAttendance(student, status);
                System.out.println("\n✓ Zarejestrowano obecność: " + student.getFullName() + " - " + status.getFullName());
            } catch (IllegalArgumentException e) {
                System.out.println("\n✗ Błąd: " + e.getMessage());
            }
            
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
        } catch (NumberFormatException e) {
            System.out.println("✗ Nieprawidłowy format liczby!");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
        }
    }
}

