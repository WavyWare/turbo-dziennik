package pl.zsgornik;

public enum StatusObecnosci {
    OBECNY("Obecny", "O", true),
    SPOZNIONY("Spóźniony", "S", true),
    ZWOLNIONY("Zwolniony", "ZW", null),
    NIEOBECNY("Nieobecny", "N", false);

    private String fullname;
    private String shortForm;
    private boolean wasPresent;

    StatusObecnosci(String fullname, String shortForm, Boolean wasPresent) {
        this.fullname = fullname;
        this.shortForm = shortForm;
        this.wasPresent = wasPresent;
    }

    public String getFullname() {
        return fullname;
    }

    public String getShortForm() {
        return shortForm;
    }

    public boolean isWasPresent() {
        return wasPresent;
    }
}
