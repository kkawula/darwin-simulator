package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStats {

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

    public SimulationStats(WorldMap worldMap) {
        this.worldMap = worldMap;
        updateStats();
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
    }

    private void updateGrass() {
        grass = worldMap.getGrasses().size();
    }

    private void updateBestGenes() {

        Map<Genome, Integer> counterMap = new HashMap<>();
        worldMap.getAliveAnimals().forEach(animal1 -> counterMap.put(animal1.getGenome(), 0));
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
