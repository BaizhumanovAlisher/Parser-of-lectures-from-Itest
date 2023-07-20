package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import model.Chapter;
import model.Lecture;
import model.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.homyakin.iuliia.Translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebParser {
    private final ObjectMapper mapper;
    private final FlexmarkHtmlConverter converter;
    private final Translator translator;

    public WebParser(ObjectMapper mapper, FlexmarkHtmlConverter converter, Translator translator) {
        this.mapper = mapper;
        this.converter = converter;
        this.translator = translator;
    }

    public List<Chapter> getChapters(String domainName, String path) {
        try {
            Document document = Jsoup.connect(domainName + path).get();
            Elements elements = document.getElementsByClass("select-block select-block_mod_section");

            List<Chapter> chapters = new ArrayList<>();

            for (Element e : elements) {
                String name = e.text().split("\\. ", 2)[1];
                String address = e.attr("data-url");
                chapters.add(new Chapter(name, address));
            }

            return chapters;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Lecture> getLectures(String domainName, Chapter chapter) {
        try {
            String responseJson = Jsoup.connect(domainName + chapter.address())
                    .ignoreContentType(true)
                    .execute().body();

            View view = mapper.readValue(responseJson, View.class);
            Document document = Jsoup.parse(view.toString());
            Elements elements = document.getElementsByClass("button button_theme_conspect");

            List<Lecture> lectures = new ArrayList<>();

            for (Element element : elements) {
                String url = element.attr("href");

                Lecture lecture = getLecture(domainName, url);
                lectures.add(lecture);
            }

            return lectures;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Lecture getLecture(String domainName, String url) {
        try {
            Document document = Jsoup.connect(domainName + url).get();

            String title = document.getElementsByClass("header-title-inner").first().text();
            String lecture = converter.convert(document.getElementsByClass("content-text").toString());

            return new Lecture(title, translator.translate(title), lecture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
