package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomMutationTest {

    @Test
    void mutate() {
        Genome genome = new Genome(7);
        System.out.println(genome);
        Mutation mutation = new RandomMutation();
        mutation.mutate(genome);
        System.out.println(genome);
    }
}