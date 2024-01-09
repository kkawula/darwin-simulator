package agh.ics.oop.model;

import agh.ics.oop.simulation.MoveValidator;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;


public class Animal implements Comparable<Animal> {
    private Vector2d position;
    private Animal father;
    private Animal mother;
    private int energy;
    private int age = 0;
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


    public Animal (Vector2d newPosition, int energy, int genomeLength, BehaviorVariant behaviorVariant) {
        this.position = newPosition;
        this.energy = energy;
        this.genomeLength = genomeLength;
        behavior = switch (behaviorVariant)
        {
            case TRAVERSAL_BEHAVIOR -> new TraversalBehavior();
            case PREDESTINATION_BEHAVIOR -> new PredestinationBehavior();
        };
        this.genome = new Genome(genomeLength);
    }

    public Animal(Vector2d newPosition, int energy, Animal father, Animal mother, BehaviorVariant behaviorVariant) {
        position = newPosition;
        this.energy = energy;
        this.father = father;
        this.mother = mother;

        behavior = switch (behaviorVariant)
        {
            case TRAVERSAL_BEHAVIOR -> new TraversalBehavior();
            case PREDESTINATION_BEHAVIOR -> new PredestinationBehavior();
        };

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

    public int getAge(){return age;}

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return position.toString();
    }

    public void eatGrass(int plantEnergy) {
        energy+=plantEnergy;
    }

    public void move(MoveValidator moveValidator) {
        age++;
        position = moveValidator.newPosition(position, genome.getGene(activeGene));
        this.performGeneBehavior();
    }
    @Override
    public int compareTo(Animal other) {
        return Comparator.comparing(Animal::getEnergy)
                .thenComparing(Animal::getAge)
                .thenComparing(Animal::getOffspring)
                .compare(this, other);
    }


}
