package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

public class UczenScreen extends Screen {
    public UczenScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== UCZNIOWIE ===");
        System.out.println("\n1. Lista uczniów (według klas)");
        System.out.println("2. Dodaj ucznia do klasy");
        System.out.println("3. Raport frekwencji ucznia");
        System.out.println("4. Raport średniej ocen ucznia");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                listStudents();
                break;
            case "2":
                addStudent();
                break;
            case "3":
                reportPresence();
                break;
            case "4":
                reportGrades();
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

    private void listStudents() {
        List<Klasa> classes = dziennik.getGroups();
        System.out.println("\n=== LISTA UCZNIÓW ===");
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie.");
        } else {
            for (Klasa klasa : classes) {
                System.out.println("\nKlasa: " + klasa.getClassName());
                List<Uczen> students = klasa.getStudents();
                if (students.isEmpty()) {
                    System.out.println("  (brak uczniów)");
                } else {
                    for (int i = 0; i < students.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + students.get(i).getFullName());
                    }
                }
            }
        }
        System.out.println("\nNaciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void addStudent() {
        List<Klasa> classes = dziennik.getGroups();
        System.out.println("\n=== DODAWANIE UCZNIA ===");
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie. Najpierw dodaj klasę.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("Dostępne klasy:");
        for (int i = 0; i < classes.size(); i++) {
            System.out.println((i + 1) + ". " + classes.get(i).getClassName());
        }
        System.out.print("Wybierz klasę: ");

        int classIndex;
        try {
            classIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        if (classIndex < 0 || classIndex >= classes.size()) {
            System.out.println("Nieprawidłowy numer klasy.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        Klasa chosenClass = classes.get(classIndex);
        System.out.print("Podaj imię i nazwisko ucznia: ");
        String fullName = menuManager.getScanner().nextLine().trim();
        if (fullName.isEmpty()) {
            System.out.println("Imię i nazwisko nie może być puste.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        Uczen newStudent = new Uczen(fullName);
        chosenClass.addStudent(newStudent);

        System.out.println("\nDodano ucznia " + fullName + " do klasy " + chosenClass.getClassName());
        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private Uczen chooseStudentWithClass() {
        List<Klasa> classes = dziennik.getGroups();
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        System.out.println("Dostępne klasy:");
        for (int i = 0; i < classes.size(); i++) {
            System.out.println((i + 1) + ". " + classes.get(i).getClassName());
        }
        System.out.print("Wybierz klasę: ");

        int classIndex;
        try {
            classIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        if (classIndex < 0 || classIndex >= classes.size()) {
            System.out.println("Nieprawidłowy numer klasy.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        Klasa chosenClass = classes.get(classIndex);
        List<Uczen> students = chosenClass.getStudents();
        if (students.isEmpty()) {
            System.out.println("Brak uczniów w wybranej klasie.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        System.out.println("\nUczniowie w klasie " + chosenClass.getClassName() + ":");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        }
        System.out.print("Wybierz ucznia: ");

        int studentIndex;
        try {
            studentIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Nieprawidłowy numer ucznia.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return null;
        }

        return students.get(studentIndex);
    }

    private void reportPresence() {
        System.out.println("\n=== RAPORT FREKWENCJI UCZNIA ===");
        Uczen student = chooseStudentWithClass();
        if (student == null) {
            return;
        }

        double percent = dziennik.reportPresencePercent(student);
        if (percent < 0) {
            System.out.println("Brak danych o frekwencji dla tego ucznia.");
        } else {
            System.out.printf("Frekwencja ucznia %s: %.2f%%%n", student.getFullName(), percent);
        }
        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void reportGrades() {
        System.out.println("\n=== RAPORT ŚREDNIEJ OCEN UCZNIA ===");
        Uczen student = chooseStudentWithClass();
        if (student == null) {
            return;
        }

        double avg = dziennik.reportAverageGrade(student);
        if (avg < 0) {
            System.out.println("Brak ocen dla tego ucznia.");
        } else {
            System.out.printf("Średnia ocen ucznia %s: %.2f%n", student.getFullName(), avg);
        }
        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}


