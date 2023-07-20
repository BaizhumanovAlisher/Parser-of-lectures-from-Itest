import builder.WebParserBuilder;
import model.Chapter;
import model.Lecture;
import service.FileSystem;
import service.WebParser;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        WebParser webParser = WebParserBuilder.build();
        FileSystem fileSystem = new FileSystem("/home/alisher/Desktop");

        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");
        fileSystem.makeParentFolder("History of Kazakhstan");

        for (Chapter chapter : chapters) {
            List<Lecture> lectures = webParser.getLectures(domainName, chapter);
            fileSystem.makeChildFolder(chapter.title());
        }
    }
}
