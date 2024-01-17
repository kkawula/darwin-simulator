package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

class RandomMutationTest {

    @Test
    void mutate() {
        Genome genome = new Genome(7);
        System.out.println(genome);
        Mutation mutation = new RandomMutation();
        mutation.mutate(genome,0,2);
        System.out.println(genome);
    }
}