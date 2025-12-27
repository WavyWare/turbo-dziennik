package pl.zsgornik.ui;

import java.time.LocalDate;
import java.util.List;
import pl.zsgornik.enums.StatusObecnosci;
import pl.zsgornik.model.Klasa;
import pl.zsgornik.model.Lekcja;
import pl.zsgornik.model.Nauczyciel;
import pl.zsgornik.model.Obecnosc;
import pl.zsgornik.model.Przedmiot;
import pl.zsgornik.model.Uczen;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class LekcjeScreen extends Screen {
    public LekcjeScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\nturbo dziennik - LEKCJE");
        System.out.println("\n1. Lista lekcji");
        System.out.println("2. Dodaj lekcję");
        System.out.println("3. Zarejestruj obecności");
        System.out.println("4. Zmień obecności");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayLessons();
                break;
            case "2":
                addLesson();
                break;
            case "3":
                registerClassAttendance();
                break;
            case "4":
                Lekcja attendanceLesson = selectionHelper.selectLesson();
                if (attendanceLesson == null) {
                    break;
                }
                Obecnosc attendance = selectionHelper.selectAttendance(attendanceLesson);
                if (attendance == null) {
                    break;
                }
                StatusObecnosci newStatus = StatusObecnosci.chooseType();
                if (newStatus != null) {
                    attendance.setStatus(newStatus);
                    ObecnosciScreen.punishStudentForUnexcusedHours(dziennik, attendance.getStudent());
                }
                pauseAndReturn();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }

    private void registerClassAttendance() {
        Lekcja lesson = selectionHelper.selectLesson();
        if (lesson == null) {
            pauseAndReturn("Nieprawidłowa opcja");
            return;
        }
        List<Uczen> students = lesson.getGroup().getStudents();

        for (Uczen student : students) {
            System.out.printf("\nTyp obecności dla %s: ", student);
            StatusObecnosci type = StatusObecnosci.chooseType();
            if (type == null) {
                pauseAndReturn("Nieprawidłowa opcja");
                break;
            }
            lesson.registerAttendance(student, type);
        }
        System.out.println("\nDodano obecności!");
        pauseAndReturn();
    }

    private void displayLessons() {
        System.out.println("\nturbo dziennik - LISTA LEKCJI");
        List<Lekcja> lessons = dziennik.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("Brak lekcji w systemie.");
        } else {
            for (int i = 0; i < lessons.size(); i++) {
                System.out.println((i + 1) + ". " + lessons.get(i));
            }
        }
        pauseAndReturn();
    }

    private void addLesson() {
        System.out.println("\nturbo dziennik - DODAWANIE LEKCJI");
        
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            pauseAndReturn("Brak przedmiotów");
            return;
        }

        System.out.println("Dostępne przedmioty:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.print("Wybierz przedmiot: ");
        
        try {
            int subjectIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
                pauseAndReturn("Nieprawidłowy numer przedmioty");
                return;
            }

            Przedmiot subject = subjects.get(subjectIndex);
            Nauczyciel teacher = subject.getTeachers().isEmpty() ? null : subject.getTeachers().getFirst();

            Klasa klasa = selectionHelper.selectClass();
            if (klasa == null) {
                return;
            }
            Lekcja lesson = new Lekcja(subject, teacher, klasa, LocalDate.now());
            dziennik.addLesson(lesson);
            
            System.out.println("\nDodano lekcję: " + lesson);
            pauseAndReturn();
        } catch (NumberFormatException e) {
            pauseAndReturn("Nieprawidłowy format liczby");
        }
    }
}

