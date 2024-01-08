package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.RandomPositionGenerator;

import java.util.*;

public class DayManager {

    private final GrassDealer grassDealer = null;
    private final int initialPlants;
    private final int initialAnimals;
    private final int initialAnimalEnergy;
    private final int parentEnergyConsumption;
    private final int genomeLength;
    private final int plantEnergy;
    private final int plantsPerDay;
    private final int behaviorVariant;
    private final int minEnergyToReproduce;
    private final int movingCost;

    public DayManager(ConfigurationData config) {
        this.initialPlants = config.getInitialPlants();
        this.initialAnimals = config.getInitialAnimals();
        this.initialAnimalEnergy = config.getInitialAnimalEnergy();
        this.parentEnergyConsumption = config.getParentEnergyConsumption();
        this.genomeLength = config.getGenomeLength();
        this.plantEnergy = config.getPlantEnergy();
        this.plantsPerDay = config.getPlantsPerDay();
        this.behaviorVariant = config.getBehaviorVariant();
        this.minEnergyToReproduce = config.getMinEnergyToReproduce();
        this.movingCost = config.getMovingCost();
        System.out.println((config.getGrowthVariant()));
        System.out.println((config.getBehaviorVariant()));
    }
    public void initializeFirstDay(WorldMap worldMap)
    {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        placeAnimals(worldMap.getAnimals(), width, height);
        placeGrasses(worldMap.getGrasses(), width, height);
    }

    private void placeAnimals(HashMap<Vector2d,TreeSet<Animal>> animals, int width, int height) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(initialAnimals, width, height);

        for (Vector2d position : randomPositionGenerator) {
            Animal animal = new Animal(position, initialAnimalEnergy, genomeLength, behaviorVariant);
            animals.get(position).add(animal);
        }
    }

    private void placeGrasses(LinkedList<Grass> grasses, int width, int height) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(initialPlants, width, height);

        for (Vector2d position : randomPositionGenerator) {
            grasses.add(new Grass(position));
        }
    }

    public void updateDay(WorldMap map) {
        HashMap<Vector2d, TreeSet<Animal>> animals = map.getAnimals();
        LinkedList<Grass> grasses = map.getGrasses();
        LinkedList<Animal> deadAnimals = map.getDeadAnimals();

        growGrass(grasses, map.getWidth(), map.getHeight());
        removeDeadAnimals(animals, deadAnimals);
        moveAnimals(map, animals);
        eatGrass(grasses, animals);
        reproduceAnimals(animals);
    }

    private void removeDeadAnimals(HashMap<Vector2d, TreeSet<Animal>> animals, LinkedList<Animal> deadAnimals) {
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet()) {
            Iterator<Animal> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                if (animal.getEnergy() <= 0) {
                    deadAnimals.add(animal);
                    iterator.remove();
                }
            }
        }
    }

    private void eatGrass(LinkedList<Grass> grasses, HashMap<Vector2d, TreeSet<Animal>> animals) {
        Iterator<Grass> iterator = grasses.iterator();

        while(iterator.hasNext()) {
            Vector2d currentGrassPosition = iterator.next().getPosition();

            if(!animals.get(currentGrassPosition).isEmpty()) {
                Animal animal = animals.get(currentGrassPosition).pollLast();
                Objects.requireNonNull(animal).eatGrass(plantEnergy);
                animals.get(animal.getPosition()).add(animal);
                iterator.remove();
                System.out.println("Grass eaten");
            }
        }
    }

    private void growGrass(LinkedList<Grass> grasses, int width, int height)
    {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(plantsPerDay, width, height, grasses);
        for (Vector2d position : randomPositionGenerator)
        {
            grasses.add(new Grass(position));
        }
    }
    private void moveAnimals(MoveValidator moveValidator, HashMap<Vector2d, TreeSet<Animal>> animals)
    {
        LinkedList<Animal> copyOfValues = new LinkedList<>();
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet())
        {
            for(int i = 0; i < entry.getValue().size(); i++)
                copyOfValues.add(entry.getValue().pollLast());
        }
        for (Animal animal : copyOfValues)
        {
            animal.move(moveValidator);
            animal.setEnergy(animal.getEnergy() - movingCost);
            animals.get(animal.getPosition()).add(animal);
        }

    }

    private void reproduceAnimals(HashMap<Vector2d, TreeSet<Animal>> animals) {
        for (Map.Entry<Vector2d, TreeSet<Animal>> entry : animals.entrySet()) {

            Vector2d position = entry.getKey();
            LinkedList<Animal> newAnimals = new LinkedList<>();
            if(entry.getValue().size() >= 2) {

                System.out.println(entry.getValue().size());
                Animal father = entry.getValue().pollLast();
                Animal mother = entry.getValue().pollLast();

                if(mother.getEnergy() >= minEnergyToReproduce && father.getEnergy() >= minEnergyToReproduce) {

                    Animal child = new Animal(position, initialAnimalEnergy, father, mother, behaviorVariant);
                    mother.setEnergy(mother.getEnergy() - parentEnergyConsumption);
                    father.setEnergy(father.getEnergy() - parentEnergyConsumption);
                    newAnimals.add(child);


                }
                newAnimals.add(father);
                newAnimals.add(mother);
            }
            for(Animal animal : newAnimals)
            {
                animals.get(position).add(animal);
            }
        }
    }
}
