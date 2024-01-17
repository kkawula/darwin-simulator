package agh.ics.oop.utils;

import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomPositionGeneratorTest {
    @Test
    void testGenerateRandomPosition() {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(10,10, 10);
        for (Vector2d position : randomPositionGenerator)
        {
            System.out.println(position);
        }
        assertTrue(true);
    }

}