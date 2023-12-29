package agh.ics.oop.model;

public class TraversalBehavior implements Behavior {
    private boolean forward = true;
    @Override
    public void geneBehavior(Animal animal) {
        if (forward && animal.activeGene < animal.getGenome().getLength() - 1){
            animal.activeGene++;
        } else if (forward && animal.activeGene >= animal.getGenome().getLength() - 1) {
            forward = false;
            animal.activeGene--;
        } else if (!forward && animal.activeGene > 0) {
            animal.activeGene--;
        } else {
            forward = true;
            animal.activeGene++;
        }
    }
}
