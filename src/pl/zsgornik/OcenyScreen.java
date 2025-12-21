package pl.zsgornik;

import java.util.List;

public class OcenyScreen extends Screen {
    public OcenyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== ZARZĄDZANIE OCENAMI ===");
        System.out.println("\n1. Wyświetl wszystkie oceny");
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
                System.out.println("\nFunkcja w trakcie implementacji...");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
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

    private void displayGrades() {
        System.out.println("\n=== LISTA OCEN ===");
        List<Ocena> grades = dziennik.getGrades();
        if (grades.isEmpty()) {
            System.out.println("Brak ocen w systemie.");
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

