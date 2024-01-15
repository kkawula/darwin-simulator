package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

public class RandomMutation implements Mutation {

    public void mutate(Genome genome, int minimumMutations, int maximalMutations) {
        List<Integer> genesIdx = IntStream.of(0,genome.getLength()-1)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(genesIdx);
        Random random = new Random();
        int numberOfMutations = random.nextInt(minimumMutations,maximalMutations);
        for(int i=0;i<min(numberOfMutations,genome.getLength());i++)
        {
            genome.setGenes(genesIdx.get(i),MapDirection.values()[random.nextInt(0,7)]);
        }
    }
}
