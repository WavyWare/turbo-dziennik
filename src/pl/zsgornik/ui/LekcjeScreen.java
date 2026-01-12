package pl.zsgornik.ui;

import java.io.IOException;
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
                    System.out.println("Nieprawidłowa lekcja");
                    break;
                }
                Obecnosc attendance = selectionHelper.selectAttendance(attendanceLesson);
                if (attendance == null) {
                    System.out.println("Nieprawidłowa obecność");
                    break;
                }
                StatusObecnosci newStatus = StatusObecnosci.chooseType();
                if (newStatus != null) {
                    attendance.setStatus(newStatus);
                    ObecnosciScreen.punishStudentForUnexcusedHours(dziennik, attendance.getStudent());
                }
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                System.out.println("Nieprawidłowa opcja");
                break;
        }
    }

    private void registerClassAttendance() {
        Lekcja lesson = selectionHelper.selectLesson();
        if (lesson == null) {
            System.out.println("Nieprawidłowa lekcja");
            return;
        }
        List<Uczen> students = lesson.getGroup().getStudents();

        for (Uczen student : students) {
            System.out.printf("\nTyp obecności dla %s: ", student);
            StatusObecnosci type = StatusObecnosci.chooseType();
            if (type == null) {
                System.out.println("Nieprawidłowy typ");
                break;
            }
            lesson.registerAttendance(student, type);
        }
        System.out.println("\nDodano obecności!");
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
    }

    private void addLesson() {
        System.out.println("\nturbo dziennik - DODAWANIE LEKCJI");
        
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("Brak przedmiotów");
            return;
        }

        System.out.println("Dostępne przedmioty:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.print("Wybierz przedmiot: ");
        
        try {
            int subjectIndex = Integer.parseInt(menuManager.getConsole().readLine().trim()) - 1;
            if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
                System.out.println("Nieprawidłowy numer przedmioty");
                return;
            }

            Przedmiot subject = subjects.get(subjectIndex);
            Nauczyciel teacher = DziennikLekcyjny.getLoggedAs();

            Klasa klasa = selectionHelper.selectClass();
            if (klasa == null) {
                System.out.println("Nieprawidłowa klasa");
                return;
            }
            Lekcja lesson = new Lekcja(subject, teacher, klasa, LocalDate.now());
            dziennik.addLesson(lesson);
            
            System.out.println("\nDodano lekcję: " + lesson);
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

