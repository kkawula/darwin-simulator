package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;

import java.util.HashMap;
import java.util.LinkedList;

public class PositionsData {
    private final WorldMap worldMap;
    private HashMap<Vector2d, Integer> animalPositions; // nazwa do zmiany, Integer to maksymalna energia zwierzecia na danym polu
    private LinkedList<Vector2d> grassPositions;
    private LinkedList<Vector2d> lastDayDeadAnimalsPositions;

    public PositionsData(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.animalPositions = worldMap.getAnimalsPositions();
        this.grassPositions = worldMap.getGrassesPositions();
        this.lastDayDeadAnimalsPositions = worldMap.getLastDayDeadAnimalsPositions();
    }

    public HashMap<Vector2d, Integer> getAnimalPositions() {
        return animalPositions;
    }
    public LinkedList<Vector2d> getGrassPositions() {
        return grassPositions;
    }

    public LinkedList<Vector2d> getLastDayDeadAnimalsPositions() {
        return lastDayDeadAnimalsPositions;
    }
}
