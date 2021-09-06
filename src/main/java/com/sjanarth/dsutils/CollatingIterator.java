package com.sjanarth.dsutils;

import java.util.*;

/**
 * Given a list of Iterators that produce an non-decreasing sequence, the CollatingIterator
 * can produce a collated, non-decreasing sequence without dropping any values from the
 * underling iterators.
 *
 * @param <E> Type - could be a primitive or a custom class
 */
public class CollatingIterator<E> implements Iterator<E>
{
	/**
	 * Constructs an empty instance.
	 */
	public CollatingIterator () {
		pq = new PriorityQueue<E>();
		allIterators = new ArrayList<>();
		waitingValues = new HashMap<>();
	}

	/**
	 * Constructs an empty instance with a Comparator
	 * @param comparator Comparator instance to compare elements.
	 */
	public CollatingIterator (Comparator<? super E> comparator) {
		pq = new PriorityQueue<E>(comparator);
		allIterators = new ArrayList<>();
		waitingValues = new HashMap<>();
	}

	/**
	 * Adds the given iterator to an internal list of iterators.
	 * @param it Iterator instance to add to the list.
	 */
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

	private PriorityQueue<E> pq;
	private List<Iterator<E>> allIterators;
	private Map<E, Iterator<E>> waitingValues;
}