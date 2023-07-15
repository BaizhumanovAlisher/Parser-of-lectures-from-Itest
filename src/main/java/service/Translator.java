package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TranslatedText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {
    private final ObjectMapper mapper;

    public Translator(ObjectMapper mapper) {
        this.mapper = mapper;
    }



    public String translate(String text) {
        HttpURLConnection connection = null;
        try {
            String body = mapper.writeValueAsString(new TranslatedText(text));

            connection = initializeConnection();
            requestToServer(connection, body);
            checkConnectionCode(connection);
            String respond = getString(connection);

            return mapper.readValue(respond, TranslatedText.class).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection initializeConnection() throws Exception {
        URL url = new URL("https://libretranslate.com/translate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    private String getString(HttpURLConnection connection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        return br.readLine();
    }

    private void checkConnectionCode(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }
    }

    private void requestToServer(HttpURLConnection connection, String body) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(body.getBytes());
        outputStream.flush();
    }
}