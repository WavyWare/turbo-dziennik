package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;

public class MainMenuScreen extends Screen {
    public MainMenuScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== MENU GŁÓWNE ===");
        System.out.println("Zalogowany jako: " + DziennikLekcyjny.getLoggedAs().getFullName());
        System.out.println("\n1. Lekcje");
        System.out.println("2. Oceny");
        System.out.println("3. Obecności");
        System.out.println("4. Klasy");
        System.out.println("5. Przedmioty");
        System.out.println("6. Uczniowie");
        System.out.println("7. Wyloguj");
        System.out.println("0. Wyjście");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1":
                menuManager.pushScreen(new LekcjeScreen(menuManager, dziennik));
                break;
            case "2":
                menuManager.pushScreen(new OcenyScreen(menuManager, dziennik));
                break;
            case "3":
                menuManager.pushScreen(new ObecnosciScreen(menuManager, dziennik));
                break;
            case "4":
                menuManager.pushScreen(new KlasyScreen(menuManager, dziennik));
                break;
            case "5":
                menuManager.pushScreen(new PrzedmiotyScreen(menuManager, dziennik));
                break;
            case "6":
                menuManager.pushScreen(new UczenScreen(menuManager, dziennik));
                break;
            case "7":
                dziennik.logout();
                System.out.println("\nWylogowano pomyślnie");
                System.out.println("Naciśnij Enter aby kontynuować...");
                menuManager.getScanner().nextLine();
                menuManager.replaceScreen(new StartScreen(menuManager, dziennik));
                break;
            case "0":
                System.out.println("\nDo widzenia.");
                menuManager.stop();
                break;
            default:
                System.out.println("\nNieprawidłowa opcja");
                System.out.println("Naciśnij Enter aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
        }
    }
}

