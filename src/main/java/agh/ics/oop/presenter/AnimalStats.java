package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;

public class AnimalStats {

    private final Animal animal;
    private int birthday;
    private String activeGene = "";
    private String genome = "";
    private int energy;
    private int eatenPlants;
    private int children;
    private int descendants;
    private int age;
    private String positionString = "";

    public AnimalStats(Animal animal) {
        this.animal = animal;
        updateFollowedAnimalStats();
    }
    private void updateFollowedAnimalStats() {
        birthday = animal.getBirthDay();
        activeGene = animal.getGenome().getGene(animal.activeGene).toString();
        genome = animal.getGenome().toString();
        energy = animal.getEnergy();
        eatenPlants = animal.getGrassEaten();
        children = animal.getChildren();
        descendants = animal.getOffspring();
        age = animal.getAge();
        positionString = animal.getPosition().toString();
    }

    public int getBirthday() {
        return birthday;
    }

    public String getActiveGene() {
        return activeGene;
    }

    public String getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public int getChildren() {
        return children;
    }

    public int getDescendants() {
        return descendants;
    }

    public int getAge() {
        return age;
    }

    public String getPositionString() {
        return positionString;
    }
}
