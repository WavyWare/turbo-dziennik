package pl.zsgornik.ui;

import pl.zsgornik.model.Nauczyciel;
import pl.zsgornik.service.DziennikLekcyjny;

public class RejestracjaScreen extends Screen {
    public RejestracjaScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== REJESTRACJA ===");
        System.out.println("Podaj imię i nazwisko nauczyciela: ");
        String fullName = menuManager.getScanner().nextLine();
        if (fullName.isEmpty()) {
            System.out.println("Imię i nazwisko nie może być puste");
            throw new IllegalArgumentException("Imię i nazwisko nie może być puste");
        }
        System.out.println("Podaj login: ");
        String username = menuManager.getScanner().nextLine();
        if (username.isEmpty()) {
            System.out.println("Login nie może być pusty");
            throw new IllegalArgumentException("Login nie może być pusty");
        }
        if (dziennik.getTeachers().stream().anyMatch(x -> x.username().equals(username))) {
            System.out.println("Login już istnieje");
            throw new IllegalArgumentException("Login już istnieje");
        }
        System.out.println("Podaj hasło: ");
        String password = menuManager.getScanner().nextLine();
        if (password.isEmpty()) {
            System.out.println("Hasło nie może być puste");
            throw new IllegalArgumentException("Hasło nie może być puste");
        }
        if (password.length() < 8) {
            System.out.println("Hasło musi mieć co najmniej 8 znaków");
            throw new IllegalArgumentException("Hasło musi mieć co najmniej 8 znaków");
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Hasło musi zawierać co najmniej jedną dużą literę");
            throw new IllegalArgumentException("Hasło musi zawierać co najmniej jedną dużą literę");
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Hasło musi zawierać co najmniej jedną małą literę");
            throw new IllegalArgumentException("Hasło musi zawierać co najmniej jedną małą literę");
        }
        Nauczyciel teacher = new Nauczyciel(fullName, username, password);
        dziennik.pushTeacher(teacher);
        System.out.println("Naciśnij Enter aby wrócić do menu głównego...");
        menuManager.getScanner().nextLine();
    }

    @Override
    public void handleInput(String input) {
        menuManager.replaceScreen(new StartScreen(menuManager, dziennik));
    }
}


