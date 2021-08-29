package com.sjanarth.dsutils;

import java.util.*;

/*
 * Given a list of iterators that give out non decreasing stream of integers
 * design a "collating iterator" that gives out a non decreasing stream of integers.
 * 
 * Notes:
 * 
 * 1. The iterators could individually and collective give out duplicate integers.
 * 2. The collating iterator must not drop any numbers from the individual iterators.
 * 
 * Reference:
 * 
 * org.apache.commons.collections.iterators.CollatingIterator	
 * 
 * This uses an ArrayList as a backing store and a BitSet for checking which iterators
 * to consult for the next value.
 * 
 * TODO:
 * 
 * 1. Make this a generic class able to give out any valid types
 * 2. Provide a way to supply a custom Comparator for the PriorityQueue
 * 
 */
public class CollatingIterator implements Iterator<Integer> 
{
	private List<Iterator<Integer>> allIterators = new ArrayList<Iterator<Integer>>();
	private PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
	private Map<Integer, Iterator<Integer>> waitingValues = new HashMap<Integer, Iterator<Integer>>();
	
	public CollatingIterator () { }
	public void addIterator(Iterator<Integer> it) { allIterators.add(it); }
	@Override
	public boolean hasNext() {
		if (!pq.isEmpty()) 
			return true;
		for (Iterator<Integer> it : allIterators)	
			if (it.hasNext())
				return true;
		return false;
	}
	@Override
	public Integer next() {
		if (!hasNext())
			return null;
		Collection<Iterator<Integer>> waitingIterators = waitingValues.values();
		if (waitingIterators.size() < allIterators.size())	{
			for (Iterator<Integer> it : allIterators)	{
				if (!waitingIterators.contains(it))	{
					pullFrom (it);
				}
			}
		}
		//debug();
		Integer v = pq.poll();
		waitingValues.remove(v);
		return v;
	}
	private void pullFrom(Iterator<Integer> it)	{
		if (it.hasNext())	{
			Integer v = it.next();
			pq.offer(v);
			waitingValues.put(v, it);
		}
	}
	private void debug ()	{
		System.out.print("    Queue=[");
		Integer[] values = pq.toArray(new Integer[pq.size()]);
		Arrays.sort(values, pq.comparator());
		for (Integer v : values) 
		    System.out.print(v+", ");
		System.out.println("]");
	}
	
	public static void main(String[] args) {
		CollatingIterator ci = new CollatingIterator();
		ci.addIterator(new RandomIncreasingIterator());
		ci.addIterator(new RandomIncreasingIterator());
		ci.addIterator(new RandomIncreasingIterator());
		for (int i = 0; i < 10; i++)	
			System.out.println(ci.next());
	}
	
	private static class RandomIncreasingIterator implements Iterator<Integer> 	{
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
