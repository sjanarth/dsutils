package com.sjanarth.dsutils;

import java.util.*;

public class CollatingIterator<E> implements Iterator<E>
{
	private PriorityQueue<E> pq;
	private List<Iterator<E>> allIterators;
	private Map<E, Iterator<E>> waitingValues;
	
	public CollatingIterator () {
		pq = new PriorityQueue<E>();
		allIterators = new ArrayList<Iterator<E>>();
		waitingValues = new HashMap<E, Iterator<E>>();
	}

	public CollatingIterator (Comparator<? super E> comparator) {
		pq = new PriorityQueue<E>(comparator);
		allIterators = new ArrayList<Iterator<E>>();
		waitingValues = new HashMap<E, Iterator<E>>();
	}

	public void addIterator(Iterator<E> it) {
		allIterators.add(it);
	}

	@Override
	public boolean hasNext() {
		if (!pq.isEmpty()) 
			return true;
		for (Iterator<E> it : allIterators)
			if (it.hasNext())
				return true;
		return false;
	}

	@Override
	public E next() {
		if (!hasNext())
			return null;
		Collection<Iterator<E>> waitingIterators = waitingValues.values();
		if (waitingIterators.size() < allIterators.size())	{
			for (Iterator<E> it : allIterators)	{
				if (!waitingIterators.contains(it))	{
					pullFrom (it);
				}
			}
		}
		E v = pq.poll();
		waitingValues.remove(v);
		return v;
	}

	private void pullFrom(Iterator<E> it)	{
		if (it.hasNext())	{
			E v = it.next();
			pq.offer(v);
			waitingValues.put(v, it);
		}
	}

	public static void main(String[] args) {
		CollatingIterator<Integer> ci = new CollatingIterator<>();
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
