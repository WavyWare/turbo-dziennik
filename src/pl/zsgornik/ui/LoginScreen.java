package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;

public class LoginScreen extends Screen {
    public LoginScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== LOGOWANIE ===");
        System.out.print("Nazwa użytkownika: ");
    }

    @Override
    public void handleInput(String input) {
        if (input.isEmpty()) {
            return;
        }

        String username = input;
        System.out.print("Hasło: ");
        String password = menuManager.getScanner().nextLine().trim();

        boolean success = dziennik.login(username, password);
        
        if (success) {
            System.out.println("\nZalogowano jako: " + DziennikLekcyjny.getLoggedAs().getFullName());
            System.out.println("Naciśnij Enter aby kontynuować...");
            menuManager.getScanner().nextLine();
            menuManager.replaceScreen(new MainMenuScreen(menuManager, dziennik));
        } else {
            System.out.println("\nNieprawidłowa nazwa użytkownika lub hasło.");
            System.out.println("Naciśnij Enter aby spróbować ponownie...");
            menuManager.getScanner().nextLine();
        }
    }
}

