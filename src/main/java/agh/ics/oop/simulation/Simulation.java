package agh.ics.oop.simulation;

import agh.ics.oop.utils.ConfigurationData;

public class Simulation {

    private int numberOfAnimals;

    private final DayManager dayManager;

    private final WorldMap worldMap;

    public Simulation(ConfigurationData cfg) {
        worldMap = new WorldMap(cfg.getMapWidth(), cfg.getMapHeight());
        dayManager = new DayManager(cfg.getInitialPlants(), cfg.getInitialAnimals(),
                cfg.getInitialAnimalEnergy(), cfg.getParentEnergyConsumption(),
                cfg.getGenomeLength(),cfg.getPlantEnergy(), cfg.getPlantsPerDay(), cfg.getBehaviorVariant());
        dayManager.initializeFirstDay(worldMap);
    }
    public void run() {
        dayManager.updateDay(worldMap);
    }

}
