package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

public class KlasyScreen extends Screen {
    public KlasyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== KLASY ===");
        System.out.println("\n1. Lista klas");
        System.out.println("2. Dodaj klasę");
        System.out.println("3. Zmień nazwe klasy");
        System.out.println("4. Ustaw przewodniczącego klasy");
        System.out.println("5. Ustaw wychowawce");
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
                System.out.println("\nPodaj nazwe klasy");
                System.out.print("\nNazwa: ");
                String className = menuManager.getScanner().nextLine();
                if (className.isEmpty()) {
                    System.out.println("Nazwa klasy nie może być pusta");
                    throw new IllegalArgumentException("Nazwa klasy nie może być pusta");
                }
                if (dziennik.getGroups().stream().anyMatch(x -> x.getClassName().equals(className))) {
                    System.out.println("Klasa o tej nazwie już istnieje");
                    throw new IllegalArgumentException("Klasa o tej nazwie już istnieje");
                } 
                dziennik.pushGroup(new Klasa(className));
                break;
            case "3":
                Klasa changeNameGroup = selectClass();
                String newClassName = menuManager.getScanner().nextLine();
                if (newClassName.isEmpty()) {
                    System.out.println("Nowa nazwa klasy nie może być pusta");
                    throw new IllegalArgumentException("Nowa nazwa klasy nie może być pusta");
                }
                if (dziennik.getGroups().stream().anyMatch(x -> x.getClassName().equals(newClassName))) {
                    System.out.println("Klasa o tej nazwie już istnieje");
                    throw new IllegalArgumentException("Klasa o tej nazwie już istnieje");
                }
                assert changeNameGroup != null;
                changeNameGroup.setClassName(newClassName);
                System.out.println("\nPomyślnie zmieniono nazwe\nNaciśnij Enter aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
            case "4":
                Klasa leaderGroup = selectClass();
                assert leaderGroup != null;
                Uczen newLeader = chooseStudent(leaderGroup);
                leaderGroup.setClassLeader(newLeader);
                break;
            case "5":
                Klasa supervisorGroup = selectClass();
                Nauczyciel supervisor = chooseSupervisor();
                assert supervisorGroup != null;
                supervisorGroup.setSupervisor(supervisor);
                System.out.println("\nPomyślnie ustawiono wychowawce\nNaciśnij Enter aby kontynuować...");
                menuManager.getScanner().nextLine();
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
                    " (Wychowawca: " + klasa.getSupervisor().fullName() +
                    ", Uczniowie: " + klasa.getStudents().size() + ")");
            }
        }
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    public static Klasa selectClass() {
        List<Klasa> classes = dziennik.getGroups();

        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie");
            return null;
        }

        System.out.println("\n=== WYBIERZ KLASE ===");
        for (int i = 0; i < classes.size(); i++) {
            Klasa group = classes.get(i);
            System.out.println((i + 1) + ". " + group.getClassName() +
                    " (Wychowawca: " + group.getSupervisor().fullName() + ")");
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

        if (choice < 1 || choice > classes.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return classes.get(choice - 1);
    }

    public static Uczen chooseStudent(Klasa group) {
        List<Uczen> students = group.getStudents();

        if (students.isEmpty()) {
            System.out.println("Brak uczniów w klasie");
            return null;
        }

        System.out.println("\n=== WYBIERZ UCZNIA ===");
        for (int i = 0; i < students.size(); i++) {
            Uczen student = students.get(i);
            System.out.println((i + 1) + ". " + student.getFullName());
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

        if (choice < 1 || choice > students.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return students.get(choice - 1);
    }

    private Nauczyciel chooseSupervisor() {
        List<Nauczyciel> teachers = dziennik.getTeachers();

        if (teachers.isEmpty()) {
            System.out.println("Brak nauczycieli w systemie");
            return null;
        }

        System.out.println("\n=== WYBIERZ NAUCZYCIELA ===");
        for (int i = 0; i < teachers.size(); i++) {
            Nauczyciel teacher = teachers.get(i);
            System.out.println((i + 1) + ". " + teacher.fullName() + (teacher.fullName().equals(DziennikLekcyjny.getLoggedAs().fullName()) ? " (ty)" : ""));
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

        if (choice < 1 || choice > teachers.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return teachers.get(choice - 1);
    }

}

