package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import model.Chapter;
import model.Lecture;
import model.LectureLink;
import model.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.homyakin.iuliia.Translator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebParser {
    private final ObjectMapper mapper;
    private final FlexmarkHtmlConverter converter;
    private final Translator translator;
    private int countOfLecture;

    public WebParser(ObjectMapper mapper, FlexmarkHtmlConverter converter, Translator translator) {
        this.mapper = mapper;
        this.converter = converter;
        this.translator = translator;
        this.countOfLecture = 0;
    }

    public List<Chapter> getChapters(String domainName, String path) {
        try {
            Document document = Jsoup.connect(domainName + path).get();
            Elements elements = document.getElementsByClass("select-block select-block_mod_section");

            List<Chapter> chapters = new ArrayList<>();

            for (Element e : elements) {
                String name = e.text();
                String address = e.attr("data-url");
                List<LectureLink> lectureLinks = getLectureLinks(domainName, address);
                chapters.add(new Chapter(name, lectureLinks));
            }

            return chapters;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<LectureLink> getLectureLinks(String domainName, String address) {
        try {
            String responseJson = Jsoup.connect(domainName + address)
                    .ignoreContentType(true)
                    .execute().body();

            View view = mapper.readValue(responseJson, View.class);
            Document document = Jsoup.parse(view.toString());
            Elements elements = document.getElementsByClass("button button_theme_conspect");

            List<LectureLink> lectureLinks = new ArrayList<>();

            for (Element element : elements) {
                String url = element.attr("href");
                LectureLink lectureLink = new LectureLink(++countOfLecture, url);
                lectureLinks.add(lectureLink);
            }

            return lectureLinks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Lecture getLecture(int id, String domainName, String url) {
        try {
            Document document = Jsoup.connect(domainName + url).get();

            String title = "%d) %s".formatted(id, document.getElementsByClass("header-title-inner").first().text());
            String lecture = converter.convert(document.getElementsByClass("content-text").toString());

            String pathToAudio = document.getElementsByClass("content-audio").first().attr("src");

            byte[] audio = getUrlAudio(domainName, pathToAudio);

            return new Lecture(title, translator.translate(title), lecture, audio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getUrlAudio(String domainName, String pathToAudio) {
        String audioUrl = domainName + pathToAudio;

        try {
            URL url = new URL(audioUrl);
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
