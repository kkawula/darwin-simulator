package agh.ics.oop.simulation;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.utils.RandomPositionGenerator;

import java.util.LinkedList;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LifeGivingCorpsesGrassDealer implements GrassDealer{

    private final WorldMap worldMap;

    private final int plantsPerDay;

    private final int initialPlants;

    public LifeGivingCorpsesGrassDealer(WorldMap worldMap, int plantsPerDay, int initialPlants)
    {
        this.worldMap = worldMap;
        this.plantsPerDay = plantsPerDay;
        this.initialPlants = initialPlants;
    }
    @Override
    public void initializeGrass() {
        RandomPositionGenerator grassPositions = new RandomPositionGenerator(initialPlants, worldMap.getWidth(),worldMap.getHeight());
        for (Vector2d position : grassPositions) {
            worldMap.getGrasses().add(new Grass(position));
        }
    }

    @Override
    public void spawnGrass() {
        LinkedList<Vector2d> occupiedPositions = new LinkedList<>(worldMap.getGrassesPositions());

        LinkedList<Vector2d> preferredPositions = new LinkedList<>(worldMap.getLastDayDeadAnimalsPositions());

        RandomPositionGenerator grassPositions = new RandomPositionGenerator(plantsPerDay, worldMap.getWidth(),worldMap.getHeight(), preferredPositions, occupiedPositions);
        System.out.println(grassPositions);
        for (Vector2d position : grassPositions) {
            worldMap.getGrasses().add(new Grass(position));
        }
    }
}
