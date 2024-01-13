package agh.ics.oop.model;

import agh.ics.oop.simulation.MoveValidator;


import java.util.*;


public class Animal implements Comparable<Animal> {

    public UUID ID = UUID.randomUUID();
    private Vector2d position;
    private Animal father;
    private Animal mother;
    private int energy;
    private int age = 0;
    private final int birthDay = 0;

    private int deathDay = 0;
    private boolean isDead = false;
    private final LinkedList<Animal> children = new LinkedList<>();
    private int grassEaten = 0;
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
        father.children.add(this);
        mother.children.add(this);
        this.genome = new Genome(father.getEnergy(), mother.getEnergy(), father.getGenome(), mother.getGenome());
    }

    public void performGeneBehavior() {
        behavior.geneBehavior(this);
    }

    public int getChildren() {
        return children.size();
    }

    public int getOffspring() {
        HashSet<Animal> visitedAnimals = new HashSet<>();
        return getOffspringHelper(visitedAnimals);
    }
    public int getOffspringHelper(HashSet<Animal> visitedAnimals)
    {
        int numberOfChild=0;
        for(Animal animal : children)
        {
            if(!visitedAnimals.contains(animal))
            {
                visitedAnimals.add(animal);
                numberOfChild+=(animal.getOffspringHelper(visitedAnimals)+1);
            }
        }
        return numberOfChild;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public String getActiveGene() {
        return genome.getGene(activeGene).toString();
    }

    public int getAge(){return age;}

    public int getBirthDay(){return birthDay;}

    public int getGrassEaten(){return grassEaten;}

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public Vector2d getPosition() {
        return position;
    }


    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        isDead = true;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
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

    public void subtractEnergy(int energy) {
        this.energy -= energy;
    }
    @Override
    public int compareTo(Animal other) {
        return Comparator.comparing(Animal::getEnergy)
                .thenComparing(Animal::getAge)
                .thenComparing(Animal::getChildren)
                .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(mother, animal.mother) && Objects.equals(father, animal.father) && Objects.equals(age,animal.age) && Objects.equals(ID, animal.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(father, mother, age, ID);
    }
}
