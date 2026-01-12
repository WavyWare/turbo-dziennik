package pl.zsgornik.ui;

import pl.zsgornik.model.Nauczyciel;
import pl.zsgornik.service.DziennikLekcyjny;

import java.io.IOException;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class RejestracjaScreen extends Screen {
    public RejestracjaScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() throws IOException {
        System.out.println("\nturbo dziennik -  REJESTRACJA");

        String fullName;
        while (true) {
            System.out.print("Podaj imię i nazwisko nauczyciela: ");
            fullName = menuManager.getConsole().readLine().trim();
            if (!fullName.isEmpty()) {
                break;
            }
            System.out.println("Imię i nazwisko nie może być puste. Spróbuj ponownie.");
        }

        String username;
        while (true) {
            System.out.print("Podaj login: ");
            String inputUsername = menuManager.getConsole().readLine().trim();
            if (inputUsername.isEmpty()) {
                System.out.println("Login nie może być pusty. Spróbuj ponownie.");
                continue;
            }
            final String finalUsername = inputUsername;
            if (dziennik.getTeachers().stream().anyMatch(x -> x.username().equals(finalUsername))) {
                System.out.println("Login już istnieje. Spróbuj ponownie.");
                continue;
            }
            username = inputUsername;
            break;
        }

        String password;
        while (true) {
            System.out.print("Podaj hasło: ");
            password = menuManager.getConsole().readLine();
            
            if (password.isEmpty()) {
                System.out.println("Hasło nie może być puste. Spróbuj ponownie.");
                continue;
            }
            if (password.length() < 8) {
                System.out.println("Hasło musi mieć co najmniej 8 znaków. Spróbuj ponownie.");
                continue;
            }
            if (!password.matches(".*[A-Z].*")) {
                System.out.println("Hasło musi zawierać co najmniej jedną dużą literę. Spróbuj ponownie.");
                continue;
            }
            if (!password.matches(".*[a-z].*")) {
                System.out.println("Hasło musi zawierać co najmniej jedną małą literę. Spróbuj ponownie.");
                continue;
            }
            break;
        }
        
        Nauczyciel teacher = new Nauczyciel(fullName, username, password);
        dziennik.pushTeacher(teacher);
        pauseAndReturn("Rejestracja zakończona pomyślnie!");
    }

    @Override
    public void handleInput(String input) {
        menuManager.replaceScreen(new StartScreen(menuManager, dziennik));
    }
}