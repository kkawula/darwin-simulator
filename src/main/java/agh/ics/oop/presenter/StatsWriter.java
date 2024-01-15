package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;

import java.util.*;

public class StatsWriter {

    private final WorldMap worldMap;
    private int worldLifespan;
    private int freeFields;
    private int animalsAlive;
    private int animalsDead;
    private double averageEnergy;
    private double averageLifeLength;
    private double averageChildrenNumber;
    private Genome bestGenes;
    private int grass;
    private int maximumAnimalEnergy;

    // data for tracking exact animal

    private Animal animal;

    private int birthday;
    private String activeGene ="";

    private String genome = "";
    private int energy;
    private int eatenPlants;
    private int children;
    private int descendants;
    private int age;
    private Vector2d position;
    private String positionString = "";

    private boolean isFollowed = false;

    public int getBirthday() {
        return birthday;
    }
    public String getGenome() {
        return genome;
    }
    public String getActiveGene() {
        return activeGene;
    }
    public int getEnergy() {
        return energy;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public int getChildren() {
        return children;
    }

    public int getDescendants() {
        return descendants;
    }

    public int getAge() {
        return age;
    }

    public String getPositionString() {
        return positionString;
    }

    public StatsWriter(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void updateStats() {
        updateFreeFieldsNumber();
        updateAnimalsAlive();
        updateAnimalsDead();
        updateAverageEnergy();
        updateAverageLifeLength();
        updateAverageChildrenNumber();
        updateWorldLifespan();
        updateGrass();
        updateBestGenes();

        if (isFollowed) {
            updateFollowedAnimalStats();
            updatePosition();
        }
    }
    private void updateFollowedAnimalStats() {
        birthday = animal.getBirthDay();
        activeGene = animal.getGenome().getGene(animal.activeGene).toString();
        genome = animal.getGenome().toString();
        energy = animal.getEnergy();
        eatenPlants = animal.getGrassEaten();
        children = animal.getChildren();
        descendants = animal.getOffspring();
        age = animal.getAge();
        positionString = animal.getPosition().toString();
    }

    private void updateMaximumAnimalEnergy() {
        if (worldMap.getAliveAnimals().isEmpty())
            maximumAnimalEnergy = 0;
        else
            maximumAnimalEnergy = worldMap.getAliveAnimals().stream().mapToInt(Animal::getEnergy).max().getAsInt();
    }

    public int getMaximumAnimalEnergy() {
        return maximumAnimalEnergy;
    }

    public Vector2d getPosition() {
        return position;
    }
    private void updatePosition() {
        position = animal.getPosition();
    }

    private void updateGrass() {
        grass = worldMap.getGrasses().size();
    }

    private void updateBestGenes() {

        Map<Genome, Integer> counterMap = new HashMap<>();
        worldMap.getAliveAnimals().forEach(animal1 -> counterMap.put(animal1.getGenome(),0));
        worldMap.getAliveAnimals().forEach(animal1 -> counterMap.put(animal1.getGenome(), counterMap.get(animal1.getGenome())+1));
        int maximum = 0;
        Genome bestGenome = null;
        for(Map.Entry<Genome, Integer>  map : counterMap.entrySet())
        {
            if(map.getValue() > maximum)
            {
                maximum = map.getValue();
                bestGenome = map.getKey();
            }
        }
        this.bestGenes = bestGenome;
    }

    public String getBestGenes() {
        if (bestGenes == null) return "";
        return bestGenes.toString();
    }

    private void updateAnimalsAlive() {
        animalsAlive = worldMap.getAliveAnimals().size();
    }

    private void updateAnimalsDead() {
        animalsDead = worldMap.getDeadAnimals().size();
    }

    private void updateAverageEnergy() {
        if (worldMap.getAliveAnimals().isEmpty())
            averageEnergy = 0.0;
        else
            averageEnergy = worldMap.getAliveAnimals().stream().mapToInt(Animal::getEnergy).average().getAsDouble();
    }
    public Animal getAnimal()
    {
        return animal;
    }
    private void updateAverageLifeLength() {
        if (worldMap.getDeadAnimals().isEmpty())
            averageLifeLength = 0;
        else
            averageLifeLength = worldMap.getDeadAnimals().stream().mapToInt(Animal::getAge).average().getAsDouble();
    }

    private void updateAverageChildrenNumber() {
        if (worldMap.getAliveAnimals().isEmpty())
            averageChildrenNumber = 0;
        else averageChildrenNumber = worldMap.getAliveAnimals().stream().mapToInt(Animal::getChildren).average().getAsDouble();
    }
    private void updateFreeFieldsNumber() {
        List<Vector2d> aloneGrassPositions = worldMap.getGrassesPositions().stream().filter(Vector2d -> !worldMap.animalsOccupiedPositions().contains(Vector2d)).toList();
        freeFields = Math.max(worldMap.getHeight() * worldMap.getHeight()-(aloneGrassPositions.size() + worldMap.animalsOccupiedPositions().size()), 0);
    }
    private void updateWorldLifespan() {
        worldLifespan = worldMap.getWorldLifespan();
    }

    public void setAnimal (Vector2d vector) {
        this.animal = worldMap.getLastAnimal(vector);
        isFollowed = true;
    }

    public void unFollowAnimal() {
        this.animal = null;
        isFollowed = false;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public int getFreeFields() {
        return freeFields;
    }

    public int getAnimalsAlive() {
        return animalsAlive;
    }

    public int getAnimalsDead() {
        return animalsDead;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageLifeLength() {
        return averageLifeLength;
    }

    public double getAverageChildrenNumber() {
        return averageChildrenNumber;
    }

    public int getWorldLifespan() {
        return worldLifespan;
    }

    public int getGrass() {
        return grass;
    }
}
