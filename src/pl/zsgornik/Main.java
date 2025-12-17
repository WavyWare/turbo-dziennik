package pl.zsgornik;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Nauczyciel teacher1 = new Nauczyciel("Jan Kowalski");
        Nauczyciel teacher2 = new Nauczyciel("Anna Nowak");
        Nauczyciel supervisor = new Nauczyciel("Maria Wiśniewska");

        Przedmiot math = new Przedmiot(TypPrzedmiotu.MATEMATYKA);
        math.addTeacher(teacher1);
        
        Przedmiot polish = new Przedmiot(TypPrzedmiotu.POLSKI);
        polish.addTeacher(teacher2);

        Klasa class1A = new Klasa("1A", supervisor);

        Uczen student1 = new Uczen("Piotr Zieliński");
        Uczen student2 = new Uczen("Katarzyna Kowalczyk");
        Uczen student3 = new Uczen("Marek Nowak");
        Uczen student4 = new Uczen("Anna Kowalska");

        class1A.addStudent(student1);
        class1A.addStudent(student2);
        class1A.addStudent(student3);
        class1A.addStudent(student4);

        Lekcja mathLesson = new Lekcja(math, teacher1, class1A, LocalDate.now());
        Lekcja polishLesson = new Lekcja(polish, teacher2, class1A, LocalDate.now().plusDays(1));

        System.out.println("=== Rejestrowanie obecności ===");
        
        System.out.println("\nLekcja: " + mathLesson);
        mathLesson.registerAttendance(student1, StatusObecnosci.OBECNY);
        mathLesson.registerAttendance(student2, StatusObecnosci.NIEOBECNY);
        mathLesson.registerAttendance(student3, StatusObecnosci.SPOZNIONY);
        mathLesson.registerAttendance(student4, StatusObecnosci.ZWOLNIONY);

        System.out.println("\nObecności na lekcji matematyki:");
        for (Obecnosc attendance : mathLesson.getAttendances()) {
            System.out.println("  " + attendance);
        }

        System.out.println("\nLekcja: " + polishLesson);
        polishLesson.registerAttendance(student1, StatusObecnosci.OBECNY);
        polishLesson.registerAttendance(student2, StatusObecnosci.OBECNY);
        polishLesson.registerAttendance(student3, StatusObecnosci.NIEOBECNY);
        polishLesson.registerAttendance(student4, StatusObecnosci.SPOZNIONY);

        System.out.println("\nObecności na lekcji polskiego:");
        for (Obecnosc attendance : polishLesson.getAttendances()) {
            System.out.println("  " + attendance);
        }

        System.out.println("\n=== Statystyki nieobecności ===");
        int absentMath = 0;
        for (Obecnosc attendance : mathLesson.getAttendances()) {
            if (attendance.getStatus() == StatusObecnosci.NIEOBECNY) {
                absentMath++;
            }
        }
        System.out.println("Nieobecni na matematyce: " + absentMath);

        int absentPolish = 0;
        for (Obecnosc attendance : polishLesson.getAttendances()) {
            if (attendance.getStatus() == StatusObecnosci.NIEOBECNY) {
                absentPolish++;
            }
        }
        System.out.println("Nieobecni na polskim: " + absentPolish);
    }
}
