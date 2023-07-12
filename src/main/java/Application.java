import builder.WebParserBuilder;
import model.Chapter;
import model.Lecture;
import service.FileSystem;
import service.WebParser;

import java.io.File;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        WebParser webParser = WebParserBuilder.build();
        FileSystem fileSystem = new FileSystem();

        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");
        File parentFolder = fileSystem.makeParentFolder("history_of_kazakhstan");

        for (Chapter chapter : chapters) {
            List<Lecture> lectures = webParser.getLectures(domainName, chapter);

        }
    }
}
