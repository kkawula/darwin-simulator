package agh.ics.oop.simulation;


import agh.ics.oop.utils.ConfigurationData;


public class DayManager {

    private final GrassDealer grassDealer;
    private final AnimalGuardian animalGuardian;
    private final WorldMap worldMap;

    public DayManager(ConfigurationData config, WorldMap worldMap) {
        this.worldMap = worldMap;
        this.grassDealer = switch (config.growthVariant())
        {
            case FORESTED_EQUATOR -> new ForestedEquatorGrassDealer(worldMap, config.plantsPerDay(), config.initialPlants());
            case LIFE_GIVING_CORPSES -> new LifeGivingCorpsesGrassDealer(worldMap, config.plantsPerDay(), config.initialPlants());
        };
        this.animalGuardian = new AnimalGuardian(worldMap, config.initialAnimals(), config.initialAnimalEnergy(),
                config.genomeLength(), config.behaviorVariant(), config.movingCost(),
                config.plantEnergy(), config.minEnergyToReproduce(), config.parentEnergyConsumption(),
                config.minMutations(), config.maxMutations());
    }

    public void initializeFirstDay() {
        grassDealer.initializeGrass();
        animalGuardian.initializeAnimals();
    }

    public void updateDay() {
        animalGuardian.moveAnimals();
        animalGuardian.eatGrass();
        animalGuardian.reproduceAnimals();
        animalGuardian.removeDeadAnimals();
        grassDealer.spawnGrass();
        worldMap.increaseWorldLifespan();
    }
}
