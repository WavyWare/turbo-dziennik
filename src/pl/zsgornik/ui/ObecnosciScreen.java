package pl.zsgornik.ui;

import java.util.ArrayList;
import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.enums.StatusObecnosci;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class ObecnosciScreen extends Screen {
    public ObecnosciScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== OBECNOŚCI ===");
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
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }

    private void displayAttendance() {
        System.out.println("\n=== OBECNOŚCI ===");

        Lekcja lesson = LekcjeScreen.selectLesson();
        if (lesson == null) {
            pauseAndReturn();
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

        pauseAndReturn();
    }

    private void registerAttendance () {
        System.out.println("\n=== REJESTROWANIE OBECNOŚCI ===");

        Lekcja lesson = LekcjeScreen.selectLesson();
        if (lesson == null) {
            pauseAndReturn();
            return;
        }

        Uczen student = KlasyScreen.chooseStudent(lesson.getGroup());
        if (student == null) {
            pauseAndReturn();
            return;
        }

        StatusObecnosci status = StatusObecnosci.chooseType();
        if (status == null) {
            pauseAndReturn();
            return;
        }

        try {
            lesson.registerAttendance(student, status);
            System.out.println("\nZarejestrowano obecność: " + student.getFullName() + " - " + status.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println("\nBłąd: " + e.getMessage());
        }

        punishStudentForUnexcusedHours(dziennik, student);
        pauseAndReturn();
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

