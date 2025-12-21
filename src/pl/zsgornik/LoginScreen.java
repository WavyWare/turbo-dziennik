package pl.zsgornik;

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
            System.out.println("\n✓ Zalogowano pomyślnie jako: " + DziennikLekcyjny.getLoggedAs().getFullName());
            System.out.println("Naciśnij Enter, aby kontynuować...");
            menuManager.getScanner().nextLine();
            menuManager.replaceScreen(new MainMenuScreen(menuManager, dziennik));
        } else {
            System.out.println("\n✗ Błąd logowania! Nieprawidłowa nazwa użytkownika lub hasło.");
            System.out.println("Naciśnij Enter, aby spróbować ponownie...");
            menuManager.getScanner().nextLine();
        }
    }
}

