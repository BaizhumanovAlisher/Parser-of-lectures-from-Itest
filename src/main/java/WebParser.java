import model.Chapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebParser {
    public List<Chapter> getChapters(String domainName, String path) {
        try {
            Document document = Jsoup.connect(domainName + path).get();
            Elements elements = document.getElementsByClass("select-block select-block_mod_section");

            List<Chapter> chapters = new ArrayList<>();

            for (Element e : elements) {
                String[] split = e.text().split("\\. ", 2);
                int partitionNumber = RomanConverter.romanToArabic(split[0]);
                String name = split[1];
                String address = e.attr("data-url");
                chapters.add(new Chapter(partitionNumber, name, address));
            }

            return chapters;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
