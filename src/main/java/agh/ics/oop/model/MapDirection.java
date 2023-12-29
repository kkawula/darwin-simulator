package agh.ics.oop.model;

public enum MapDirection {
    NORTH_WEST,
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST;

    @Override
    public String toString() {
        return switch (this) {
            case NORTH_WEST -> "NW";
            case NORTH -> "N";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case SOUTH_EAST -> "SE";
            case SOUTH -> "S";
            case SOUTH_WEST -> "SW";
            case WEST -> "W";
        };
    }

    public MapDirection next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    public MapDirection previous() {
        int previousOrdinal = (this.ordinal() + values().length - 1) % values().length;
        return values()[previousOrdinal];
    }

    public MapDirection rotate(int n) {
        int nextOrdinal = (this.ordinal() + n) % values().length;
        return values()[nextOrdinal];
    }

    public static MapDirection random() {
        int randomOrdinal = (int) (Math.random() * values().length);
        return values()[randomOrdinal];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH_WEST -> new Vector2d(-1, 1);
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
        };
    }
}

