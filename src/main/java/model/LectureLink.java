package model;

public record LectureLink(int id, String url) {
    @Override
    public String toString() {
        return "LectureLink{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
