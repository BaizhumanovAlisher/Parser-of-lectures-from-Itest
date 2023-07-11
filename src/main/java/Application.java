import model.Chapter;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        String domainName = "https://itest.kz";

        WebParser webParser = new WebParser();
        List<Chapter> chapters = webParser.getChapters(domainName, "/ru/attestation/istoriya-kazahstana-4077");


    }
}
