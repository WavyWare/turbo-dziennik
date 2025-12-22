package pl.zsgornik;

public class StartScreen extends Screen {
    public StartScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== TURBO DZIENNIK ===");
        System.out.println("1. Zaloguj");
        System.out.println("2. Zarejestruj");
        System.out.println("3. Wyjdź");
        System.out.print("Wybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        if (input.isEmpty()) {
            return;
        }

        switch (input) {
            case "1":
                menuManager.replaceScreen(new LoginScreen(menuManager, dziennik));
                break;
            case "2":
                menuManager.replaceScreen(new RegisterScreen(menuManager, dziennik));
                break;
            case "3":
                System.out.println("\nZamykanie programu...");
                menuManager.stop();
                break;
            default:
                System.out.println("\nNieprawidłowa opcja. Spróbuj ponownie.");
                break;
        }
    }
}


