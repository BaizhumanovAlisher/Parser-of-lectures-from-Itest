package model;

public record Lecture(String title, String lecture) {
    @Override
    public String toString() {
        return "Lecture{" +
                "title='" + title + '\'' +
                ", lecture='" + lecture + '\'' +
                '}';
    }
}
