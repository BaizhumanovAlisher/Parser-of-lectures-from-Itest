package model;

import java.util.List;

public record Chapter(String title, List<LectureLink> lectureLinks) {
    @Override
    public String toString() {
        return "Chapter{" +
                "title='" + title + '\'' +
                ", lectureLinks=" + lectureLinks +
                '}';
    }
}
