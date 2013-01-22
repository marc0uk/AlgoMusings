package org.tullio.algo.dp.tsp;


public class TspSetBuilder {
	
	private final int size;
	
	private int set;
	private int cities;
	
	public TspSetBuilder(final int numCities) {
		size = numCities;
	}
	
	private void recycle() {
		set = 0;
		cities = 0;
	}

	public void addCity(final TspCity city) {
		set |= 1 << city.id();
		cities++;
	}
	
	
	public TspSet build() {
		final TspSet out = new TspSet(size, set, cities);
		recycle();
		return out;
	}

	public TspSet from(final int bits) {
		for (int i=0; i<size; i++) {
			if ((bits & (1L << i)) != 0) {
				cities++;
			}
		}
		final TspSet out = new TspSet(size, bits, cities);
		recycle();
		return out;
	}
}
