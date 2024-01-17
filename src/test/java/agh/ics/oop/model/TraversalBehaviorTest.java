package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

class TraversalBehaviorTest {

    @Test
    void geneBehavior() {
        Animal animal = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.TRAVERSAL_BEHAVIOR);
        int genomeLength = animal.getGenome().getLength();

        for (int i = 0; i < 2 * genomeLength - 1; i++){
            System.out.println(animal.activeGene);
            animal.performGeneBehavior();
        }
    }
}