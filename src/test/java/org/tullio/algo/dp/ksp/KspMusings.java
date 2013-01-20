package org.tullio.algo.dp.ksp;

import static org.tullio.algo.dp.ksp.KspUtil.readExpected;
import static org.tullio.algo.dp.ksp.KspUtil.readInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tullio.algo.dp.ksp.KnapSackProblem;
import org.tullio.algo.dp.ksp.KspItem;
import org.tullio.algo.dp.ksp.KspSolution;
import org.tullio.algo.dp.ksp.MatrixKspSolver;

public class KspMusings {
	
	private static final Logger LOG = LoggerFactory.getLogger(KspMusings.class);
	
	private static final String KSP_EX1 = "org/tullio/algo/dp/kspMusing1.txt";
	private static final String KSP_EX2 = "org/tullio/algo/dp/kspMusing2.txt";
	
	private static final String KSP_TEST_1 = "org/tullio/algo/dp/knapTest1.txt";
	private static final String KSP_TEST_SOL_1 = "org/tullio/algo/dp/knapTest1.sol";
	
	private static final String KSP_TEST_2 = "org/tullio/algo/dp/knapTest2.txt";
	private static final String KSP_TEST_SOL_2 = "org/tullio/algo/dp/knapTest2.sol";

	@Ignore
	@Test
	public void musing1() throws IOException {
		LOG.info(">>> ALGORITHM 1");
		algo1(readInput(ClassLoader.getSystemResourceAsStream(KSP_EX1)));
		algo1(readInput(ClassLoader.getSystemResourceAsStream(KSP_EX2)));
	}
	
	@Ignore
	@Test
	public void musing2() throws IOException {
		LOG.info(">>> ALGORITHM 2");
		algo2(readInput(ClassLoader.getSystemResourceAsStream(KSP_EX1)));
		algo2(readInput(ClassLoader.getSystemResourceAsStream(KSP_EX2)));
	}
	
	@Test
	public void invertedKnapSack() throws IOException {
		final KnapSackProblem problem1 = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_1));
		final KspSolution expected1 = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_1));
		//
		LOG.info("Inverted1 - Solution: {} Expected: {}", inverseMusing(problem1), expected1.optimalValue());
		final KnapSackProblem problem2 = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_2));
		final KspSolution expected2 = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_2));
		LOG.info("Inverted2 - Solution: {} Expected: {}", inverseMusing(problem2), expected2.optimalValue());
	}
	
	public int inverseMusing(final KnapSackProblem problem) throws IOException {
		final int[][] A = new int[problem.numItems() + 1][];
		for (int i=0; i<=problem.numItems(); i++) {
			A[i] = new int[problem.sackSize() + 1];
		}
		for (int w=0; w<=problem.sackSize(); w++) {
			for (int i=1; i<=problem.numItems(); i++) {
				final KspItem item = problem.item(i-1);
				if (item.weight > w) {
					A[i][w] = A[i-1][w];
				} else {
					A[i][w] = Math.max(
							A[i-1][w],
							item.value + A[i-1][w-item.weight]);
				} 
			}
		}
		return A[problem.numItems()][problem.sackSize()];
	}
	
	private void algo1(final KnapSackProblem fullProblem) {
		final KspSolution sol1 = new MatrixKspSolver().solve(fullProblem);
		LOG.info("Problem1: {} --> {}", fullProblem, sol1.optimalValue());
		final boolean[] picked1 = sol1.flag();
		final List<KspItem> complement = new ArrayList<>();
		for (int i=0; i<picked1.length; i++) {
			if (!picked1[i]) {
				complement.add(fullProblem.item(i));
			}
			LOG.info("{} -> {}", i, picked1[i]);
		}
		final KnapSackProblem problem2 = new KnapSackProblem(fullProblem.sackSize(), complement);
		final KspSolution sol2 = new MatrixKspSolver().solve(problem2);
		LOG.info("Problem2: {} --> {}", problem2, sol2.optimalValue());
		final boolean[] picked2 = sol2.flag();
		for (int i=0; i<picked2.length; i++) {
			LOG.info("{} -> {}", i, picked2[i]);
		}
	}

	private void algo2(final KnapSackProblem original) {
		final List<KspItem> items = new ArrayList<>(original.numItems());
		for (final KspItem item : original) {
			items.add(item);
		}
		final KnapSackProblem combined = new KnapSackProblem(
				original.sackSize() * 2,
				items);
		final KspSolution comboSol = new MatrixKspSolver().solve(combined);
		LOG.info("Combo: {} --> {}", combined, comboSol.optimalValue());
		final boolean[] picked = comboSol.flag();
		for (int i=0; i<picked.length; i++) {
			LOG.info("{} -> {}", i, picked[i]);
		}
	}
}
