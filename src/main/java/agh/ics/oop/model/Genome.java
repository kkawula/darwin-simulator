package agh.ics.oop.model;

import java.util.Random;

import static java.lang.Math.max;

public class Genome {
    private int length;

    private MapDirection[] genes;

    public MapDirection[] getGenes() {
        return genes;
    }

    public int getLength() {
        return length;
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
        int strongerLenght = (int) (strongerRatio * length);
        int weakerLenght = length - strongerLenght;

        boolean startFromLeft = new Random().nextBoolean();

        for (int i = 0; i < strongerLenght; i++) {
            if (startFromLeft)
                this.genes[i] = strongerGenome.genes[i];
            else
                this.genes[length - i - 1] = strongerGenome.genes[length - i - 1];
        }
        for (int i = 0; i < weakerLenght; i++) {
            if (startFromLeft)
                this.genes[i + strongerLenght] = weakerGenome.genes[i + strongerLenght];
            else
                this.genes[length - i - 1 - strongerLenght] = weakerGenome.genes[length - i - 1 - strongerLenght];
        }
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
