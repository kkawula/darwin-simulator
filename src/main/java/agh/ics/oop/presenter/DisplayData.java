package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.simulation.WorldMap;

public class DisplayData {

    private final WorldMap worldMap;
    private final Animal animal;
    private SimulationStats simulationStats;
    private AnimalStats animalStats;
    private PositionsData positionsData;

    public DisplayData(WorldMap worldMap, Animal animal) {
        this.worldMap = worldMap;
        this.animal = animal;
        createData();
    }

    private void createData() {
        SimulationStats simulationStats = new SimulationStats(worldMap);
        PositionsData positionsData = new PositionsData(worldMap);
        if (animal != null) {
            AnimalStats animalStats = new AnimalStats(animal);
        }
    }

    public SimulationStats getSimulationStats() {
        return simulationStats;
    }
    public AnimalStats getAnimalStats() {
        // mozna rzucic wyjatek jesli animal == null
        // jakis fancy wyjatek
        return animalStats;
    }

    public PositionsData getPositionsData() {
        return positionsData;
    }
}
