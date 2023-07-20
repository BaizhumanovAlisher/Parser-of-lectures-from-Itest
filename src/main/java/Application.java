import builder.WebParserBuilder;
import model.Chapter;
import model.Lecture;
import model.LectureLink;
import service.FileSystem;
import service.WebParser;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        String domainName = "https://itest.kz";

        WebParser webParser = WebParserBuilder.build();
        FileSystem fileSystem = new FileSystem("/home/alisher/Desktop/History of Kazakhstan", "lecture", "audio");

        fileSystem.startWriteOutline();
        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");

        for (Chapter chapter : chapters) {
            fileSystem.writeChapterNameInOutline();

            for (LectureLink lectureLink : chapter.lectureLinks()) {
                Lecture lecture = webParser.getLecture(lectureLink.id(), domainName, lectureLink.url());
                fileSystem.saveLecture(lecture);
            }
        }

        fileSystem.endWriteOutline();
    }
}
