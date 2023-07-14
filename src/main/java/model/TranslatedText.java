package model;

public record TranslatedText(String text) {
    @Override
    public String toString() {
        return text;
    }
}
