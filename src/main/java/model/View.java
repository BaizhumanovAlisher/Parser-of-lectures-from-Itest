package model;

public record View(String view) {
    @Override
    public String toString() {
        return view;
    }
}
