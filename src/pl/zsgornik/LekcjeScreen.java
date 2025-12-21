package pl.zsgornik;

import java.time.LocalDate;
import java.util.List;

public class LekcjeScreen extends Screen {
    public LekcjeScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== ZARZĄDZANIE LEKCJAMI ===");
        System.out.println("\n1. Wyświetl wszystkie lekcje");
        System.out.println("2. Dodaj nową lekcję");
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
            case "0":
                menuManager.popScreen();
                break;
            default:
                System.out.println("\n✗ Nieprawidłowa opcja! Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
        }
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
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void addLesson() {
        System.out.println("\n=== DODAWANIE NOWEJ LEKCJI ===");
        
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("Brak przedmiotów w systemie. Najpierw dodaj przedmiot.");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("Dostępne przedmioty:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.print("Wybierz przedmiot (numer): ");
        
        try {
            int subjectIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
                System.out.println("✗ Nieprawidłowy numer przedmiotu!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            Przedmiot subject = subjects.get(subjectIndex);
            Nauczyciel teacher = subject.getTeachers().isEmpty() ? null : subject.getTeachers().get(0);

            List<Klasa> classes = dziennik.getGroups();
            if (classes.isEmpty()) {
                System.out.println("Brak klas w systemie. Najpierw dodaj klasę.");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            System.out.println("Dostępne klasy:");
            for (int i = 0; i < classes.size(); i++) {
                System.out.println((i + 1) + ". " + classes.get(i));
            }
            System.out.print("Wybierz klasę (numer): ");
            
            int classIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (classIndex < 0 || classIndex >= classes.size()) {
                System.out.println("✗ Nieprawidłowy numer klasy!");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                return;
            }

            Klasa klasa = classes.get(classIndex);
            Lekcja lesson = new Lekcja(subject, teacher, klasa, LocalDate.now());
            dziennik.addLesson(lesson);
            
            System.out.println("\n✓ Dodano lekcję: " + lesson);
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
        } catch (NumberFormatException e) {
            System.out.println("✗ Nieprawidłowy format liczby!");
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
        }
    }
}

