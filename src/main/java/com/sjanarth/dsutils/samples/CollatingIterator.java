package com.sjanarth.dsutils.samples;

import java.util.Iterator;

public class CollatingIterator
{
    public static void main (String[] args) {
        com.sjanarth.dsutils.CollatingIterator<Integer> ci = new com.sjanarth.dsutils.CollatingIterator<>();
        ci.addIterator(new RandomIncreasingIterator());
        ci.addIterator(new RandomIncreasingIterator());
        ci.addIterator(new RandomIncreasingIterator());
        for (int i = 0; i < 10; i++)
            System.out.println(ci.next());
    }

    private static class RandomIncreasingIterator implements Iterator<Integer> {
        public RandomIncreasingIterator() {}
        private Integer lastValue = null;
        @Override
        public boolean hasNext() {
            return true;
        }
        @Override
        public Integer next() {
            Integer value = (int) (Math.random() * 10.0);
            //System.out.println("value="+value+", lastValue="+lastValue);
            if (lastValue == null)	{
                lastValue = value;
                return value;
            } else {
                lastValue = lastValue + value;
                return lastValue;
            }
        }
    }
}
