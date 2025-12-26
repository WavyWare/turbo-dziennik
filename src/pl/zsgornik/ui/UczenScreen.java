package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

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
        System.out.println("4. Raport średnich ocen ucznia");
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
                pauseAndReturn("Nieprawidłowa opcja");
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
        pauseAndReturn();
    }

    private void addStudent() {
        List<Klasa> classes = dziennik.getGroups();
        System.out.println("\n=== DODAWANIE UCZNIA ===");
        if (classes.isEmpty()) {
            pauseAndReturn("Brak klas w systemie. Najpierw dodaj klasę.");
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
            pauseAndReturn("Nieprawidłowy format liczby.");
            return;
        }

        if (classIndex < 0 || classIndex >= classes.size()) {
            pauseAndReturn("Nieprawidłowy numer klasy.");
            return;
        }

        Klasa chosenClass = classes.get(classIndex);
        System.out.print("Podaj imię i nazwisko ucznia: ");
        String fullName = menuManager.getScanner().nextLine().trim();
        if (fullName.isEmpty()) {
            pauseAndReturn("Imię i nazwisko nie może być puste.");
            return;
        }

        Uczen newStudent = new Uczen(fullName);
        chosenClass.addStudent(newStudent);

        pauseAndReturn("\nDodano ucznia " + fullName + " do klasy " + chosenClass.getClassName());
    }

    private Uczen chooseStudentWithClass() {
        List<Klasa> classes = dziennik.getGroups();
        if (classes.isEmpty()) {
            pauseAndReturn("Brak klas w systemie.");
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
            pauseAndReturn("Nieprawidłowy format liczby.");
            return null;
        }

        if (classIndex < 0 || classIndex >= classes.size()) {
            pauseAndReturn("Nieprawidłowy numer klasy.");
            return null;
        }

        Klasa chosenClass = classes.get(classIndex);
        List<Uczen> students = chosenClass.getStudents();
        if (students.isEmpty()) {
            pauseAndReturn("Brak uczniów w wybranej klasie.");
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
            pauseAndReturn("Nieprawidłowy format liczby.");
            return null;
        }

        if (studentIndex < 0 || studentIndex >= students.size()) {
            pauseAndReturn("Nieprawidłowy numer ucznia.");
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
        pauseAndReturn();
    }

    private void reportGrades() {
        dziennik.printStudentReport(chooseStudentWithClass());
        pauseAndReturn();
    }
}


