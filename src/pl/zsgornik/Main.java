package pl.zsgornik;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Klasa> groups = new ArrayList<>();
        ArrayList<Nauczyciel> teachers = new ArrayList<>();
        ArrayList<Przedmiot> subjects = new ArrayList<>();
        ArrayList<Ocena> grades = new ArrayList<>();

        Nauczyciel teacher1 = new Nauczyciel("Jan Kowalski", "jkowalski", "haslo123");
        Nauczyciel teacher2 = new Nauczyciel("Anna Nowak", "anowak", "haslo123");
        Nauczyciel supervisor = new Nauczyciel("Maria Wiśniewska", "mwisniewska", "haslo123");
        
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(supervisor);

        Przedmiot math = new Przedmiot(TypPrzedmiotu.MATEMATYKA);
        math.addTeacher(teacher1);
        subjects.add(math);
        
        Przedmiot polish = new Przedmiot(TypPrzedmiotu.POLSKI);
        polish.addTeacher(teacher2);
        subjects.add(polish);

        Klasa class1A = new Klasa("1A", supervisor);
        
        Uczen student1 = new Uczen("Piotr Zieliński");
        Uczen student2 = new Uczen("Katarzyna Kowalczyk");
        Uczen student3 = new Uczen("Marek Nowak");
        Uczen student4 = new Uczen("Anna Kowalska");

        class1A.addStudent(student1);
        class1A.addStudent(student2);
        class1A.addStudent(student3);
        class1A.addStudent(student4);
        
        groups.add(class1A);

        DziennikLekcyjny dziennik = new DziennikLekcyjny(groups, teachers, subjects, grades);

        MenuManager menuManager = new MenuManager(dziennik);
        menuManager.pushScreen(new StartScreen(menuManager, dziennik));

        menuManager.start();
    }
}
