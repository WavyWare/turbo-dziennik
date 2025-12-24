package pl.zsgornik.enums;

import java.util.Scanner;

public enum StatusObecnosci {
    OBECNY("Obecny", true),
    SPOZNIONY("Spóźniony", true),
    ZWOLNIONY("Zwolniony", null),
    NIEOBECNY("Nieobecny", false);

    private final String fullName;
    private final Boolean wasPresent;

    StatusObecnosci(String fullName, Boolean wasPresent) {
        this.fullName = fullName;
        this.wasPresent = wasPresent;
    }
 
    public String getFullName() {
        return fullName;
    }

    public Boolean getWasPresent() {
        return wasPresent;
    }

    public static StatusObecnosci chooseType() {
        StatusObecnosci[] types = StatusObecnosci.values();

        System.out.println("\n=== WYBIERZ TYP ===");
        for (int i = 0; i < types.length; i++) {
            StatusObecnosci status = types[i];
            System.out.println((i + 1) + ". " + status.getFullName());
        }
        System.out.println("0. Anuluj");
        System.out.print("Wybierz numer: ");

        String input = new Scanner(System.in).nextLine();
        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy numer");
            return null;
        }

        if (choice == 0) {
            return null;
        }

        if (choice < 1 || choice > types.length) {
            System.out.println("Numer poza zakresem");
            return null;
        }

        return types[choice - 1];
    }
}
