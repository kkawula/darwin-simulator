package agh.ics.oop.model;

import agh.ics.oop.simulation.WorldMap;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void updateChildren1() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        assertEquals(1, father.getChildren());
        assertEquals(1, mother.getChildren());
    }

    @Test
    void updateChildren2() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, father, mother);
        assertEquals(2, father.getChildren());
        assertEquals(2, mother.getChildren());
    }
    @Test
    void updateChildren3() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal postMan = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, postMan, mother);
        assertEquals(1, father.getChildren());
        assertEquals(1, postMan.getChildren());
        assertEquals(2, mother.getChildren());
    }

    @Test
    void updateOffSpring1() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        assertEquals(1, father.getOffspring());
        assertEquals(1, mother.getOffspring());
    }
    @Test
    void updateOffSpring2() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, father, mother);
        assertEquals(2, father.getOffspring());
        assertEquals(2, mother.getOffspring());
    }
    @Test
    void updateOffspring3() {
        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal postMan = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, postMan, mother);
        assertEquals(1, father.getOffspring());
        assertEquals(1, postMan.getOffspring());
        assertEquals(2, mother.getOffspring());
    }

    @Test
    void updateOffspring4(){

        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid3 = new Animal(new Vector2d(0,0), 30, kid1, kid2);

        assertEquals(3, father.getOffspring());
        assertEquals(3, mother.getOffspring());
        assertEquals(1, kid1.getOffspring());
        assertEquals(1, kid2.getOffspring());
    }

    @Test
    void updateOffspring5(){

        Animal father = new Animal(new Vector2d(0,0), 30, 4);
        Animal mother = new Animal(new Vector2d(0,0), 30, 4);
        Animal kid1 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid2 = new Animal(new Vector2d(0,0), 30, father, mother);
        Animal kid3 = new Animal(new Vector2d(0,0), 30, kid1, kid2);
        Animal kid4 = new Animal(new Vector2d(0,0), 30, father, kid3);

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
        Animal animal = new Animal(new Vector2d(2,2),10,5);
        for(int i=0;i<100;i++)
            animal.move(map);
        assertTrue(animal.getPosition().precedes(new Vector2d(3,3))
                && animal.getPosition().follows(new Vector2d(0,0)));
    }
}