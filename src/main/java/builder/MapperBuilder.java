package builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import json.TranslatedTextSerializer;
import json.deserializer.TranslatedTextDeserializer;
import json.deserializer.ViewDeserializer;
import model.TranslatedText;
import model.View;

public class MapperBuilder {
    public static ObjectMapper build() {
        return new ObjectMapper().registerModule(new SimpleModule()
                .addDeserializer(View.class, new ViewDeserializer())
                .addDeserializer(TranslatedText.class, new TranslatedTextDeserializer())
                .addSerializer(new TranslatedTextSerializer()));
    }
}
