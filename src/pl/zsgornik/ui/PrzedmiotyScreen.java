package pl.zsgornik.ui;

import java.util.List;
import pl.zsgornik.enums.TypPrzedmiotu;
import pl.zsgornik.model.Nauczyciel;
import pl.zsgornik.model.Przedmiot;
import pl.zsgornik.service.DziennikLekcyjny;

import static pl.zsgornik.service.DziennikLekcyjny.getLoggedAs;
import static pl.zsgornik.util.Util.pauseAndReturn;

public class PrzedmiotyScreen extends Screen {
    public PrzedmiotyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== PRZEDMIOTY ===");
        System.out.println("\n1. Lista przedmiotów");
        System.out.println("2. Dodaj przedmiot");
        System.out.println("3. Usuń przedmiot");
        System.out.println("4. Zmień typ przedmiotu");
        System.out.println("5. Dodaj siebie do przedmiotu");
        System.out.println("6. Usuń siebie z przedmiotu");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                displaySubjects();
                break;
            case "2":
                addSubject();
                break;
            case "3":
                removeSubject();
                break;
            case "4":
                editSubject();
                break;
            case "5":
                addTeacherToSubject();
                break;
            case "6":
                removeTeacherFromSubject();
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                pauseAndReturn("Nieprawidłowa opcja");
                break;
        }
    }

    private void addSubject() {
                System.out.println("\n=== DODAJ PRZEDMIOT ===");
                TypPrzedmiotu[] types = TypPrzedmiotu.values();
                System.out.println("Wybierz typ przedmiotu:");
                for (int i = 0; i < types.length; i++) {
                    System.out.println((i + 1) + ". " + types[i].getName());
                }

                int selectedTypeIdx = -1;
                while (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                    System.out.print("Podaj numer typu przedmiotu: ");
                    try {
                        String line = menuManager.getScanner().nextLine().trim();
                        selectedTypeIdx = Integer.parseInt(line) - 1;
                    } catch (NumberFormatException e) {
                        selectedTypeIdx = -1;
                    }
                    if (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                        System.out.println("Nieprawidłowy numer typu przedmiotu.");
                    }
                }

                TypPrzedmiotu wybranyTyp = types[selectedTypeIdx];

                Przedmiot nowyPrzedmiot = new Przedmiot(wybranyTyp);
                dziennik.pushSubject(nowyPrzedmiot);

                pauseAndReturn("\nDodano przedmiot: " + wybranyTyp.getName());
    }

    private void removeSubject() {
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            pauseAndReturn("\nBrak przedmiotów do usunięcia.");
            return;
        }

        System.out.println("\n=== USUWANIE PRZEDMIOTU ===");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Podaj numer przedmiotu do usunięcia: ");
        try {
            int idx = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (idx < 0 || idx >= subjects.size()) {
                System.out.println("Nieprawidłowy numer przedmiotu.");
            } else {
                Przedmiot removed = subjects.remove(idx);
                System.out.println("Usunięto przedmiot: " + removed);
            }
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format liczby.");
        }

        pauseAndReturn();
    }

    private void editSubject() {
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            pauseAndReturn("\nBrak przedmiotów do modyfikacji.");
            return;
        }

        System.out.println("\n=== MODYFIKACJA PRZEDMIOTU ===");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Podaj numer przedmiotu do zmiany: ");
        int idx;
        try {
            idx = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            pauseAndReturn("Nieprawidłowy format liczby.");
            return;
        }

        if (idx < 0 || idx >= subjects.size()) {
            pauseAndReturn("Nieprawidłowy numer przedmiotu.");
            return;
        }

        Przedmiot subject = subjects.get(idx);

        System.out.println("\nAktualny typ: " + subject.getType().getName());
        TypPrzedmiotu[] types = TypPrzedmiotu.values();
        System.out.println("Wybierz nowy typ:");
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getName());
        }

        int selectedTypeIdx = -1;
        while (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
            System.out.print("Podaj numer typu przedmiotu: ");
            try {
                String line = menuManager.getScanner().nextLine().trim();
                selectedTypeIdx = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                selectedTypeIdx = -1;
            }
            if (selectedTypeIdx < 0 || selectedTypeIdx >= types.length) {
                System.out.println("Nieprawidłowy numer typu przedmiotu.");
            }
        }

        subject.setType(types[selectedTypeIdx]);
        pauseAndReturn("\nZmieniono typ przedmiotu na: " + subject.getType().getName());
    }

    private void displaySubjects() {
        System.out.println("\n=== LISTA PRZEDMIOTÓW ===");
        List<Przedmiot> subjects = dziennik.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("Brak przedmiotów w systemie");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                Przedmiot subject = subjects.get(i);
                System.out.print((i + 1) + ". " + subject.getType().getName());
                if (!subject.getTeachers().isEmpty()) {
                    System.out.print(" (Nauczyciele: ");
                    for (int j = 0; j < subject.getTeachers().size(); j++) {
                        System.out.print(subject.getTeachers().get(j).fullName());
                        if (j < subject.getTeachers().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.print(")");
                }
                System.out.println();
            }
        }
        pauseAndReturn();
    }

    private void addTeacherToSubject() {
        System.out.println("\n=== DODAWANIE SIEBIE DO PRZEDMIOTU ===");
        Nauczyciel loggedTeacher = getLoggedAs();
        System.out.println("Zalogowany jako: " + loggedTeacher.fullName());
        
        Przedmiot subject = selectionHelper.selectSubject();
        if (subject == null) {
            return;
        }
        
        if (subject.getTeachers().contains(loggedTeacher)) {
            pauseAndReturn("Jesteś już przypisany do tego przedmiotu.");
            return;
        }
        
        subject.addTeacher(loggedTeacher);
        pauseAndReturn("\nDodano Cię do przedmiotu: " + subject.getType().getName());
    }

    private void removeTeacherFromSubject() {
        List<Przedmiot> subjects = dziennik.getSubjects();
        Nauczyciel loggedTeacher = getLoggedAs();
        
        List<Przedmiot> teacherSubjects = subjects.stream()
            .filter(s -> s.getTeachers().contains(loggedTeacher))
            .toList();
            
        if (teacherSubjects.isEmpty()) {
            pauseAndReturn("\nNie jesteś przypisany do żadnego przedmiotu.");
            return;
        }

        System.out.println("\n=== USUWANIE SIEBIE Z PRZEDMIOTU ===");
        System.out.println("Zalogowany jako: " + loggedTeacher.fullName());
        System.out.println("\nTwoje przedmioty:");
        for (int i = 0; i < teacherSubjects.size(); i++) {
            Przedmiot subject = teacherSubjects.get(i);
            System.out.println((i + 1) + ". " + subject.getType().getName());
        }
        System.out.println("0. Anuluj");
        System.out.print("Wybierz numer przedmiotu: ");

        try {
            int idx = Integer.parseInt(menuManager.getScanner().nextLine().trim()) - 1;
            if (idx == -1) {
                return;
            }
            if (idx < 0 || idx >= teacherSubjects.size()) {
                pauseAndReturn("Nieprawidłowy numer przedmiotu.");
                return;
            }
            
            Przedmiot subject = teacherSubjects.get(idx);
            if (subject.removeTeacher(loggedTeacher)) {
                pauseAndReturn("\nUsunięto Cię z przedmiotu: " + subject.getType().getName());
            } else {
                pauseAndReturn("\nNie udało się usunąć z przedmiotu.");
            }
        } catch (NumberFormatException e) {
            pauseAndReturn("Nieprawidłowy format liczby.");
        }
    }
}

