package pl.zsgornik.ui;

import java.util.List;
import java.util.Objects;

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
        System.out.println("5. Wyświetl uwagi ucznia");
        System.out.println("6. Dodaj uwagę dla ucznia");
        System.out.println("7. Edytuj opis uwagi dla ucznia");
        System.out.println("8. Wyświetl raport zachowania");
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
            case "5":
                displayNotes();
                break;
            case "6":
                addNote();
                break;
            case "7":
                editNote();
                break;
            case "8":
                reportBehavior();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }

    private void editNote() {
        Uwaga note = chooseNote();
        System.out.printf("Stary opis: %s", note.getDescription());
        System.out.println("\nPodaj nowy opis uwagi:");
        String newDescription = menuManager.getScanner().nextLine();
        if (newDescription.isEmpty()){
            pauseAndReturn("Opis nie może być pusty");
            return;
        }
        note.setDescription(newDescription);
        pauseAndReturn("Zastosowano zmiany");
    }

    public Uwaga chooseNote() {
        List<Uwaga> notes = Objects.requireNonNull(chooseStudentWithClass()).getBehaviouralNotes();

        if (notes == null || notes.isEmpty()) {
            pauseAndReturn("Uczeń nie ma uwag. Nic do zrobienia.");
            return null;
        }

        System.out.println("\n=== WYBIERZ UWAGĘ ===");
        for (int i = 0; i < notes.size(); i++) {
            Uwaga note = notes.get(i);
            System.out.println((i + 1) + ". " + note);
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

        if (choice < 1 || choice > notes.size()) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return notes.get(choice - 1);
    }

    private void reportBehavior() {
        Uczen student = chooseStudentWithClass();
        assert student != null;
        int negatives = 0;
        int positives = 0;
        for (Uwaga u: student.getBehaviouralNotes()) {
            if (u.isPositive()) {
                positives++;
            } else {
                negatives++;
            }
        }
        System.out.println("\nRaport zachowania:");
        System.out.printf("Pochwał: %d\n", positives);
        System.out.printf("Uwag: %d\n", negatives);
        System.out.println("\nRóżnica: " + (positives-negatives));
        pauseAndReturn();
    }

    private void addNote() {
        Uczen student = chooseStudentWithClass();
        assert student != null;
        System.out.println("\nPodaj treść uwagi:");
        String description = menuManager.getScanner().nextLine();
        if (description.isEmpty()) {
            pauseAndReturn("Opis nie może być pusty");
            return;
        }
        System.out.println("Czy uwaga jest negatywna (domyślnie: tak): tak/nie");
        boolean isPositive = menuManager.getScanner().nextLine().equalsIgnoreCase("nie");
        Uwaga newNote = new Uwaga(isPositive, description);
        student.pushNote(newNote);
        pauseAndReturn();
    }

    private void displayNotes() {
        Uczen student = chooseStudentWithClass();
        System.out.println("\nUwagi dla ucznia: "+student);
        assert student != null;
        List<Uwaga> behaviouralNotes = student.getBehaviouralNotes();
        for (int i = 0; i < behaviouralNotes.size(); i++) {
            System.out.println((i + 1)+ ". " + behaviouralNotes.get(i));
        }
        pauseAndReturn();
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
        System.out.println("\n=== DODAWANIE UCZNIA ===");
        Klasa chosenClass = KlasyScreen.selectClass();
        if (chosenClass == null) {
            return;
        }

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
        Klasa chosenClass = KlasyScreen.selectClass();
        if (chosenClass == null) {
            return null;
        }
        return KlasyScreen.chooseStudent(chosenClass);
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


