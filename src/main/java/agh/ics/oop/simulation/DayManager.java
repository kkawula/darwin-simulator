package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.RandomPositionGenerator;

import java.lang.reflect.Array;
import java.util.*;

public class DayManager {

    private final LinkedList<Animal> listOfAnimals = new LinkedList<>();

    private final int initialPlants;

    private final int initialAnimals;

    private final int initialAnimalEnergy;

    private final int parentEnergyConsumption;

    private final int genomeLength;

    private final int plantEnergy;

    private final int plantsPerDay;

    public DayManager(int initialPlants, int initialAnimals, int initialAnimalEnergy,
                      int parentEnergyConsumption, int genomeLength, int plantEnergy,
                      int plantsPerDay)
    {
        this.initialPlants = initialPlants;
        this.initialAnimals = initialAnimals;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.parentEnergyConsumption = parentEnergyConsumption;
        this.genomeLength = genomeLength;
        this.plantEnergy = plantEnergy;
        this.plantsPerDay = plantsPerDay;
    }
    public void initializeFirstDay(WorldMap worldMap)
    {
        int width= worldMap.getWidth();
        int height = worldMap.getHeight();
        placeAnimals(worldMap.getAnimals(),width,height);
        placeGrasses(worldMap.getGrasses(),width,height);
    }

    private void placeAnimals(HashMap<Vector2d,TreeSet<Animal>> animals, int width, int height)
    {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(initialAnimals,width,height);
        for (Vector2d position : randomPositionGenerator)
        {
            Animal animal = new Animal(position,initialAnimalEnergy,genomeLength);
            animals.get(position).add(animal);
        }
    }
    private void placeGrasses(LinkedList<Grass> grasses, int width, int height)
    {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(initialAnimals,width,height);
        for (Vector2d position : randomPositionGenerator)
        {
            grasses.add(new Grass(position));
        }
    }
    public void updateDay(WorldMap map)
    {
        HashMap<Vector2d,TreeSet<Animal>> animals=map.getAnimals();
        LinkedList<Grass> grasses=map.getGrasses();
        removeDeadAnimals(animals);
        moveAnimals(animals);
        eatGrass(grasses,animals);
        reproduceAnimals(animals);
        growGrass(grasses);
    }
    private void removeDeadAnimals(HashMap<Vector2d,TreeSet<Animal>> animals)
    {
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet())
            entry.getValue().removeIf(animal -> animal.getEnergy() <= 0);
    }
    private void eatGrass(LinkedList<Grass> grasses,HashMap<Vector2d,TreeSet<Animal>> animals)
    {
        Iterator<Grass> iterator = grasses.iterator();
        while(iterator.hasNext()) {
            Vector2d currentGrassPosition = iterator.next().getPosition();
            if(!animals.get(currentGrassPosition).isEmpty()) {
                Animal animal = animals.get(currentGrassPosition).pollLast();
                Objects.requireNonNull(animal).eatGrass(plantEnergy);
                animals.get(animal.getPosition()).add(animal);
                iterator.remove();
            }
        }
    }

    private void growGrass(LinkedList<Grass> grasses, int width, int height)
    {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(plantsPerDay,width,height,grasses);
        for (Vector2d position : randomPositionGenerator)
        {
            grasses.add(new Grass(position));
        }
    }
    private void moveAnimals(HashMap<Vector2d, TreeSet<Animal>> animals)
    {
        LinkedList<Animal> copyOfValues = new LinkedList<>();
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet())
        {
            for (Animal animal : entry.getValue()) {
                copyOfValues.add(entry.getValue().pollLast());
            }
        }
        for (Animal animal:copyOfValues)
        {
            animal.move();
            animals.get(animal.getPosition()).add(animal);
        }

    }

    private void reproduceAnimals(HashMap<Vector2d, TreeSet<Animal>> animals)
    {
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet())
        {
            if(entry.getValue().size()>=2)
            {
                Vector2d position = entry.getKey();
                Animal father = entry.getValue().pollLast();
                Animal mother = entry.getValue().pollLast();
                if(mother.getEnergy()>=parentEnergyConsumption)
                {
                    Animal child=father.reproduce(mother, initialAnimalEnergy);
                    animals.get(position).add(child);
                    animals.get(position).add(father);
                    animals.get(position).add(mother);
                }
            }
        }
    }
}
