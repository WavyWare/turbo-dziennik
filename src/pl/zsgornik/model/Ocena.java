package pl.zsgornik.model;

public class Ocena {
    private double value;
    private String comment;
    private final Lekcja lesson;
    private final Uczen student;

    public Ocena(double value, Lekcja lesson, Uczen student,String comment) {
        this.value = value;
        this.comment = comment;
        this.lesson = lesson;
        this.student = student;
    }

    public Ocena(double value, Lekcja lesson, Uczen student) {
        this.lesson = lesson;
        this.student = student;
        this.value = value;
        comment = "Brak komentarza.";
    }

    public double getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public Uczen getStudent() {
        return student;
    }

    public Lekcja getLesson() {
        return lesson;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ocena: " + value + ", Komentarz: " + comment + ", Ucznia: " + student + " z lekcji " + lesson;
    }

}
