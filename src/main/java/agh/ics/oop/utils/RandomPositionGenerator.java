package agh.ics.oop.utils;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.Collections.max;
import static java.util.Collections.swap;


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

    public RandomPositionGenerator(int numberOfPositions, int maxWidth, int maxHeight, LinkedList<Grass> grasses)
    {
        arrayOfPositions = new ArrayList<>();

        ArrayList<Integer> linearizedArrayOfPositions =  new ArrayList<>(IntStream.range(0, maxWidth * maxHeight).boxed().toList());
        Collections.shuffle(linearizedArrayOfPositions);

        ArrayList<Boolean> areGrassAlreadyOnMap = new ArrayList<>(Collections.nCopies(maxWidth * maxHeight, false));

        for(Grass grass : grasses)
            areGrassAlreadyOnMap.set(grass.getPosition().linearizedVector2d(maxWidth), true);

        int numberOfNewPositions = 0;
        int i = 0;

        while(numberOfNewPositions < numberOfPositions && i < maxWidth * maxHeight)
        {
            int newPosition = linearizedArrayOfPositions.get(i);
            if(!areGrassAlreadyOnMap.get(newPosition))
            {
                arrayOfPositions.add(Vector2d.intToVector2d(newPosition, maxWidth));
                numberOfNewPositions++;
            }
            i++;
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
