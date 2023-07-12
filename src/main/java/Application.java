import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import json.ViewDeserializer;
import model.Chapter;
import model.Lecture;
import model.View;
import service.FileSystem;
import service.WebParser;

import java.io.File;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        ObjectMapper mapper = new ObjectMapper().registerModule(new SimpleModule()
                .addDeserializer(View.class, new ViewDeserializer()));
        WebParser webParser = new WebParser(mapper);
        FileSystem fileSystem = new FileSystem();

        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");
        File parentDirectory = fileSystem.makeParentFolder("history_of_kazakhstan");

        for (Chapter chapter : chapters) {
            List<Lecture> lectures = webParser.getLectures(domainName, chapter);

        }
    }
}
