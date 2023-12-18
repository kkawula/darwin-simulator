package agh.ics.oop.utils;

public class ConfigurationData {
    private int mapHeight;
    private int mapWidth;
    private int initialPlants;
    private int plantEnergy;
    private int plantsPerDay;
    private int growthVariant;
    private int initialAnimals;
    private int initialAnimalEnergy;
    private int fullnessThreshold;
    private int parentEnergyConsumption;
    private int minMutations;
    private int maxMutations;
    private int genomeLength;
    private int behaviorVariant;

    public ConfigurationData(int mapHeight, int mapWidth, int initialPlants, int plantEnergy, int plantsPerDay, int growthVariant, int initialAnimals, int initialAnimalEnergy, int fullnessThreshold, int parentEnergyConsumption, int minMutations, int maxMutations, int genomeLength, int behaviorVariant) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.initialPlants = initialPlants;
        this.plantEnergy = plantEnergy;
        this.plantsPerDay = plantsPerDay;
        this.growthVariant = growthVariant;
        this.initialAnimals = initialAnimals;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.fullnessThreshold = fullnessThreshold;
        this.parentEnergyConsumption = parentEnergyConsumption;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genomeLength = genomeLength;
        this.behaviorVariant = behaviorVariant;
    }
}

