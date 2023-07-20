package model;

public record Lecture(String title, String transliteratedText, String lecture) {
    @Override
    public String toString() {
        return "Lecture{" +
                "title='" + title + '\'' +
                ", lecture='" + lecture + '\'' +
                '}';
    }

    public String getTransliteratedText() {
        return transliteratedText;
    }
}
