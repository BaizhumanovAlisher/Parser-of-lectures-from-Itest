package model;

public record Lecture(String title, String lecture, String summarise) {
    @Override
    public String toString() {
        return "Lecture{" +
                "title='" + title + '\'' +
                ", lecture='" + lecture + '\'' +
                ", summarise='" + summarise + '\'' +
                '}';
    }
}
