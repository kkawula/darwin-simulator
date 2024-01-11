package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StatsWriter {

    private WorldMap worldMap;
    private int worldLifespan;
    private int freeFields;
    private int animalsAlive;
    private int animalsDead;
    private int plants;
    private int averageEnergy;
    private int averageLifeLength;
    private int averageChildrenNumber;

    private int grass;

    // data for tracking exact animal

    private Animal animal;

    private boolean isFollowed = false;
    private int brithday;
    private int age;
    private Genome genome;
    private String activeGene;
    private int energy;
    private int eatenPlants;
    private int children;
    private int descendants;
    private Vector2d position;

    public StatsWriter(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void updateStats() {
        updateFreeFields();
        updateAnimalsAlive();
        updateAnimalsDead();
        updatePlants();
        updateAverageEnergy();
        updateAverageLifeLength();
        updateAverageChildrenNumber();
        updateWorldLifespan();
        updateGrass();

        if (isFollowed) {
            updateFollowedAnimalStats();
        }
    }

    private void updateGrass() {
        grass = worldMap.getGrasses().size();
    }

    private void updateFreeFields() {
        Set<Vector2d> occupiedFields = new HashSet<>(Collections.emptySet());

        Set<Vector2d> animalsPositions = worldMap.getAnimals().keySet();
        Set<Vector2d> grassPositions = new HashSet<>(worldMap.getGrassesPositions());

        occupiedFields.addAll(animalsPositions);
        occupiedFields.addAll(grassPositions);

        freeFields = worldMap.getWidth() * worldMap.getHeight() - occupiedFields.size();
    }

    private void updateAnimalsAlive() {
        animalsAlive = worldMap.getAliveAnimals().size();
    }

    private void updateAnimalsDead() {
        animalsDead = worldMap.getDeadAnimals().size();
    }

    private void updatePlants() {
        plants = worldMap.getGrasses().size();
    }

    private void updateAverageEnergy() {
        if (worldMap.getAliveAnimals().size() == 0) {
            averageEnergy = 0;
        }
        else{
            averageEnergy = worldMap.getAliveAnimals().stream().mapToInt(animal -> animal.getEnergy()).sum() / worldMap.getAliveAnimals().size();
        }
    }

    private void updateAverageLifeLength() {
        if (worldMap.getDeadAnimals().size() == 0) {
            averageLifeLength = 0;
        }
        else {
            averageLifeLength = worldMap.getDeadAnimals().stream().mapToInt(animal -> animal.getAge()).sum() / worldMap.getDeadAnimals().size();
        }
    }

    private void updateAverageChildrenNumber() {
        averageChildrenNumber = 0;
        if (worldMap.getDeadAnimals().size() != 0) {
            averageChildrenNumber = worldMap.getDeadAnimals().stream().mapToInt(animal -> animal.getChildren()).sum();
        }
        if (worldMap.getAliveAnimals().size() != 0) {
            averageChildrenNumber += worldMap.getAliveAnimals().stream().mapToInt(animal -> animal.getChildren()).sum();
        }
    }

    private void updateWorldLifespan() {
        worldLifespan = worldMap.getWorldLifespan();
    }

    public void setAnimal (Animal animal) {
        this.animal = animal;
        isFollowed = true;
    }

    public void unFollowAnimal() {
        this.animal = null;
        isFollowed = false;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void updateFollowedAnimalStats() {
        energy = animal.getEnergy();
        brithday = animal.getBirthDay();
        age = animal.getAge();
        genome = animal.getGenome();
        activeGene = animal.getActiveGene();
        eatenPlants = animal.getGrassEaten();
        descendants = animal.getOffspring();
        position = animal.getPosition();
        children = animal.getChildren();
        if (animal.isDead()) {
            isFollowed = false;
        }
    }

    public int getFreeFields() {
        return freeFields;
    }

    public int getChildren() {
        return children;
    }

    public int getAnimalsAlive() {
        return animalsAlive;
    }

    public int getAnimalsDead() {
        return animalsDead;
    }

    public int getPlants() {
        return plants;
    }

    public int getAverageEnergy() {
        return averageEnergy;
    }

    public int getAverageLifeLength() {
        return averageLifeLength;
    }

    public int getAverageChildrenNumber() {
        return averageChildrenNumber;
    }

    public int getBrithday() {
        return brithday;
    }

    public int getAge() {
        return age;
    }

    public Genome getGenome() {
        return genome;
    }

    public String getActiveGene() {
        return activeGene;
    }

    public int getEnergy() {
        return energy;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public int getDescendants() {
        return descendants;
    }

    public int getWorldLifespan() {
        return worldLifespan;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getGrass() {
        return grass;
    }
}
