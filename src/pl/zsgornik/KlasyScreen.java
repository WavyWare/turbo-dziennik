package pl.zsgornik;

import java.util.List;

public class KlasyScreen extends Screen {
    public KlasyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== KLASY ===");
        System.out.println("\n1. Lista klas");
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
                String className = menuManager.getScanner().nextLine();
                if (className.isEmpty()) {
                    System.out.println("Nazwa klasy nie może być pusta");
                    throw new IllegalArgumentException("Nazwa klasy nie może być pusta");
                }
                if (dziennik.getGroups().stream().anyMatch(x -> x.getClassName().equals(className))) {
                    System.out.println("Klasa o tej nazwie już istnieje");
                    throw new IllegalArgumentException("Klasa o tej nazwie już istnieje");
                }
                dziennik.pushGroup(new Klasa(className, DziennikLekcyjny.getLoggedAs()));
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

    private void displayClasses() {
        System.out.println("\n=== LISTA KLAS ===");
        List<Klasa> classes = dziennik.getGroups();
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie");
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

