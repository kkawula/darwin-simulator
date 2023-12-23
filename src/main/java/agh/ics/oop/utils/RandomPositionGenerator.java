package agh.ics.oop.utils;

import agh.ics.oop.model.Vector2d;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class RandomPositionGenerator implements Iterable<Vector2d>{

    private final ArrayList<Vector2d> arrayOfPositions;

    public RandomPositionGenerator(int numberOfPositions, int maxWidth, int maxHeight)
    {
        arrayOfPositions=new ArrayList<>(numberOfPositions);
        ArrayList<Integer> linearizedArrayOfGrassPosition =
                new ArrayList<>(IntStream.range(0, maxWidth * maxHeight).boxed().toList());
        Collections.shuffle(linearizedArrayOfGrassPosition);
        for(int i=0;i<numberOfPositions;i++)
            arrayOfPositions.add(new Vector2d(linearizedArrayOfGrassPosition.get(i)%maxWidth,linearizedArrayOfGrassPosition.get(i)/maxHeight));
    }

    public Iterator<Vector2d> iterator() {
        return new randomPositionGeneratorIterator();
    }
    private class randomPositionGeneratorIterator implements Iterator<Vector2d>
    {
        private int index;
        public randomPositionGeneratorIterator()
        {
            index=0;
        }
        @Override
        public boolean hasNext() {
            return index<arrayOfPositions.size();
        }

        @Override
        public Vector2d next() {
            return arrayOfPositions.get(index++);
        }
    }
}
