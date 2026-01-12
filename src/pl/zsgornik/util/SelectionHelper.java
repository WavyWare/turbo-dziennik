package pl.zsgornik.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import pl.zsgornik.model.*;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.service.DziennikLekcyjny.getLoggedAs;

public class SelectionHelper {
    private final DziennikLekcyjny dziennik;
    private final BufferedReader reader;

    public SelectionHelper(DziennikLekcyjny dziennik, BufferedReader reader) {
        this.dziennik = dziennik;
        this.reader = reader;
    }

    private <T> T selectFromList(List<T> items, String title, String emptyMessage, Function<T, String> formatter) {
        if (items.isEmpty()) {
            System.out.println(emptyMessage);
            return null;
        }

        System.out.println("\nturbo dziennik - " + title);
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + formatter.apply(items.get(i)));
        }
        System.out.println("0. Anuluj");
        System.out.print("Wybierz numer: ");

        try {
            int choice = Integer.parseInt(reader.readLine().trim());
            if (choice == 0) {
                return null;
            }
            if (choice < 1 || choice > items.size()) {
                System.out.println("Numer poza zakresem");
                return null;
            }
            return items.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy numer");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Klasa selectClass() {
        return selectFromList(
            dziennik.getGroups(),
            "WYBIERZ KLASĘ",
            "Brak klas w systemie",
            group -> group.getClassName() + " (Wychowawca: " + group.getSupervisor().fullName() + ")"
        );
    }

    public Uczen chooseStudent(Klasa group) {
        if (group == null) {
            return null;
        }
        return selectFromList(
            group.getStudents(),
            "WYBIERZ UCZNIA",
            "Brak uczniów w klasie",
            Uczen::getFullName
        );
    }

    public Uczen chooseStudentWithClass() {
        Klasa chosenClass = selectClass();
        return chooseStudent(chosenClass);
    }

    public Nauczyciel chooseTeacher() {
        return selectFromList(
            dziennik.getTeachers(),
            "WYBIERZ NAUCZYCIELA",
            "Brak nauczycieli w systemie",
            teacher -> {
                Nauczyciel logged = getLoggedAs();
                return teacher.fullName() + (logged != null && teacher.fullName().equals(logged.fullName()) ? " (ty)" : "");
            }
        );
    }

    public Lekcja selectLesson() {
        return selectFromList(
            dziennik.getLessons(),
            "WYBIERZ LEKCJE",
            "Brak lekcji w systemie",
            Lekcja::toString
        );
    }

    public Obecnosc selectAttendance(Lekcja lesson) {
        if (lesson == null) {
            return null;
        }
        try {
            List<Obecnosc> attendances = lesson.getAttendances();
            return selectFromList(
                attendances,
                "WYBIERZ OBECNOŚĆ",
                "Brak obecności na tej lekcji",
                Obecnosc::toString
            );
        } catch (NullPointerException e) {
            System.out.println("Nieprawidłowa lekcja");
            return null;
        }
    }

    public Ocena selectGrade() {
        return selectFromList(
            dziennik.getGrades(),
            "WYBIERZ OCENĘ",
            "Brak ocen w systemie",
            Ocena::toString
        );
    }

    public Uwaga selectNote(Uczen student) {
        if (student == null) {
            return null;
        }
        List<Uwaga> notes = student.getBehaviouralNotes();
        return selectFromList(
            notes,
            "WYBIERZ UWAGĘ",
            "Uczeń nie ma uwag",
            Uwaga::toString
        );
    }

    public Przedmiot selectSubject() {
        return selectFromList(
            dziennik.getSubjects(),
            "WYBIERZ PRZEDMIOT",
            "Brak przedmiotów w systemie",
            Przedmiot::toString
        );
    }
}

