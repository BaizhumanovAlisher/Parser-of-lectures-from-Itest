package json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.View;

import java.io.IOException;

public class ViewDeserializer extends StdDeserializer<View> {
    public ViewDeserializer() {
        super(View.class);
    }

    @Override
    public View deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JacksonException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        String view = root.get("view").asText();

        return new View(view);
    }
}
