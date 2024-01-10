package agh.ics.oop.simulation;


import agh.ics.oop.utils.ConfigurationData;


public class DayManager {

    private final GrassDealer grassDealer;
    private final AnimalGuardian animalGuardian;

    public DayManager(ConfigurationData config, WorldMap worldMap) {
        this.grassDealer = switch (config.getGrowthVariant())
        {
            case FORESTED_EQUATOR -> new ForestedEquatorGrassDealer(worldMap, config.getPlantsPerDay(), config.getInitialPlants());
            case LIFE_GIVING_CORPSES -> new LifeGivingCorpsesGrassDealer(worldMap, config.getPlantsPerDay(), config.getInitialPlants());
        };
        this.animalGuardian = new AnimalGuardian(worldMap, config.getInitialAnimals(), config.getInitialAnimalEnergy(), config.getGenomeLength(), config.getBehaviorVariant(), config.getMovingCost(), config.getPlantEnergy(), config.getMinEnergyToReproduce(), config.getParentEnergyConsumption());
    }

    public void initializeFirstDay() {
        grassDealer.initializeGrass();
        animalGuardian.initializeAnimals();
    }

    public void updateDay() {
        grassDealer.spawnGrass();
        animalGuardian.removeDeadAnimals();
        animalGuardian.moveAnimals();
        animalGuardian.eatGrass();
        animalGuardian.reproduceAnimals();
    }
}
