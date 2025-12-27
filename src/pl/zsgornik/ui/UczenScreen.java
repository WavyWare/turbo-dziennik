package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.model.Klasa;
import pl.zsgornik.model.Uczen;
import pl.zsgornik.model.Uwaga;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class UczenScreen extends Screen {
    public UczenScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\nturbo dziennik - UCZNIOWIE");
        System.out.println("\n1. Lista uczniów (według klas)");
        System.out.println("2. Dodaj ucznia do klasy");
        System.out.println("3. Usuń ucznia z klasy");
        System.out.println("4. Raport frekwencji ucznia");
        System.out.println("5. Raport średnich ocen ucznia");
        System.out.println("6. Wyświetl uwagi ucznia");
        System.out.println("7. Dodaj uwagę dla ucznia");
        System.out.println("8. Edytuj opis uwagi dla ucznia");
        System.out.println("9. Wyświetl raport zachowania");
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
                removeStudent();
                break;
            case "4":
                reportPresence();
                break;
            case "5":
                reportGrades();
                break;
            case "6":
                displayNotes();
                break;
            case "7":
                addNote();
                break;
            case "8":
                editNote();
                break;
            case "9":
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
        assert note != null;
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

    private Uwaga chooseNote() {
        Uczen student = selectionHelper.chooseStudentWithClass();
        if (student == null) {
            return null;
        }
        return selectionHelper.selectNote(student);
    }

    private void reportBehavior() {
        Uczen student = selectionHelper.chooseStudentWithClass();
        if (student == null) {
            return;
        }
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
        Uczen student = selectionHelper.chooseStudentWithClass();
        if (student == null) {
            return;
        }
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
        Uczen student = selectionHelper.chooseStudentWithClass();
        if (student == null) {
            return;
        }
        System.out.println("\nUwagi dla ucznia: " + student);
        List<Uwaga> behaviouralNotes = student.getBehaviouralNotes();
        for (int i = 0; i < behaviouralNotes.size(); i++) {
            System.out.println((i + 1)+ ". " + behaviouralNotes.get(i));
        }
        pauseAndReturn();
    }

    private void listStudents() {
        List<Klasa> classes = dziennik.getGroups();
        System.out.println("\nturbo dziennik - LISTA UCZNIÓW");
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
        System.out.println("\nturbo dziennik - DODAWANIE UCZNIA");
        Klasa chosenClass = selectionHelper.selectClass();
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

    private void removeStudent() {
        System.out.println("\nturbo dziennik - USUWANIE UCZNIA Z KLASY");
        Klasa chosenClass = selectionHelper.selectClass();
        if (chosenClass == null) {
            return;
        }

        Uczen student = selectionHelper.chooseStudent(chosenClass);
        if (student == null) {
            return;
        }

        if (chosenClass.removeStudent(student)) {
            pauseAndReturn("\nUsunięto ucznia " + student.getFullName() + " z klasy " + chosenClass.getClassName());
        } else {
            pauseAndReturn("\nNie udało się usunąć ucznia z klasy.");
        }
    }

    private void reportPresence() {
        System.out.println("\nturbo dziennik - RAPORT FREKWENCJI UCZNIA");
        Uczen student = selectionHelper.chooseStudentWithClass();
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
        Uczen student = selectionHelper.chooseStudentWithClass();
        if (student == null) {
            return;
        }
        dziennik.printStudentReport(student);
        pauseAndReturn();
    }
}


