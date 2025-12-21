package pl.zsgornik;

public class MainMenuScreen extends Screen {
    public MainMenuScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== GŁÓWNE MENU ===");
        System.out.println("Zalogowany jako: " + DziennikLekcyjny.getLoggedAs().getFullName());
        System.out.println("\n1. Zarządzaj lekcjami");
        System.out.println("2. Zarządzaj ocenami");
        System.out.println("3. Zarządzaj obecnościami");
        System.out.println("4. Zarządzaj klasami");
        System.out.println("5. Zarządzaj przedmiotami");
        System.out.println("6. Wyloguj");
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
                dziennik.logout();
                System.out.println("\n✓ Wylogowano pomyślnie.");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                menuManager.replaceScreen(new LoginScreen(menuManager, dziennik));
                break;
            case "0":
                System.out.println("\nDo widzenia!");
                menuManager.stop();
                break;
            default:
                System.out.println("\n✗ Nieprawidłowa opcja! Naciśnij Enter, aby kontynuować...");
                menuManager.getScanner().nextLine();
                break;
        }
    }
}

