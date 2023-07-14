import builder.MapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import model.Chapter;
import model.Lecture;
import service.FileSystem;
import service.Translator;
import service.WebParser;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        ObjectMapper mapper = MapperBuilder.build();
        WebParser webParser = new WebParser(mapper, FlexmarkHtmlConverter.builder().build());
        Translator translator = new Translator(mapper);
        FileSystem fileSystem = new FileSystem("/home/alisher/Desktop", translator);

        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");
        fileSystem.makeParentFolder("История Казахстана");

        for (Chapter chapter : chapters) {
            List<Lecture> lectures = webParser.getLectures(domainName, chapter);
            fileSystem.makeChildFolder(chapter.title());
        }
    }
}
