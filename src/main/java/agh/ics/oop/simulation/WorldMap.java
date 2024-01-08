package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;


import java.util.*;

import static java.lang.Math.max;

public class WorldMap implements MoveValidator{

    private final int width;

    private final int height;

    private HashMap<Vector2d, TreeSet<Animal>> animals = new HashMap<>();

    private LinkedList<Animal> deadAnimals = new LinkedList<>();

    private final LinkedList<Grass> grasses= new LinkedList<>();
    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        initializeHashMap(width, height);
    }
    private void initializeHashMap(int width, int height) {
        for (int i = 0; i < width*height; i++) {

            animals.put(new Vector2d(i % width,i / width), new TreeSet<>());
        }
    }

    public LinkedList<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public HashMap<Vector2d, TreeSet<Animal>> getAnimals() {
        return animals;
    }

    public HashMap<Vector2d, Integer> getAnimalsPositions() {
        HashMap<Vector2d, Integer> positions = new HashMap<>();
        for (Vector2d position : animals.keySet()) {
            if (!animals.get(position).isEmpty()) {
                positions.put(position, animals.get(position).size());
            }
        }
        return positions;
    }

    public LinkedList<Grass> getGrasses() {
        return grasses;
    }

    public LinkedList<Vector2d> getGrassesPositions() {
        LinkedList<Vector2d> positions = new LinkedList<>();
        for (Grass grass : grasses) {
            positions.add(grass.getPosition());
        }

        return positions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d newPosition(Vector2d position, MapDirection mapDirection)
    {
        int newX =(position.getX() + mapDirection.toUnitVector().getX()) >= 0 ?
                (position.getX() + mapDirection.toUnitVector().getX()) % width : width - 1;
        int newY = position.getY() + mapDirection.toUnitVector().getY();
        if(newY < 0 || newY >= height)
        {
            return new Vector2d(newX, position.getY());
        }
        else
        {
            return new Vector2d(newX, newY);

        }
    }
}