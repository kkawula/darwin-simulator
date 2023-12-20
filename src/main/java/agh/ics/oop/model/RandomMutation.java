package agh.ics.oop.model;

public class RandomMutation implements Mutation {
    @Override
    public void mutate(Genome genome) {
        int randomIndex = (int) (Math.random() * genome.getLength());
        genome.setGenes(randomIndex, MapDirection.random());
    }
}
