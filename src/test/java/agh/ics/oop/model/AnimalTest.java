package agh.ics.oop.model;

import agh.ics.oop.simulation.WorldMap;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void updateChildren1() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        assertEquals(1, father.getChildren());
        assertEquals(1, mother.getChildren());
    }

    @Test
    void updateChildren2() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,father,30);
        assertEquals(2, father.getChildren());
        assertEquals(2, mother.getChildren());
    }
    @Test
    void updateChildren3() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal postMan = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,postMan,30);
        assertEquals(1, father.getChildren());
        assertEquals(1, postMan.getChildren());
        assertEquals(2, mother.getChildren());
    }

    @Test
    void updateOffSpring1() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        assertEquals(1, father.getOffspring());
        assertEquals(1, mother.getOffspring());
    }
    @Test
    void updateOffSpring2() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,father,30);
        assertEquals(2, father.getOffspring());
        assertEquals(2, mother.getOffspring());
    }
    @Test
    void updateOffspring3() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal postMan = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,postMan,30);
        assertEquals(1, father.getOffspring());
        assertEquals(1, postMan.getOffspring());
        assertEquals(2, mother.getOffspring());
    }

    @Test
    void updateOffspring4(){

        Animal father = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,father,30);
        Animal kid3 = Animal.reproduce(kid1,kid2,30);

        assertEquals(3, father.getOffspring());
        assertEquals(3, mother.getOffspring());
        assertEquals(1, kid1.getOffspring());
        assertEquals(1, kid2.getOffspring());
    }

    @Test
    void updateOffspring5(){

        Animal father = new Animal(new Vector2d(0,0), 100, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal mother = new Animal(new Vector2d(0,0), 100, 4, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        Animal kid1 = Animal.reproduce(mother,father,30);
        Animal kid2 = Animal.reproduce(mother,father,30);
        Animal kid3 = Animal.reproduce(kid1,kid2,30);
        Animal kid4 = Animal.reproduce(kid3,father,30);

        assertEquals(4, father.getOffspring());
        assertEquals(4, mother.getOffspring());
        assertEquals(2, kid1.getOffspring());
        assertEquals(2, kid2.getOffspring());
        assertEquals(1, kid3.getOffspring());
    }
    @Test
    void IsAnimalMovingOutOfMap()
    {
        WorldMap map = new WorldMap(4,4);
        Animal animal = new Animal(new Vector2d(2,2), 10, 5, BehaviorVariant.PREDESTINATION_BEHAVIOR);
        for(int i = 0; i < 100; i++)
            animal.move(map,0);
        System.out.println(animal);
        assertTrue((animal.getPosition().precedes(new Vector2d(3,3))
                && animal.getPosition().follows(new Vector2d(0,0))));
    }
}