package agh.ics.oop.model;

public record Grass(Vector2d position) {

    @Override
    public String toString() {
        return "*";
    }
}
