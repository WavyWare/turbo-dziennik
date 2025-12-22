package pl.zsgornik;

import java.util.List;

public class OcenyScreen extends Screen {
    public OcenyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== OCENY ===");
        System.out.println("\n1. Lista ocen");
        System.out.println("2. Dodaj ocenę");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displayGrades();
                break;
            case "2":
                addGrade();
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

    private void addGrade() {
        System.out.println("\n=== DODAWANIE OCENY ===");

        
        Lekcja lastTeacherLesson = null;
        List<Lekcja> lessons = dziennik.getLessons();
        for (int i = lessons.size() - 1; i >= 0; i--) {
            Lekcja lesson = lessons.get(i);
            if (lesson.getTeacher() != null && lesson.getTeacher().equals(DziennikLekcyjny.getLoggedAs())) {
                lastTeacherLesson = lesson;
                break;
            }
        }

        if (lastTeacherLesson == null) {
            System.out.println("Brak lekcji prowadzonych przez tego nauczyciela.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        List<Uczen> students = lastTeacherLesson.getGroup().getStudents();
        if (students.isEmpty()) {
            System.out.println("Brak uczniów w tej klasie.");
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            return;
        }

        System.out.println("\nLekcja: " + lastTeacherLesson);
        System.out.println("Wybierz ucznia do wystawienia oceny:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        }

        int studentIndex = -1;
        while (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.print("Podaj numer ucznia: ");
            try {
                studentIndex = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            } catch (NumberFormatException e) {
                studentIndex = -1;
            }
            if (studentIndex < 0 || studentIndex >= students.size()) {
                System.out.println("Nieprawidłowy numer ucznia.");
            }
        }

        Uczen chosenStudent = students.get(studentIndex);

        
        double gradeValue;
        while (true) {
            System.out.print("Podaj ocenę (1-6): ");
            try {
                String line = menuManager.getScanner().nextLine().trim();
                gradeValue = Double.parseDouble(line);
                if (gradeValue < 1 || gradeValue > 6) {
                    System.out.println("Ocena musi być między 1 a 6.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowy format liczby.");
            }
        }

        
        System.out.print("Podaj komentarz: ");
        String gradeComment = menuManager.getScanner().nextLine().trim();
        if (gradeComment.isEmpty()) {
            gradeComment = "Brak komentarza.";
        }

        Ocena newGrade = new Ocena(gradeValue, lastTeacherLesson, chosenStudent, gradeComment);
        dziennik.pushGrade(newGrade);

        System.out.println("\nDodano ocenę: " + chosenStudent.getFullName() +
                " - " + gradeValue + " (" + gradeComment + ")");
        System.out.println("Naciśnij Enter aby kontynuować...");
        menuManager.getScanner().nextLine();
    }

    private void displayGrades() {
        System.out.println("\n=== LISTA OCEN ===");
        List<Ocena> grades = dziennik.getGrades();
        if (grades.isEmpty()) {
            System.out.println("Brak ocen w systemie");
        } else {
            for (int i = 0; i < grades.size(); i++) {
                Ocena grade = grades.get(i);
                System.out.println((i + 1) + ". " + grade.getStudent().getFullName() + 
                    " - " + grade.getValue() + " (" + grade.getComment() + ")");
            }
        }
        System.out.println("\nNaciśnij Enter, aby kontynuować...");
        menuManager.getScanner().nextLine();
    }
}

