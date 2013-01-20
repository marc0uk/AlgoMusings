package org.tullio.algo.dp.ksp;

import java.util.HashMap;
import java.util.Map;

public class RecursiveKspSolver implements KspSolver {
	
	private final Map<KspKey, Integer> cache = new HashMap<>();
	private KnapSackProblem problem;
	
	@Override
	public KspSolution solve(KnapSackProblem problem) {
		init(problem);
		final int solution = solve(problem.numItems(), problem.sackSize());
		return new KspSolution(solution, optimalSubSet());
	}
	
	private void init(final KnapSackProblem currentProblem) {
		cache.clear();
		problem = currentProblem;		
	}
	
	private int solve(final int numItems, final int w) {
		final KspKey key = new KspKey(w, numItems);
		final Integer sol = cache.get(key);
		if (sol != null) {
			return sol;
		}
		final KspItem item = problem.item(numItems - 1);
		if (numItems == 1) {
			if (item.weight() > w) {
				cache.put(key, 0);
				return 0;
			}
			cache.put(key, problem.item(0).value());
			return problem.item(0).value();
		} else if (item.weight > w) {
			final int s = solve(numItems - 1, w);
			cache.put(key, s);
		} else {
			final int skip = solve(numItems - 1, w);
			final int keep = item.value() + solve(numItems - 1, w - item.weight);
			final int s = Math.max(skip, keep);
			cache.put(key, s);
		}
		return cache.get(key);
	}
	
	private boolean[] optimalSubSet() {
		boolean[] flag = new boolean[problem.numItems()];
		int w = problem.sackSize();
		for (int i=problem.numItems(); i>0; i--) {
			final Integer current = cache.get(new KspKey(w, i));
			final Integer prev = cache.get(new KspKey(w, i-1));
			if (prev == null || prev.intValue() != current.intValue()) {
				flag[i-1] = true;
				w -= problem.item(i-1).weight();
			}
		}
		return flag;
	}

	private static class KspKey {
		
		private final int numItems;
		private final int capacity;
		
		KspKey(final int W, final int N) {
			numItems = N;
			capacity = W;
		}
		
		@Override
		public int hashCode() {
			return (numItems * 3539) ^ capacity;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (!(obj instanceof KspKey)) {
				return false;
			}
			final KspKey other = KspKey.class.cast(obj);
			return numItems == other.numItems && capacity == other.capacity;
		}
		
		@Override
		public String toString() {
			return String.format("KS(%d,%d)", numItems, capacity);
		}
	}
}
