package agh.ics.oop.model;

public class PredestinationBehavior implements Behavior{

    @Override
    public void geneBehavior(Animal animal) {
        if (animal.activeGene < animal.getGenome().getLength() - 1) {
            animal.activeGene++;
        } else {
            animal.activeGene = 0;
        }
    }
}
