package agh.ics.oop.model;

import java.util.Random;

import static java.lang.Math.max;

public class Genome {
    private final int length;

    private final MapDirection[] genes;

    public MapDirection[] getGenes() {
        return genes;
    }

    public Genome(MapDirection[] genes) {// for testing
        this.length = genes.length;
        this.genes = genes;
    }
    public Genome(int length) {
        this.length = length;
        this.genes = new MapDirection[length];
        for (int i = 0; i < length; i++) {
            this.genes[i] = MapDirection.random();
        }
    }

    public Genome(double motherEnergy, double fatherEnergy, Genome motherGenome, Genome fatherGenome) {
        this.length = motherGenome.length;
        this.genes = new MapDirection[length];

        Genome strongerGenome = fatherEnergy > motherEnergy ? fatherGenome : motherGenome;
        Genome weakerGenome = fatherEnergy > motherEnergy ? motherGenome : fatherGenome;
        double strongerRatio = max(fatherEnergy, motherEnergy)  / (motherEnergy + fatherEnergy);
        int strongerLength = (int) (strongerRatio * length);
        int weakerLength = length - strongerLength;
        boolean startFromLeft = new Random().nextBoolean();

        for (int i = 0; i < strongerLength; i++) {
            if (startFromLeft)
                this.genes[i] = strongerGenome.genes[i];
            else
                this.genes[length - i - 1] = strongerGenome.genes[length - i - 1];
        }
        for (int i = 0; i < weakerLength; i++) {
            if (startFromLeft)
                this.genes[i + strongerLength] = weakerGenome.genes[i + strongerLength];
            else
                this.genes[length - i - 1 - strongerLength] = weakerGenome.genes[length - i - 1 - strongerLength];
        }
    }

    public void setGenes(int index, MapDirection gene) {
        this.genes[index] = gene;
    }

    public int getLength() { return length; }

    public MapDirection getGene(int index)  {
        return genes[index];
    }

    @Override
    public String toString() {
        StringBuilder genomeString = new StringBuilder();
        for (MapDirection gene : genes) {
            genomeString.append(gene.toString());
            genomeString.append(" ");
        }
        return genomeString.toString().trim();
    }
}
