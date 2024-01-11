package agh.ics.oop.utils;

import agh.ics.oop.model.BehaviorVariant;
import agh.ics.oop.model.GrowthVariant;

public class ConfigurationData {
    private final int mapHeight;
    private final int mapWidth;
    private final int initialPlants;
    private final int plantEnergy;
    private final int plantsPerDay;
    private final GrowthVariant growthVariant;
    private final int initialAnimals;
    private final int initialAnimalEnergy;
    private final int minEnergyToReproduce;
    private final int parentEnergyConsumption;
    private final int minMutations;
    private final int maxMutations;
    private final int genomeLength;
    private final BehaviorVariant behaviorVariant;
    private final int movingCost;
    private final int csvWriting;

    public ConfigurationData(int mapHeight, int mapWidth, int initialPlants, int plantEnergy, int plantsPerDay, GrowthVariant growthVariant, int initialAnimals, int initialAnimalEnergy, int minEnergyToReproduce, int parentEnergyConsumption, int minMutations, int maxMutations, int genomeLength, BehaviorVariant behaviorVariant, int movingCost, int csvWriting) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.initialPlants = initialPlants;
        this.plantEnergy = plantEnergy;
        this.plantsPerDay = plantsPerDay;
        this.growthVariant = growthVariant;
        this.initialAnimals = initialAnimals;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.minEnergyToReproduce = minEnergyToReproduce;
        this.parentEnergyConsumption = parentEnergyConsumption;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genomeLength = genomeLength;
        this.behaviorVariant = behaviorVariant;
        this.movingCost = movingCost;
        this.csvWriting = csvWriting;
    }
    public int getMapHeight() { return mapHeight; }

    public int getMapWidth() { return mapWidth; }

    public int getInitialPlants() {
        return initialPlants;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getPlantsPerDay() {
        return plantsPerDay;
    }

    public GrowthVariant getGrowthVariant() {
        return growthVariant;
    }

    public int getInitialAnimals() {
        return initialAnimals;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public int getMinEnergyToReproduce() {
        return minEnergyToReproduce;
    }

    public int getParentEnergyConsumption() {
        return parentEnergyConsumption;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public BehaviorVariant getBehaviorVariant() {
        return behaviorVariant;
    }

    public int getMovingCost() {
        return movingCost;
    }

    public int getCsvWriting() { return csvWriting; }
}

