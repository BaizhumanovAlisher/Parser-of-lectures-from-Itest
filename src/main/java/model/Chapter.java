package model;

public record Chapter(String title, String address) {
    @Override
    public String toString() {
        return "Chapter{" +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
