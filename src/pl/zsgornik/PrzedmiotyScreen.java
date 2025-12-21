package pl.zsgornik;

import java.util.List;

public class PrzedmiotyScreen extends Screen {
    public PrzedmiotyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== PRZEDMIOTY ===");
        System.out.println("\n1. Lista przedmiotów");
        System.out.println("2. Dodaj przedmiot");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displaySubjects();
                break;
            case "2":
                // do zrobienia
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

    private void displaySubjects() {
        System.out.println("\n=== LISTA PRZEDMIOTÓW ===");
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("Brak przedmiotów w systemie");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                Przedmiot subject = subjects.get(i);
                System.out.print((i + 1) + ". " + subject.getType().getName());
                if (!subject.getTeachers().isEmpty()) {
                    System.out.print(" (Nauczyciele: ");
                    for (int j = 0; j < subject.getTeachers().size(); j++) {
                        System.out.print(subject.getTeachers().get(j).getFullName());
                        if (j < subject.getTeachers().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.print(")");
                }
                System.out.println();
            }
        }
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}

