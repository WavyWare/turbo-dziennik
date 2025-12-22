package pl.zsgornik;

public enum StatusObecnosci {
    OBECNY("Obecny", "O", true),
    SPOZNIONY("Spóźniony", "S", true),
    ZWOLNIONY("Zwolniony", "ZW", null),
    NIEOBECNY("Nieobecny", "N", false);

    private final String fullName;
    private final String shortForm;
    private final Boolean wasPresent;

    StatusObecnosci(String fullName, String shortForm, Boolean wasPresent) {
        this.fullName = fullName;
        this.shortForm = shortForm;
        this.wasPresent = wasPresent;
    }
 
    public String getFullName() {
        return fullName;
    }
}
