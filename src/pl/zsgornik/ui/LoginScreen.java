package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;

import java.io.IOException;

import static pl.zsgornik.util.Util.pauseAndReturn;

public class LoginScreen extends Screen {
    public LoginScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\nturbo dziennik - LOGOWANIE");
        System.out.print("Nazwa użytkownika: ");
    }

    @Override
    public void handleInput(String input) throws IOException {
        if (input.isEmpty()) {
            return;
        }

        System.out.print("Hasło: ");
        String password = menuManager.getConsole().readLine().trim();

        boolean success = dziennik.login(input, password);
        
        if (success) {
            System.out.println("\nZalogowano jako: " + DziennikLekcyjny.getLoggedAs().fullName());
            menuManager.replaceScreen(new MainMenuScreen(menuManager, dziennik));
        } else {
            pauseAndReturn("Nieprawidłowy login lub hasło");
        }
    }
}

