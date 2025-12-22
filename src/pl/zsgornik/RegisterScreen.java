package pl.zsgornik;

public class RegisterScreen extends Screen {
    public RegisterScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== REJESTRACJA ===");
        // do zrobienia
        System.out.println("Naciśnij Enter aby wrócić do menu głównego...");
    }

    @Override
    public void handleInput(String input) {
        menuManager.replaceScreen(new StartScreen(menuManager, dziennik));
    }
}


