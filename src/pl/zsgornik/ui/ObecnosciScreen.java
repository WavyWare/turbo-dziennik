package pl.zsgornik.ui;

import java.util.ArrayList;
import java.util.List;
import pl.zsgornik.enums.StatusObecnosci;
import pl.zsgornik.model.Klasa;
import pl.zsgornik.model.Lekcja;
import pl.zsgornik.model.Obecnosc;
import pl.zsgornik.model.Uczen;
import pl.zsgornik.model.Uwaga;
import pl.zsgornik.service.DziennikLekcyjny;

public class ObecnosciScreen extends Screen {
    public ObecnosciScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\nturbo dziennik - OBECNOŚCI");
        System.out.println("\n1. Wyświetl obecności");
        System.out.println("2. Zarejestruj obecność");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayAttendance();
                break;
            case "2":
                registerAttendance();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                System.out.println("Nieprawidłowa opcja");
                break;
        }
    }

    private void displayAttendance() {
        System.out.println("\nturbo dziennik - OBECNOŚCI");

        Lekcja lesson = selectionHelper.selectLesson();
        if (lesson == null) {
            System.out.println("Nieprawidłowa lekcja");
            return;
        }

        System.out.println("\nObecności na lekcji: " + lesson);
        List<Obecnosc> attendances = lesson.getAttendances();

        if (attendances.isEmpty()) {
            System.out.println("Brak zarejestrowanych obecności.");
        } else {
            for (Obecnosc attendance : attendances) {
                System.out.println("  " + attendance);
            }
        }
    }

    private void registerAttendance () {
        System.out.println("\nturbo dziennik - REJESTROWANIE OBECNOŚCI");

        Lekcja lesson = selectionHelper.selectLesson();
        if (lesson == null) {
            System.out.println("Nieprawidłowa lekcja");
            return;
        }

        Uczen student = selectionHelper.chooseStudent(lesson.getGroup());
        if (student == null) {
            System.out.println("Nieprawidłowy uczeń");
            return;
        }

        StatusObecnosci status = StatusObecnosci.chooseType();
        if (status == null) {
            System.out.println("Nieprawidłowy status");
            return;
        }

        try {
            lesson.registerAttendance(student, status);
            System.out.println("\nZarejestrowano obecność: " + student.getFullName() + " - " + status.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println("\nBłąd: " + e.getMessage());
        }

        punishStudentForUnexcusedHours(dziennik, student);
    }

    public static void punishStudentForUnexcusedHours(DziennikLekcyjny dziennik, Uczen student) {
        Klasa group = dziennik.getGroups().stream()
                .filter(x -> x.getStudents().contains(student))
                .findFirst()
                .orElseThrow();

        List<Lekcja> lessonsOfGroup = dziennik.getLessons().stream()
                .filter(x -> x.getGroup().equals(group))
                .toList();

        List<Obecnosc> attendances = new ArrayList<>();

        for (Lekcja l: lessonsOfGroup) {
            for (Obecnosc o: l.getAttendances()) {
                if (o.getStudent().equals(student)) {
                    attendances.add(o);
                }
            }
        }

        int unexcusedHoursCounter = 0;
        for (Obecnosc o: attendances) {
            if (o.getStatus()==StatusObecnosci.NIEOBECNY) {
                unexcusedHoursCounter++;
            }
        }

        if (unexcusedHoursCounter >= 3) {
            student.pushNote(new Uwaga(false, "Nieusprawiedliwione nieobecności: " + unexcusedHoursCounter));
        }
    }
}

