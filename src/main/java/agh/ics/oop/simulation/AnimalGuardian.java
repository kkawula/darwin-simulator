package agh.ics.oop.simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.BehaviorVariant;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.RandomPositionGenerator;

import java.util.Set;
import java.util.stream.Collectors;

public class AnimalGuardian {

    private final WorldMap worldMap;

    private final int initialAnimals;

    private final int initialAnimalEnergy;

    private final int genomeLength;

    private final int plantEnergy;

    private final int movingCost;

    private final int minEnergyToReproduce;

    private final int parentEnergyConsumption;
    private final BehaviorVariant behaviorVariant;

    public AnimalGuardian(WorldMap worldMap,int initialAnimals,int initialAnimalEnergy,int genomeLength,BehaviorVariant behaviorVariant,int movingCost,int plantEnergy,int minEnergyToReproduce,int parentEnergyConsumption)
    {
        this.worldMap = worldMap;
        this.initialAnimals = initialAnimals;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.genomeLength = genomeLength;
        this.behaviorVariant = behaviorVariant;
        this.movingCost = movingCost;
        this.plantEnergy = plantEnergy;
        this.minEnergyToReproduce = minEnergyToReproduce;
        this.parentEnergyConsumption = parentEnergyConsumption;
    }
    public void initializeAnimals()
    {
        RandomPositionGenerator animalPositions = new RandomPositionGenerator(initialAnimals,worldMap.getWidth(),worldMap.getHeight());
        for(Vector2d position : animalPositions)
        {
            Animal animal = new Animal(position, initialAnimalEnergy, genomeLength, behaviorVariant);
            worldMap.getAnimals().get(position).add(animal);
            worldMap.getAliveAnimals().add(animal);
        }
    }
    public void removeDeadAnimals()
    {
        for(Animal animal : worldMap.getAliveAnimals())
            if(animal.getEnergy()<0)
            {
                worldMap.getDeadAnimals().add(animal);
                worldMap.getAnimals().get(animal.getPosition()).remove(animal);
            }

        worldMap.getAliveAnimals().removeIf(animal -> animal.getEnergy()<0);
    }
    public void moveAnimals()
    {
        for(Animal animal : worldMap.getAliveAnimals())
        {
            worldMap.getAnimals().get(animal.getPosition()).remove(animal);
            animal.move(worldMap, movingCost);
            worldMap.getAnimals().get(animal.getPosition()).add(animal);
        }
    }
    public void eatGrass() {
        for (Grass grass : worldMap.getGrasses())
            if (!worldMap.getAnimals().get(grass.getPosition()).isEmpty())
                worldMap.getAnimals().get(grass.getPosition()).first().eatGrass(plantEnergy);
        worldMap.getGrasses().removeIf(grass -> !worldMap.getAnimals().get(grass.getPosition()).isEmpty());
    }
    public void reproduceAnimals()
    {
        Set<Vector2d> potentialPositions = worldMap.getAliveAnimals().stream().map(Animal::getPosition).collect(Collectors.toSet());
        for(Vector2d position : potentialPositions)
        {
            if(worldMap.getAnimals().get(position).size()>=2)
            {
                Animal father = worldMap.getAnimals().get(position).pollLast();
                Animal mother = worldMap.getAnimals().get(position).pollLast();
                if(mother.getEnergy()>=minEnergyToReproduce)
                {
                    Animal child = new Animal(position,parentEnergyConsumption,father,mother,behaviorVariant);
                    worldMap.getAnimals().get(position).add(child);
                    worldMap.getAliveAnimals().add(child);
                }
                worldMap.getAnimals().get(position).add(mother);
                worldMap.getAnimals().get(position).add(father);
            }
        }
    }
}
