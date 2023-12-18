package agh.ics.oop.model;

public class Animal {
    private Vector2d position;
    private Animal father;
    private Animal mother;

    private int energy;
    private int age = 0;
    private int children = 0;
    private int offspring = 0;
    private int grassEaten = 0;
    private boolean isDead = false;
    private int genomeLength;

    private Genome genome;

    public Genome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

        this.genome = new Genome(father.getEnergy(), mother.getEnergy(), father.getGenome(), mother.getGenome());
    }

    public void updateChildren() {
        children++;
        offspring++;

        if (father != null) {
            father.updateOffSpring();
        }
        if (mother != null) {
            mother.updateOffSpring();
        }

    }

    public void updateOffSpring() {
        offspring++;

        if (father != null) {
            father.updateOffSpring();
        }
        if (mother != null) {
            mother.updateOffSpring();
        }
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return position.toString();
    }

}
