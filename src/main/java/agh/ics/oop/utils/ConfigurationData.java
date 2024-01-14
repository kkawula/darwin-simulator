package agh.ics.oop.utils;

import agh.ics.oop.model.BehaviorVariant;
import agh.ics.oop.model.GrowthVariant;

public record ConfigurationData(
    int mapHeight,
    int mapWidth,
    int initialPlants,
    int plantEnergy,
    int plantsPerDay,
    GrowthVariant growthVariant,
    int initialAnimals,
    int initialAnimalEnergy,
    int minEnergyToReproduce,
    int parentEnergyConsumption,
    int minMutations,
    int maxMutations,
    int genomeLength,
    BehaviorVariant behaviorVariant,
    int movingCost,
    int csvWriting,
    long refreshTime
) {}


