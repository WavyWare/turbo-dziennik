package pl.zsgornik;

import java.util.ArrayList;
import pl.zsgornik.model.*;
import pl.zsgornik.enums.TypPrzedmiotu;
import pl.zsgornik.service.DziennikLekcyjny;
import pl.zsgornik.ui.MenuManager;
import pl.zsgornik.ui.StartScreen;

public class Main {
    private static final String[] FIRST_NAMES = {
        "Jan", "Anna", "Piotr", "Maria", "Krzysztof", "Katarzyna", "Tomasz", "Agnieszka",
        "Marek", "Magdalena", "Paweł", "Ewa", "Michał", "Joanna", "Adam", "Monika",
        "Łukasz", "Natalia", "Jakub", "Karolina", "Bartosz", "Aleksandra", "Marcin", "Weronika",
        "Kamil", "Patrycja", "Dawid", "Sylwia", "Rafał", "Dominika", "Mateusz", "Paulina",
        "Kacper", "Martyna", "Filip", "Julia", "Szymon", "Wiktoria", "Maciej", "Zuzanna",
        "Adrian", "Oliwia", "Daniel", "Amelia", "Hubert", "Maja", "Sebastian", "Hanna",
        "Mikołaj", "Emilia", "Wiktor", "Nina", "Oskar", "Lena", "Antoni", "Zofia",
        "Tymon", "Laura", "Igor", "Marcelina", "Alan", "Klara", "Fabian", "Milena"
    };
    
    private static final String[] LAST_NAMES = {
        "Kowalski", "Nowak", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński",
        "Szymański", "Woźniak", "Kozłowski", "Jankowski", "Wojciechowski", "Kwiatkowski", "Krawczyk", "Kaczmarek",
        "Piotrowski", "Grabowski", "Nowakowski", "Pawłowski", "Michalski", "Nowicki", "Adamczyk", "Dudek",
        "Zając", "Wieczorek", "Jabłoński", "Majewski", "Olszewski", "Jaworski", "Wróbel", "Malinowski",
        "Pawlak", "Witkowski", "Walczak", "Stepień", "Górski", "Rutkowski", "Michalak", "Sikora",
        "Ostrowski", "Baran", "Duda", "Szewczyk", "Tomaszewski", "Pietrzak", "Marciniak", "Wróblewski",
        "Zalewski", "Jakubowski", "Jasiński", "Zawadzki", "Sadowski", "Bąk", "Wilk", "Sokołowski",
        "Lis", "Kubiak", "Król", "Kania", "Mazur", "Brzeziński", "Pająk", "Szymczak"
    };
    
    private static final String[] CLASS_NAMES = {
        "1A", "1B", "1C", "2A", "2B", "2C", "3A", "3B", "3C",
        "4A", "4B", "4C", "5A", "5B", "5C", "6A", "6B", "6C",
        "7A", "7B"
    };

    public static void main(String[] args) {
        ArrayList<Klasa> groups = new ArrayList<>();
        ArrayList<Nauczyciel> teachers = new ArrayList<>();
        ArrayList<Przedmiot> subjects = new ArrayList<>();
        ArrayList<Ocena> grades = new ArrayList<>();

        teachers.add(new Nauczyciel("Jan Kowalski", "jkowalski123", "haslo123"));

        for (int i = 0; i < 20; i++) {
            String firstName = FIRST_NAMES[i % FIRST_NAMES.length];
            String lastName = LAST_NAMES[i % LAST_NAMES.length];
            String fullName = firstName + " " + lastName;
            String username = (firstName.charAt(0) + lastName).toLowerCase() + (i > 0 ? i : "");
            teachers.add(new Nauczyciel(fullName, username, "Haslo123"));
        }


        TypPrzedmiotu[] subjectTypes = TypPrzedmiotu.values();
        for (int i = 0; i < 20; i++) {
            Przedmiot subject = new Przedmiot(subjectTypes[i % subjectTypes.length]);
            subject.addTeacher(teachers.get(i % teachers.size()));
            subjects.add(subject);
        }

        int studentCounter = 0;
        for (int classNum = 0; classNum < CLASS_NAMES.length; classNum++) {
            String className = CLASS_NAMES[classNum];
            
            Nauczyciel supervisor = teachers.get(classNum % teachers.size());
            Klasa klasa = new Klasa(className, supervisor);
            
            int studentsInClass = 10 + (classNum % 3);
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
