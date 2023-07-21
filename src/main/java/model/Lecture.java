package model;

public record Lecture(String title, String transliteratedText, String lecture, byte[] audio) {
    @Override
    public String toString() {
        return "Lecture{" +
                "title='" + title + '\'' +
                ", lecture='" + lecture + '\'' +
                '}';
    }
}
