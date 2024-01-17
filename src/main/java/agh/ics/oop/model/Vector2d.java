package agh.ics.oop.model;

public record Vector2d(int x, int y) {

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public static Vector2d intToVector2d(int linearizedVector2d, int width) {
        return new Vector2d(linearizedVector2d % width, linearizedVector2d / width);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
