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

    public int getAge(){return age;}

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

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

    public void move(MoveValidator moveValidator, int movingCost) {
        age++;
        energy -= movingCost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.energy && age == animal.age && birthDay == animal.birthDay && deathDay == animal.deathDay && isDead == animal.isDead && children == animal.children && offspring == animal.offspring && grassEaten == animal.grassEaten && genomeLength == animal.genomeLength && activeGene == animal.activeGene && Objects.equals(position, animal.position) && Objects.equals(father, animal.father) && Objects.equals(mother, animal.mother) && Objects.equals(genome, animal.genome) && Objects.equals(behavior, animal.behavior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, father, mother, energy, age, birthDay, deathDay, isDead, children, offspring, grassEaten, genomeLength, activeGene, genome, behavior);
    }
}
