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
    private final String[] addresses;
    private int currentIndex;

    public Translator(ObjectMapper mapper) {
        this.mapper = mapper;

        addresses = new String[] {
                "http://localhost:5000/translate",
                "https://libretranslate.com/translate"
        };
        currentIndex = 0;
    }

    private HttpURLConnection createNewConnection() {
        try {
            return initializeConnection();
        } catch (Exception e) {
            return switchAddressAndInitializeConnection();
        }
    }

    private HttpURLConnection initializeConnection() throws Exception {
        URL url = new URL(addresses[currentIndex]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    private HttpURLConnection switchAddressAndInitializeConnection() {
        currentIndex = (currentIndex + 1) % addresses.length;
        try {
            return initializeConnection();
        } catch (Exception e) {
            throw new RuntimeException("Can not connect to LibreTranslate", e);
        }
    }

    public String translate(String text) {
        HttpURLConnection connection = null;
        try {
            connection = createNewConnection();

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