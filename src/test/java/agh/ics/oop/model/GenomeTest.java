package agh.ics.oop.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class GenomeTest {

    @Test
    public void testGenomeConstructorWithMapDirectionArray() {
        MapDirection[] directions = new MapDirection[]{MapDirection.NORTH, MapDirection.SOUTH};
        Genome genome = new Genome(directions);
        assertEquals(2, genome.getLength());
        assertArrayEquals(directions, genome.getGenes());
    }

    @Test
    public void testGenomeConstructorWithLength() {
        Genome genome = new Genome(5);
        assertEquals(5, genome.getLength());
        assertNotNull(genome.getGenes());
        assertEquals(5, genome.getGenes().length);
    }
    @Test
    public void testGenomeConstructorWithGenomesAndEnergies() {
        Genome motherGenome = new Genome(3);
        Genome fatherGenome = new Genome(3);
        Genome childGenome = new Genome(30, 10, motherGenome, fatherGenome);
        assertEquals(3, childGenome.getLength());
        assertNotNull(childGenome.getGenes());
        assertEquals(3, childGenome.getGenes().length);
    }

    @Test
    public void testGenomeConstructorWithConcreteGenomes() {
        MapDirection[] motherDirections = new MapDirection[]{MapDirection.NORTH, MapDirection.SOUTH, MapDirection.EAST};
        MapDirection[] fatherDirections = new MapDirection[]{MapDirection.WEST, MapDirection.NORTH_WEST, MapDirection.SOUTH_EAST};
        Genome motherGenome = new Genome(motherDirections);
        Genome fatherGenome = new Genome(fatherDirections);
        Genome childGenome = new Genome(30, 10, motherGenome, fatherGenome);
        System.out.println(childGenome);
        assertEquals(3, childGenome.getLength());
        assertNotNull(childGenome.getGenes());
        assertEquals(3, childGenome.getGenes().length);
    }
    @Test
    public void testGenomeToString1() {
        MapDirection[] directions = new MapDirection[]{MapDirection.NORTH, MapDirection.SOUTH, MapDirection.EAST};
        Genome genome = new Genome(directions);
        String expectedString = "N S E";
        assertEquals(expectedString, genome.toString());
    }
    @Test
    public void testGenomeToString2() {
        MapDirection[] directions = new MapDirection[]{MapDirection.NORTH_WEST, MapDirection.SOUTH_EAST, MapDirection.WEST, MapDirection.NORTH_EAST,};
        Genome genome = new Genome(directions);
        String expectedString = "NW SE W NE";
        assertEquals(expectedString, genome.toString());
    }
}