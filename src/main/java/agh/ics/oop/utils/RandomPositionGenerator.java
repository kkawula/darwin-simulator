package agh.ics.oop.utils;

import agh.ics.oop.model.Vector2d;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.min;

public class RandomPositionGenerator implements Iterable<Vector2d>{

    private final ArrayList<Vector2d> arrayOfPositions;

    public RandomPositionGenerator(int numberOfPositions, int maxWidth, int maxHeight)
    {
        arrayOfPositions = new ArrayList<>();
        ArrayList<Integer> linearizedArrayOfPositions =
                new ArrayList<>(IntStream.range(0, maxWidth * maxHeight).boxed().toList());
        Collections.shuffle(linearizedArrayOfPositions);
        for(int i = 0; i < min(maxHeight * maxWidth, numberOfPositions); i++)
            arrayOfPositions.add(Vector2d.intToVector2d(linearizedArrayOfPositions.get(i),maxWidth));
    }

    public RandomPositionGenerator(int numberOfPositions, int maxWidth, int maxHeight, LinkedList<Vector2d> preferredPositions, LinkedList<Vector2d> occupiedPositions)
    {
        int arraySize = maxWidth * maxHeight;
        arrayOfPositions = new ArrayList<>();
        ArrayList<Vector2d> linearizedArrayOfPositions =  new ArrayList<>(IntStream.range(0, arraySize).mapToObj(i-> new Vector2d(i%maxWidth,i/maxWidth)).toList());
        ArrayList<Vector2d> probPositions = new ArrayList<>(linearizedArrayOfPositions.stream().filter(index -> (!preferredPositions.contains(index) && !occupiedPositions.contains(index))).toList());
        ArrayList<Vector2d> highProbPositions = new ArrayList<>(preferredPositions.stream().filter(index -> !(occupiedPositions.contains(index))).toList());
        Collections.shuffle(probPositions);
        Collections.shuffle(highProbPositions);
        probPositions.addAll(highProbPositions);
        int leftIterator=0;
        int rightIterator=probPositions.size()-1;
        Random random = new Random();
        for(int i=0;i<min(numberOfPositions,probPositions.size());i++)
        {
            if(random.nextInt(5)==0)
                arrayOfPositions.add(probPositions.get(leftIterator++));
            else
                arrayOfPositions.add(probPositions.get(rightIterator--));
        }
    }

    public Iterator<Vector2d> iterator() {
        return new randomPositionGeneratorIterator();
    }

    private class randomPositionGeneratorIterator implements Iterator<Vector2d>
    {
        private int index;
        public randomPositionGeneratorIterator()
        {
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < arrayOfPositions.size();
        }

        @Override
        public Vector2d next() {
            return arrayOfPositions.get(index++);
        }
    }
}
