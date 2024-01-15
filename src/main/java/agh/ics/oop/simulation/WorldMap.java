package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;


import java.util.*;
import java.util.stream.Collectors;


public class WorldMap implements MoveValidator{

    private int worldLifespan = 0;
    private final int width;

    private final int height;

    private final HashMap<Vector2d, TreeSet<Animal>> animals = new HashMap<>();

    private final LinkedList<Animal> aliveAnimals = new LinkedList<>();

    private final LinkedList<Animal> deadAnimals = new LinkedList<>();

    private final LinkedList<Vector2d> lastDayDeadAnimalsPositions = new LinkedList<>();

    private final LinkedList<Grass> grasses = new LinkedList<>();
    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        initializeHashMap(width, height);
    }
    private void initializeHashMap(int width, int height) {
        for (int i = 0; i < width * height; i++) {
            animals.put(new Vector2d(i % width,i / width), new TreeSet<>());
        }
    }

    public void clearLastDayDeadAnimalsPositions() {
        lastDayDeadAnimalsPositions.clear();
    }
    public LinkedList<Animal> getAliveAnimals(){
        return aliveAnimals;
    }
    public LinkedList<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    synchronized public HashMap<Vector2d, TreeSet<Animal>> getAnimals() {
        return animals;
    }

    public HashMap<Vector2d, Integer> getAnimalsPositions() {
        HashMap<Vector2d, Integer> positions = new HashMap<>();
        for (Vector2d position : animals.keySet()) {
            if (!animals.get(position).isEmpty()) {
                positions.put(position, animals.get(position).last().getEnergy());
            }
        }
        return positions;
    }
    public Set<Vector2d> animalsOccupiedPositions(){
        return getAliveAnimals()
                .stream()
                .map(Animal::getPosition)
                .collect(Collectors.toSet());
    }
    public LinkedList<Grass> getGrasses() {
        return grasses;
    }

    public LinkedList<Vector2d> getGrassesPositions() {
        return grasses.stream()
                .map(Grass::getPosition)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Vector2d> getLastDayDeadAnimalsPositions() {
        return lastDayDeadAnimalsPositions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWorldLifespan() {
        return worldLifespan;
    }

    public void increaseWorldLifespan() {
        worldLifespan++;
    }

    public Animal getLastAnimal(Vector2d position) {
        return animals.get(position).last();
    }

    @Override
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