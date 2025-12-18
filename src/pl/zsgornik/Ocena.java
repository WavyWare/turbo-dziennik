package pl.zsgornik;

public class Ocena {
    private int value;
    private String comment;

    public Ocena(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public Ocena(int value) {
        this.value = value;
        comment = "Brak komentarza.";
    }

    public int getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
