package agh.ics.oop.simulation;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

public interface MoveValidator {
    Vector2d newPosition(Vector2d position, MapDirection mapDirection);
}
