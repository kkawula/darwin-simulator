package agh.ics.oop.model;

import java.util.HashSet;

public class Animal {
    private Vector2d position;
    private Animal father;
    private Animal mother;

    private int energy;
    private final int age = 0;
    private final int birthDay = 0;
    private final int deathDay = 0;
    private final boolean isDead = false;

    private int children = 0;
    private int offspring = 0;
    private final int grassEaten = 0;
    private int genomeLength;
    public int activeGene = 0;
    private final Genome genome;

    public Behavior behavior;


    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }



    public Animal (Vector2d newPosition, int energy, int genomeLength) {
        this.position = newPosition;
        this.energy = energy;
        this.genome = new Genome(genomeLength);
    }

    public Animal(Vector2d newPosition, int energy, Animal father, Animal mother) {
        position = newPosition;
        this.energy = energy;
        this.father = father;
        this.mother = mother;
        updateChildren();

        this.genome = new Genome(father.getEnergy(), mother.getEnergy(), father.getGenome(), mother.getGenome());
    }

    public void updateChildren() {
        father.setChildren(father.getChildren() + 1);
        mother.setChildren(mother.getChildren() + 1);

        HashSet<Animal> visitedParents = new HashSet<>();
        updateOffspring(visitedParents);

    }

    public void updateOffspring(HashSet<Animal> visitedParents) {

        if (father != null && !visitedParents.contains(father)) {
            father.setOffspring(father.getOffspring() + 1);
            visitedParents.add(father);
            father.updateOffspring(visitedParents);
        }
        if (mother != null && !visitedParents.contains(mother)) {
            mother.setOffspring(mother.getOffspring() + 1);
            visitedParents.add(mother);
            mother.updateOffspring(visitedParents);
        }
    }

    public void performGeneBehavior() {
        behavior.geneBehavior(this);
    }

    public int getChildren() {
        return children;
    }

    public int getOffspring() {
        return offspring;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setOffspring(int offspring) {
        this.offspring = offspring;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return position.toString();
    }

}
