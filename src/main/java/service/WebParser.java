package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Chapter;
import model.Lecture;
import model.View;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebParser {
    private ObjectMapper mapper;

    public WebParser(ObjectMapper mapper) {
        this.mapper = mapper;
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
        return null;
    }
}
