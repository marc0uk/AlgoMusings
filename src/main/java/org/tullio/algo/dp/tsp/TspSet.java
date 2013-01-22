package org.tullio.algo.dp.tsp;

import java.util.Iterator;

public class TspSet implements Iterable<Integer>{

	private final int max;
	private final int size;
	private final int key;
	
	public TspSet(final int numCities, final int set, final int citiesInSet) {
		max = numCities;
		size = citiesInSet;
		key = set;
	}
	
	public int asIdx() {
		return key;
	}
	
	public int size() {
		return size;
	}
	
	public boolean containsCity(final byte cityIdx) {
		return (key & (1L << toBit(cityIdx))) != 0;
	}
	
	public TspSet without(final byte cityIdx) {
		final int newSet = key & ~(1 << toBit(cityIdx));
		return new TspSet(max, newSet, size-1);
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new CityIterator();
	}
	
	private byte toBit(final byte cityIdx) {
		return (byte) (cityIdx - 1);
	}

	private class CityIterator implements Iterator<Integer> {

		private int bit = 0;

		@Override
		public boolean hasNext() {
			while (bit < max) {
				if ((key & (1 << bit)) != 0) {
					break;
				}
				bit++;
			}
			return bit < max;
		}

		@Override
		public Integer next() {
			bit++;
			return bit;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
