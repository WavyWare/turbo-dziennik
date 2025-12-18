package pl.zsgornik;

import java.time.LocalDate;

public class Ocena {
    private double value;
    private String comment;
    private LocalDate date;
    private Lekcja lesson;
    private Uczen student;

    public Ocena(double value, Lekcja lesson, Uczen student,String comment, LocalDate date) {
        this.value = value;
        this.comment = comment;
        this.date = date;
        this.lesson = lesson;
        this.student = student;
    }

    public Ocena(double value, Lekcja lesson, Uczen student, LocalDate date) {
        this(value, lesson, student);
        this.date = date;
    }

    public Ocena(double value, Lekcja lesson, Uczen student, String comment) {
        this(value, lesson, student);
        this.comment = comment;
    }

    public Ocena(double value, Lekcja lesson, Uczen student) {

        this.lesson = lesson;
        this.student = student;
        this.value = value;
        comment = "Brak komentarza.";
        date = LocalDate.now();
    }

    public double getValue() {
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

    public Uczen getStudent() {
        return student;
    }

    public void setStudent(Uczen student) {
        this.student = student;
    }

    public Lekcja getLesson() {
        return lesson;
    }

    public void setLesson(Lekcja lesson) {
        this.lesson = lesson;
    }
}
