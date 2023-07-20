package builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import json.ViewDeserializer;
import model.View;
import ru.homyakin.iuliia.Schemas;
import ru.homyakin.iuliia.Translator;
import service.WebParser;

public class WebParserBuilder {
    public static WebParser build() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new SimpleModule()
                .addDeserializer(View.class, new ViewDeserializer()));
        return new WebParser(mapper, FlexmarkHtmlConverter.builder().build(), new Translator(Schemas.ICAO_DOC_9303));
    }
}
