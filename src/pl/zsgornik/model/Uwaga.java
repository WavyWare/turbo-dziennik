package pl.zsgornik.model;

public class Uwaga {
    private final boolean isPositive;
    private String description;

    public Uwaga(boolean isPositive, String description) {
        this.isPositive = isPositive;
        this.description = description;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override()
    public String toString() {
        return getDescription() + " - Typ uwagi: " + (isPositive ? "Pozytywna" : "Negatywna");
    }
}
