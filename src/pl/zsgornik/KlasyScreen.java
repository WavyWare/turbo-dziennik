package pl.zsgornik;

import java.util.List;

public class KlasyScreen extends Screen {
    public KlasyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== ZARZĄDZANIE KLASAMI ===");
        System.out.println("\n1. Wyświetl wszystkie klasy");
        System.out.println("2. Dodaj klasę");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayClasses();
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

    private void displayClasses() {
        System.out.println("\n=== LISTA KLAS ===");
        List<Klasa> classes = dziennik.getGroups();
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie.");
        } else {
            for (int i = 0; i < classes.size(); i++) {
                Klasa klasa = classes.get(i);
                System.out.println((i + 1) + ". " + klasa.getClassName() + 
                    " (Wychowawca: " + klasa.getSupervisor().getFullName() + 
                    ", Uczniowie: " + klasa.getStudents().size() + ")");
            }
        }
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}

