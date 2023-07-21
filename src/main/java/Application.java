import builder.WebParserBuilder;
import model.Chapter;
import model.Lecture;
import model.LectureLink;
import service.FileSystem;
import service.WebParser;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        WebParser webParser = WebParserBuilder.build();
        FileSystem fileSystem = new FileSystem("/home/alisher/Desktop/History of Kazakhstan", "lecture", "audio");

        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");

        fileSystem.startWriting();
        fileSystem.writeText("# OUTLINE\n\n\n\n");

        for (Chapter chapter : chapters) {
            fileSystem.writeText("### " + chapter.title() + "\n\n");

            for (LectureLink lectureLink : chapter.lectureLinks()) {
                Lecture lecture = webParser.getLecture(lectureLink.id(), domainName, lectureLink.url());

                fileSystem.saveAudio(lecture.transliteratedText() + ".mp3", lecture.audio());
                fileSystem.saveLecture(lecture.title(), lecture.transliteratedText() + ".md", lecture.lecture());
                fileSystem.writeText(String.format("[%s](%s.md)\n\n", lecture.title(), lecture.transliteratedText()));
            }

            fileSystem.writeText("\n\n");
        }

        fileSystem.endWriting();
    }
}
