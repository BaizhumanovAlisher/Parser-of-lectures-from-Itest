package builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import json.ViewDeserializer;
import model.View;
import service.WebParser;

public class WebParserBuilder {
    public static WebParser build() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new SimpleModule()
                .addDeserializer(View.class, new ViewDeserializer()));
        FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder().build();

        return new WebParser(mapper, converter);
    }
}
