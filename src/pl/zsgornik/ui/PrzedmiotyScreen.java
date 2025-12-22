package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.enums.TypPrzedmiotu;
import pl.zsgornik.service.DziennikLekcyjny;

public class PrzedmiotyScreen extends Screen {
    public PrzedmiotyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== PRZEDMIOTY ===");
        System.out.println("\n1. Lista przedmiotów");
        System.out.println("2. Dodaj przedmiot");
        System.out.println("3. Usuń przedmiot");
        System.out.println("4. Zmień typ przedmiotu");
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
                addSubject();
                break;
            case "3":
                removeSubject();
                break;
            case "4":
                editSubject();
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

    private void addSubject() {
                System.out.println("\n=== DODAJ PRZEDMIOT ===");
                TypPrzedmiotu[] types = TypPrzedmiotu.values();
                System.out.println("Wybierz typ przedmiotu:");
                for (int i = 0; i < types.length; i++) {
                    System.out.println((i + 1) + ". " + types[i].getName());
                }

                int selectedTypeIdx = -1;
                while (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                    System.out.print("Podaj numer typu przedmiotu: ");
                    try {
                        String line = menuManager.getScanner().nextLine().trim();
                        selectedTypeIdx = Integer.parseInt(line) - 1;
                    } catch (NumberFormatException e) {
                        selectedTypeIdx = -1;
                    }
                    if (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                        System.out.println("Nieprawidłowy numer typu przedmiotu.");
                    }
                }

                TypPrzedmiotu wybranyTyp = types[selectedTypeIdx];

                Przedmiot nowyPrzedmiot = new Przedmiot(wybranyTyp);
                dziennik.pushSubject(nowyPrzedmiot);

                System.out.println("\nDodano przedmiot: " + wybranyTyp.getName());
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
    }

    private void removeSubject() {
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("\nBrak przedmiotów do usunięcia.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("\n=== USUWANIE PRZEDMIOTU ===");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Podaj numer przedmiotu do usunięcia: ");
        try {
            int idx = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (idx < 0 || idx >= subjects.size()) {
                System.out.println("Nieprawidłowy numer przedmiotu.");
            } else {
                Przedmiot removed = subjects.remove(idx);
                System.out.println("Usunięto przedmiot: " + removed);
            }
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
        }

        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void editSubject() {
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("\nBrak przedmiotów do modyfikacji.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("\n=== MODYFIKACJA PRZEDMIOTU ===");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Podaj numer przedmiotu do zmiany: ");
        int idx;
        try {
            idx = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        if (idx < 0 || idx >= subjects.size()) {
            System.out.println("Nieprawidłowy numer przedmiotu.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        Przedmiot subject = subjects.get(idx);

        System.out.println("\nAktualny typ: " + subject.getType().getName());
        TypPrzedmiotu[] types = TypPrzedmiotu.values();
        System.out.println("Wybierz nowy typ:");
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getName());
        }

        int selectedTypeIdx = -1;
        while (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
            System.out.print("Podaj numer typu przedmiotu: ");
            try {
                String line = menuManager.getScanner().nextLine().trim();
                selectedTypeIdx = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                selectedTypeIdx = -1;
            }
            if (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                System.out.println("Nieprawidłowy numer typu przedmiotu.");
            }
        }

        subject.setType(types[selectedTypeIdx]);
        System.out.println("\nZmieniono typ przedmiotu na: " + subject.getType().getName());
        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
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

