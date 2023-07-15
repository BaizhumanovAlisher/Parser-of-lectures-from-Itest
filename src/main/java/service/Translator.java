package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TranslatedText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {
    private final ObjectMapper mapper;

    public Translator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private HttpURLConnection initializeConnection() throws Exception {
        URL url = new URL("https://libretranslate.com/translate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    public String translate(String text) {
        HttpURLConnection connection = null;
        try {
            connection = initializeConnection();

            String body = mapper.writeValueAsString(new TranslatedText(text));

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String line = br.readLine();

            return mapper.readValue(line, TranslatedText.class).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}