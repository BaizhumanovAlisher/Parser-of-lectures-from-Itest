package json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.TranslatedText;

import java.io.IOException;

public class TranslatedTextDeserializer extends StdDeserializer<TranslatedText> {
    public TranslatedTextDeserializer() {
        super(TranslatedText.class);
    }

    @Override
    public TranslatedText deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode root = p.getCodec().readTree(p);

        String title = root.get("translatedText").asText();

        return new TranslatedText(title);
    }
}
