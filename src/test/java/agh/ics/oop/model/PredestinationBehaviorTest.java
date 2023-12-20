package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PredestinationBehaviorTest {

    @Test
    void geneBehavior() {
        Animal animal = new Animal(new Vector2d(0,0), 30, 4);
        animal.setBehavior(new PredestinationBehavior());
        int genomeLength = animal.getGenome().getLength();

        for (int i = 0; i < 2 * genomeLength; i++){
            System.out.println(animal.activeGene);
            animal.performGeneBehavior();
        }
    }

}