package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.TranslatedText;

import java.io.IOException;

public class TranslatedTextSerializer extends StdSerializer<TranslatedText> {
    public TranslatedTextSerializer() {
        super(TranslatedText.class);
    }

    @Override
    public void serialize(TranslatedText value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("q", value.text());
        gen.writeStringField("source", "ru");
        gen.writeStringField("target", "en");

        gen.writeEndObject();
    }
}
