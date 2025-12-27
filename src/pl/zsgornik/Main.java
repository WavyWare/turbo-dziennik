package pl.zsgornik;

import java.util.ArrayList;
import java.util.List;
import pl.zsgornik.model.*;
import pl.zsgornik.enums.TypPrzedmiotu;
import pl.zsgornik.service.DziennikLekcyjny;
import pl.zsgornik.ui.MenuManager;
import pl.zsgornik.ui.StartScreen;

public class Main {
    private static final String[] FIRST_NAMES = {
        "Jan", "Anna", "Piotr", "Maria", "Krzysztof", "Katarzyna", "Tomasz", "Agnieszka",
        "Marek", "Magdalena", "Paweł", "Ewa", "Michał", "Joanna", "Adam", "Monika",
        "Łukasz", "Natalia", "Jakub", "Karolina", "Bartosz", "Aleksandra", "Marcin", "Weronika"
    };
    
    private static final String[] LAST_NAMES = {
        "Kowalski", "Nowak", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński",
        "Szymański", "Woźniak", "Kozłowski", "Jankowski", "Wojciechowski", "Kwiatkowski", "Krawczyk", "Kaczmarek",
        "Piotrowski", "Grabowski", "Nowakowski", "Pawłowski", "Michalski", "Nowicki", "Adamczyk", "Dudek"
    };

    public static void main(String[] args) {
        ArrayList<Klasa> groups = new ArrayList<>();
        ArrayList<Nauczyciel> teachers = new ArrayList<>();
        ArrayList<Przedmiot> subjects = new ArrayList<>();
        ArrayList<Ocena> grades = new ArrayList<>();

        // Generowanie 20 nauczycieli
        for (int i = 0; i < 20; i++) {
            String firstName = FIRST_NAMES[i % FIRST_NAMES.length];
            String lastName = LAST_NAMES[i % LAST_NAMES.length];
            String fullName = firstName + " " + lastName;
            String username = (firstName.charAt(0) + lastName).toLowerCase() + (i > 0 ? i : "");
            teachers.add(new Nauczyciel(fullName, username, "Haslo123"));
        }

        // Generowanie 20 przedmiotów (8 typów, więc niektóre się powtarzają)
        TypPrzedmiotu[] subjectTypes = TypPrzedmiotu.values();
        for (int i = 0; i < 20; i++) {
            Przedmiot subject = new Przedmiot(subjectTypes[i % subjectTypes.length]);
            // Przypisanie nauczyciela do przedmiotu (cyklicznie)
            subject.addTeacher(teachers.get(i % teachers.size()));
            subjects.add(subject);
        }

        // Generowanie 20 klas, każda z co najmniej 10 uczniami
        int studentCounter = 0;
        for (int classNum = 1; classNum <= 20; classNum++) {
            String className = (classNum <= 3 ? "1" : classNum <= 6 ? "2" : classNum <= 9 ? "3" : 
                              classNum <= 12 ? "4" : classNum <= 15 ? "5" : classNum <= 18 ? "6" : "7") + 
                              (char)('A' + ((classNum - 1) % 3));
            
            // Przypisanie wychowawcy (cyklicznie z nauczycieli)
            Nauczyciel supervisor = teachers.get((classNum - 1) % teachers.size());
            Klasa klasa = new Klasa(className, supervisor);
            
            // Dodanie co najmniej 10 uczniów do każdej klasy
            int studentsInClass = 10 + (classNum % 3); // 10-12 uczniów na klasę
            for (int j = 0; j < studentsInClass; j++) {
                String studentFirstName = FIRST_NAMES[studentCounter % FIRST_NAMES.length];
                String studentLastName = LAST_NAMES[(studentCounter + 5) % LAST_NAMES.length];
                String studentFullName = studentFirstName + " " + studentLastName;
                klasa.addStudent(new Uczen(studentFullName));
                studentCounter++;
            }
            
            groups.add(klasa);
        }

        DziennikLekcyjny dziennik = new DziennikLekcyjny(groups, teachers, subjects, grades);

        MenuManager menuManager = new MenuManager(dziennik);
        menuManager.pushScreen(new StartScreen(menuManager, dziennik));
        
        menuManager.start();
    }
}
