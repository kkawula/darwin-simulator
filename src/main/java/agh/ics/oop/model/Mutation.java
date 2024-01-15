package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Mutation {
    static void mutate(Genome genome, int minimalMutations, int maximalMutations)
    {
        List<Integer> genesIdx = IntStream.of(0,genome.getLength())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(genesIdx);
        Random random = new Random();
        int numberOfMutations = random.nextInt(minimalMutations,maximalMutations);
        for(int i=0;i<numberOfMutations;i++)
        {
            genome.setGenes(genesIdx.get(i),MapDirection.values()[random.nextInt(0,7)]);
        }
    }
}
