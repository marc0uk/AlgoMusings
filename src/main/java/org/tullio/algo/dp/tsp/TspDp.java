package org.tullio.algo.dp.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TspDp {
	
	private final TspSetFormatter formatter;
	private final List<TspCity> cities;
	private final int numCities;
	private final Map<Integer, List<TspSet>> setsBySize;
	private final TspSetBuilder builder;
	private final double[][] A;
	
	public TspDp(final Collection<TspCity> tspCities) {
		cities = new ArrayList<>(tspCities);
		numCities = cities.size();
		setsBySize = allocateMap(numCities);
		builder = new TspSetBuilder(numCities);
		A = initialiseMap();
		//
		formatter = new TspSetFormatter(numCities);
	}
	
	public double solve() {
		computeSolutionMatrix();
		return computeTourDistance();
	}
	
	private Map<Integer, List<TspSet>> allocateMap(final int cities) {
		final Map<Integer, List<TspSet>> map = new HashMap<>();
		for (int i=1; i<=cities; i++) {
			map.put(i, new ArrayList<TspSet>());
		}
		return map;
	}
	
	private int populateSets() {
		final byte start = 1;
		int combos = (int) Math.pow(2, numCities);
		System.out.println(String.format("Creating %d sets", combos));
		final long ts = System.currentTimeMillis();
		for (int i=1; i<=combos; i++) {
			final TspSet set = builder.from(i);
			if (set.containsCity(start)) {
				setsBySize.get(set.size()).add(set);
			}
		}
		final double done = (System.currentTimeMillis() - ts) / 1000D;
		System.out.println(String.format("Done : %.5f [sec]", done));
		return combos;
	}
	
	private double[][] initialiseMap() {
		final int numSets = populateSets();
		System.out.println(String.format("Initialising subproblem matrix A[%d][%d]",
				numSets, (numCities + 1)));
		final long ts = System.currentTimeMillis();
		final double[][] A = new double[numSets][];
		for (int i=0; i<numSets; i++) {
			A[i] = new double[numCities + 1];
			A[i][1] = Double.POSITIVE_INFINITY;
		}
		A[1][1] = 0;
		final double done = (System.currentTimeMillis() - ts) / 1000D;
		System.out.println(String.format("Done : %.5f [sec]", done));
		return A;
	}

	private void computeSolutionMatrix() {
		// Loop on the sub-problem size
		for (int m=2; m<=numCities; m++) {
//			System.out.println("Solving form m=" + m);
			final List<TspSet> sets = setsBySize.get(m);
//			System.out.println(String.format("  Number of subproblems of size %d : %d", 
//					m, sets.size()));
			// Loop on the sets of size m containing first city
			for (final TspSet set : sets) {
				// Loop on each city of the current set.
//				System.out.println(String.format("    Subset[%d] %s", set.asIdx(), formatter.print(set)));
				for (Integer j : set) {
					if (j != 1) {
//						System.out.println(String.format("      Computing A[%d][%d]", set.asIdx(), j));
						A[set.asIdx()][j] = computeMin(set, j.byteValue());
					} else {
//						System.out.println("      j=1 : skipping");
					}
				}
			}
			System.out.println("Done problem size: " + m);
		}
	}
	
	private double computeTourDistance() {
		System.out.println("Finding last hop");
		final int tour = ((int) Math.pow(2, numCities)) - 1;
		final TspCity start = cities.get(0);
		double min = Double.MAX_VALUE;
		for (int j=2; j<=numCities; j++) {
			final double val = A[tour][j] + cities.get(j-1).distance(start);
			if (val < min) {
				min = val;
			}
		}
		return min;
	}
	
	private double computeMin(final TspSet set, final byte j) {
		final TspCity jCity = cities.get(j - 1);
		double min = Double.POSITIVE_INFINITY;
		final TspSet setMinusJ = set.without(j);
		final String setId = formatter.print(setMinusJ);
//		System.out.println(String.format("        %s-{%d}=%s", formatter.print(set), j, setId));
		for (Integer k : set) {
			if (k != j) {
				final TspCity kCity = cities.get(k - 1);
				final double dist = kCity.distance(jCity);
				final double val = A[setMinusJ.asIdx()][k] + dist;
//				System.out.println(String.format("          Dist from %s to %s = %.5f", kCity, jCity, dist));
//				System.out.println(String.format("          Checking A{%s}[%d][%d]+C_{%d,%d}=(%.1f + %.1f) < %.1f", 
//						setId, 
//						setMinusJ.asIdx(),
//						k,
//						k,
//						j,
//						A[setMinusJ.asIdx()][k],
//						dist,
//						min));
				if (val < min) {
					min = val;
//					System.out.println("            yes");
				} else {
//					System.out.println("            no");
				}
			}
		}
		return min;
	}
	
	private static class TspSetFormatter {
		
		private String format;
		
		public TspSetFormatter(final int numCities) {
			format = "%" + String.format("%ds", numCities);
		}
		
		String print(final TspSet set) {
			return String.format(format, Integer.toBinaryString(set.asIdx())).replace(' ', '0');
		}
	}
}
