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
    private final int birthDay;
    private int deathDay = 0;
    private boolean isDead = false;
    private final LinkedList<Animal> children = new LinkedList<>();
    private int grassEaten = 0;
    private int genomeLength;
    public int activeGene = 0;
    private final Genome genome;
    public Behavior behavior;


    public Animal (Vector2d newPosition, int initialEnergy, int genomeLength, BehaviorVariant behaviorVariant) {
        position = newPosition;
        energy = initialEnergy;
        this.genomeLength = genomeLength;
        birthDay = 0;
        behavior = switch (behaviorVariant)
        {
            case TRAVERSAL_BEHAVIOR -> new TraversalBehavior();
            case PREDESTINATION_BEHAVIOR -> new PredestinationBehavior();
        };
        this.genome = new Genome(genomeLength);
    }

    public Animal(Vector2d newPosition, int energy, int birthDay, Animal father, Animal mother, Behavior behaviorVariant) {
        position = newPosition;
        this.energy = energy;
        this.father = father;
        this.mother = mother;
        this.birthDay = birthDay;
        behavior = behaviorVariant;
        this.genome = new Genome(father.getEnergy(), mother.getEnergy(), father.getGenome(), mother.getGenome());
    }
    public static Animal reproduce(Animal father, Animal mother, int parentEnergyConsumption, int birthDay)
    {

        Animal child = new Animal(father.position, parentEnergyConsumption, birthDay, father, mother, father.behavior);
        father.children.add(child);
        mother.children.add(child);
        father.energy-=parentEnergyConsumption;
        mother.energy-=parentEnergyConsumption;
        return child;
    }
    public void performGeneBehavior() {
        behavior.geneBehavior(this);
    }

    public int getChildren() {
        return children.size();
    }

    synchronized public int getOffspring() {
        HashSet<Animal> visitedAnimals = new HashSet<>();
        LinkedList<Animal> stack = new LinkedList<>();
        for(Animal animal : children)
        {
            stack.push(animal);
            visitedAnimals.add(animal);
        }
        int numberOfOffspring = 0;
        while(!stack.isEmpty())
        {
            Animal animal = stack.pop();
            visitedAnimals.add(animal);
            numberOfOffspring += 1;
            for(Animal hisChild : animal.children)
            {
                if(!visitedAnimals.contains(hisChild))
                    stack.push(hisChild);
            }
        }
        return numberOfOffspring;
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
        grassEaten++;
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

    public int getBirthday() {
        return birthDay;
    }

    public int getEatenPlants() {
        return grassEaten;
    }
}
