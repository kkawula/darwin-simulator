package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

public class PositionsData {
    private final WorldMap worldMap;
    private final LinkedList<Animal> aliveAnimals;
    private HashMap<Vector2d, Integer> animalPositions; // nazwa do zmiany, Integer to maksymalna energia zwierzecia na danym polu
    private LinkedList<Vector2d> grassPositions;
    private LinkedList<Vector2d> lastDayDeadAnimalsPositions;
    private LinkedList<Vector2d> theMostPopularGenesPositions;

    public PositionsData(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.aliveAnimals = worldMap.getAliveAnimals();
        this.animalPositions = worldMap.getAnimalsPositions();
        this.grassPositions = worldMap.getGrassesPositions();
        this.lastDayDeadAnimalsPositions = worldMap.getLastDayDeadAnimalsPositions();
        this.theMostPopularGenesPositions = updateTheMostPopularGenesPositions();
    }

    private LinkedList<Vector2d> updateTheMostPopularGenesPositions() {
        LinkedList<Vector2d> theMostPopularGenesPositions = new LinkedList<>();
        Genome theMostPopularGenes = null;
        int maximum = 0;
        HashMap<Genome, Integer> counterMap = new HashMap<>();
        aliveAnimals.forEach(animal1 -> counterMap.put(animal1.getGenome(),0));
        aliveAnimals.forEach(animal1 -> counterMap.put(animal1.getGenome(), counterMap.get(animal1.getGenome())+1));
        for (Genome genome : counterMap.keySet()) {
            if (counterMap.get(genome) > maximum) {
                maximum = counterMap.get(genome);
                theMostPopularGenes = genome;
            }
        }
        Genome finalTheMostPopularGenes = theMostPopularGenes;
        aliveAnimals.forEach(animal -> {
            if (animal.getGenome().equals(finalTheMostPopularGenes)) {
                theMostPopularGenesPositions.add(animal.getPosition());
            }
        });

        return theMostPopularGenesPositions;
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

    public LinkedList<Vector2d> getTheMostPopularGenesPositions() {
        return theMostPopularGenesPositions;
    }
}
