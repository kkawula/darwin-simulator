package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.ConfigurationData;

import java.util.*;

public class WorldMap {

    private final int width;

    private final int height;

    private final int plantEnergy;

    private final HashMap<Vector2d, TreeSet<Animal>> animals= new HashMap<>();

    private final LinkedList<Grass> grasses= new LinkedList<>();
    public WorldMap(int width, int height, int plantEnergy) {
        this.width=width;
        this.height=height;
        this.plantEnergy=plantEnergy;
        initializeHashMap(width,height);
    }
    private void initializeHashMap(int width, int height)
    {
        for (int i = 0; i < width*height; i++) {

            animals.put(new Vector2d(i%width,i/width),new TreeSet<Animal>());
        }
    }
    private void growGrass()
    {

    }
    public HashMap<Vector2d, TreeSet<Animal>> getAnimals()
    {
        return animals;
    }
    public LinkedList<Grass> getGrasses()
    {
        return grasses;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }



}