package org.tullio.algo.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnapSackProblemTest {
	
	private static Logger LOG = LoggerFactory.getLogger(KnapSackProblemTest.class);
	
	private static final String KSP_SMALL = "org/tullio/algo/dp/knapSmall.txt";
	private static final String KSP_LARGE = "org/tullio/algo/dp/knapLarge.txt";
	
	private static final String KSP_TEST_1 = "org/tullio/algo/dp/knapTest1.txt";
	private static final String KSP_TEST_SOL_1 = "org/tullio/algo/dp/knapTest1.sol";
	
	private static final String KSP_TEST_2 = "org/tullio/algo/dp/knapTest2.txt";
	private static final String KSP_TEST_SOL_2 = "org/tullio/algo/dp/knapTest2.sol";
		
	@Test
	public void validateMatrixSolution1() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_1));
		Assert.assertEquals("Wrong KnapSack solution " + problem, 1458, knapSackSolution(problem));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_1));
//		final int[] set = optimalSubSet(problem);
//		expected.validate(toFlag(set, problem.numItems()));
		final boolean[] flag = optimalSubSetFlag(problem);
		expected.validate(flag);
	}
	
	@Test
	public void validateMatrixSolution2() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_2));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_2));
		Assert.assertEquals("Wrong KnapSack solution " + problem, 18, knapSackSolution(problem));
		final int[] set = optimalSubSet(problem);
		expected.validate(toFlag(set, problem.numItems()));
	}
	
	@Test
	public void validateRecursiveSolution1() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_1));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_1));
		final KspSolution actual = new Ksp(problem).solve();
		expected.validate(actual);
	}
	
	@Test
	public void validateRecursiveSolution2() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_2));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_2));
		final KspSolution actual = new Ksp(problem).solve();
		expected.validate(actual);
	}
	
	@Ignore
	@Test
	public void solveSmallProblem() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_SMALL));
		final KspSolution solution = new Ksp(problem).solve();
		System.out.println("Small problem");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + solution.optimalValue());
	}
	
	@Ignore
	@Test
	public void solveSmallProblemRecursive() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_SMALL));
		System.out.println("Small problem (recursive)");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + knapSackSolution(problem));
	}
	
	@Ignore
	@Test
	public void solveLargeProblem() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_LARGE));
		final KspSolution solution = new Ksp(problem).solve();
		System.out.println("Large problem");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + solution.optimalValue());
	}
	
	private int knapSackSolution(final KnapSackProblem problem) {
		final int[][] sol = solutionMatrix(problem);
		return sol[problem.numItems()][problem.sackSize()];
	}
	
	
	private int[][] solutionMatrix(final KnapSackProblem problem) {
		final int[][] A = init(problem.numItems(), problem.sackSize());
		for (int i=1; i<=problem.numItems(); i++) {
			final KspItem item = problem.item(i-1);
			for (int w=0; w<=problem.sackSize(); w++) {
				if (item.weight > w) {
					A[i][w] = A[i-1][w];
				} else {
					A[i][w] = Math.max(
							A[i-1][w],
							item.value + A[i-1][w-item.weight]);
				} 
			}
		}
		return A;
	}
	
	private boolean[] optimalSubSetFlag(final KnapSackProblem problem) {
		final int[][] sol = solutionMatrix(problem);
		boolean[] subset = new boolean[problem.numItems()];
		int i = problem.numItems();
		for (int j = sol[0].length - 1; j >= 0 && i > 0;i--) {
			// If the item is in the optimal subset, add it and subtract its weight
			// from the column we are checking.
			if (sol[i][j] != sol[i-1][j]) {
				subset[i-1] = true;
				j -= problem.item(i-1).weight();
			}
		}
		return subset;
	}
	
	private int[] optimalSubSet(final KnapSackProblem problem) {
		final int[][] sol = solutionMatrix(problem);
		int[] subset = new int[problem.numItems()];
		int numItems = 0;
		int i = problem.numItems();
		for (int j = sol[0].length - 1; j >= 0 && i > 0;i--) {
			// If the item is in the optimal subset, add it and subtract its weight
			// from the column we are checking.
			if (sol[i][j] != sol[i-1][j]) {
				subset[numItems] = i;
				j -= problem.item(i-1).weight();
				numItems++;
			}
		}
		return Arrays.copyOfRange(subset, 0, numItems);
	}
	
	private static boolean[] toFlag(final int[] used, final int numItems) {
		final boolean[] flag = new boolean[numItems];
		for (int item : used) {
			flag[item-1] = true;
		}
		return flag;
	}
	
	private int[][] init(final int numItems, final int sackSize) {
		final int[][] A = new int[numItems + 1][];
		for (int i=0; i<=numItems; i++) {
			A[i] = new int[sackSize + 1];
		}
		return A;
	}
		
	private static class Ksp {
		
		private final KnapSackProblem problem;
		private final Map<KspKey, Integer> cache = new HashMap<>();
		
		Ksp(final KnapSackProblem knapSack) {
			problem = knapSack;
		}
		
		public KspSolution solve() {
			final int solution = solve(problem.numItems(), problem.sackSize());
			final int[] subSet = optimalSubSet();
			LOG.debug("  Cache size: {}", cache.size());
			//return new KspSolution(solution, subSet.length, toFlag(subSet, problem.numItems()));
			return new KspSolution(solution, subSet.length, flag());
		}
		
		private boolean[] flag() {
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
		
		private int[] optimalSubSet() {
			int[] subset = new int[problem.numItems()];
			int numItems = 0;
			int w = problem.sackSize();
			for (int i=problem.numItems(); i>0; i--) {
				final Integer current = cache.get(new KspKey(w, i));
				final Integer prev = cache.get(new KspKey(w, i-1));
				if (prev == null || prev.intValue() != current.intValue()) {
					subset[numItems] = i;
					numItems++;
					w -= problem.item(i-1).weight();
				}
			}
			return Arrays.copyOfRange(subset, 0, numItems);
		}
		
		private int solveMe(final int numItems, final int w) {
			final KspKey key = new KspKey(w, numItems);
			LOG.info("Solving {}", key);
			final Integer sol = cache.get(key);
			if (sol != null) {
				LOG.info("{} ==> CACHE HIT : {}", key, sol);
				return sol;
			}
			final KspItem item = problem.item(numItems - 1);
			if (numItems == 1) {
				if (item.weight > w) {
					LOG.info("{} ==> BASE CASE, ITEM SKIPPED : 0", key);
					cache.put(key, 0);
					return 0;
				}
				cache.put(key, problem.item(0).value());
				LOG.info("{} ==> BASE CASE, ITEM USED : {}", key, problem.item(0).value());
				return problem.item(0).value();
			} else if (item.weight > w) {
				final int s = solveMe(numItems - 1, w);
				LOG.info("{} ==> ITEM SKIPPED", key);
				cache.put(key, s);
			} else {
				final int skip = solveMe(numItems - 1, w);
				final int keep = item.value() + solveMe(numItems - 1, w - item.weight);
				final int s = Math.max(skip, keep);
				if (s == keep) {
					LOG.info("{} ==> ITEM KEPT: {}", key, s);
				} else {
					LOG.info("{} ==> ITEM SKIPPED via MAX: {}", key, s);
				}
				cache.put(key, s);
			}
			LOG.info("{} --> {}", key, cache.get(key));
			return cache.get(key);
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
	
	private static KnapSackProblem readInput(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final String[] info =  br.readLine().split("\\s+");
			final int sackSize = Integer.parseInt(info[0]);
			final int numItems = Integer.parseInt(info[1]);
			final List<KspItem> items = new ArrayList<>(numItems);
			String line;
            while ((line = br.readLine()) != null) {
            	final String[] tokens = line.split("\\s+");
            	if (tokens.length != 2) {
                	throw new IOException("Invalid line, expecting 2 tokens:\n" + line);
                }
            	items.add(new KspItem(
            			Integer.parseInt(tokens[0]), 
            			Integer.parseInt(tokens[1])));
            }
            return new KnapSackProblem(sackSize, items);
		} finally {
			stream.close();
		}
	}
	
	private static KspSolution readExpected(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final String[] info = br.readLine().split("\\s+");
			final int sol = Integer.parseInt(info[0]);
			final int numItems = Integer.parseInt(info[1]);
			final boolean[] flag = new boolean[numItems];
			int usedItems = 0;
			String line;
			int idx=0;
			while ((line = br.readLine()) != null) {
				final boolean used = Integer.parseInt(line) == 1;
				if (used) {
					usedItems++;
					flag[idx] = true;
				}
				idx++;
			}
			return new KspSolution(sol, usedItems, flag);
		} finally {
			stream.close();
		}
	}
	
	private static class KspSolution {
		
		private final int optimalValue;
		private final int usedItems;
		private final boolean[] flag;
		
		private KspSolution(final int solution, final int numItemUsed, final boolean[] usedFlag) {
			optimalValue = solution;
			usedItems = numItemUsed;
			flag = usedFlag;
		}
		
		public int optimalValue() {
			return optimalValue;
		}
		
		public int usedItems() {
			return usedItems;
		}
		
		public boolean[] flag() {
			return flag;
		}
		
		public void printSubSet() {
			for (int i=0; i<flag.length; i++) {
				System.out.println(String.format("item[%d] -> %s", 
						i, 
						(flag[i] ? "PICKED" : "-")));
			}
		}
		
		public void validate(final KspSolution actual) {
			boolean failed = false;
			final StringBuilder sb = new StringBuilder();
			if (actual.optimalValue() != optimalValue) {
				sb.append("Optimal value: ").append(actual.optimalValue())
				.append(" Expected: ").append(optimalValue).append("\n");
				failed = true;
			}
			if (validateSelection(actual.flag, sb)) {
				failed = true;
			}
			if (failed) {
				System.out.println(sb.toString());
				Assert.fail("KnapSack solution invalid");
			}
		}
		
		public void validate(final boolean[] actual) {
			final StringBuilder sb = new StringBuilder();
			if (validateSelection(actual, sb)) {
				System.out.println(sb.toString());
				Assert.fail("KnapSack solution invalid");
			}
		}
		
		private boolean validateSelection(final boolean[] actual, StringBuilder sb) {
			boolean failed = false;
			for (int i=0; i<flag.length; i++) {
				if (actual[i] != flag[i]) {
					sb.append("item[").append(i).append("]=")
						.append(actual[i]).append(" expected=")
						.append(flag[i]).append("\n");
					failed = true;
				}
			}
			return failed;
		}
	}
}
