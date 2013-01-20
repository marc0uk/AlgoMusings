package org.tullio.algo.dp.ksp;

import static org.tullio.algo.dp.ksp.KspUtil.readExpected;
import static org.tullio.algo.dp.ksp.KspUtil.readInput;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.tullio.algo.dp.ksp.KnapSackProblem;
import org.tullio.algo.dp.ksp.KspSolution;
import org.tullio.algo.dp.ksp.MatrixKspSolver;
import org.tullio.algo.dp.ksp.RecursiveKspSolver;

public class KnapSackProblemTest {
	
	private static final String KSP_SMALL = "org/tullio/algo/dp/knapSmall.txt";
	private static final String KSP_LARGE = "org/tullio/algo/dp/knapLarge.txt";
	
	private static final String KSP_TEST_1 = "org/tullio/algo/dp/knapTest1.txt";
	private static final String KSP_TEST_SOL_1 = "org/tullio/algo/dp/knapTest1.sol";
	
	private static final String KSP_TEST_2 = "org/tullio/algo/dp/knapTest2.txt";
	private static final String KSP_TEST_SOL_2 = "org/tullio/algo/dp/knapTest2.sol";
		
	@Test
	public void validateMatrixSolution1() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_1));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_1));
		final KspSolution actual = new MatrixKspSolver().solve(problem);
		validate(expected, actual);
	}
	
	@Test
	public void validateMatrixSolution2() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_2));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_2));
		final KspSolution actual = new MatrixKspSolver().solve(problem);
		validate(expected, actual);
	}
	
	@Test
	public void validateRecursiveSolution1() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_1));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_1));
		final KspSolution actual = new RecursiveKspSolver().solve(problem);
		validate(expected, actual);
	}
	
	@Test
	public void validateRecursiveSolution2() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_TEST_2));
		final KspSolution expected = readExpected(ClassLoader.getSystemResourceAsStream(KSP_TEST_SOL_2));
		final KspSolution actual = new RecursiveKspSolver().solve(problem);
		validate(expected, actual);
	}
	
	@Ignore
	@Test
	public void solveSmallProblem() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_SMALL));
		final KspSolution solution = new MatrixKspSolver().solve(problem);
		System.out.println("Small problem");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + solution.optimalValue());
	}
	
	@Ignore
	@Test
	public void solveSmallProblemRecursive() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_SMALL));
		final KspSolution solution = new RecursiveKspSolver().solve(problem);
		System.out.println("Small problem (recursive)");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + solution.optimalValue());
	}
	
	@Ignore
	@Test
	public void solveLargeProblem() throws Exception {
		final KnapSackProblem problem = readInput(ClassLoader.getSystemResourceAsStream(KSP_LARGE));
		final KspSolution solution = new RecursiveKspSolver().solve(problem);
		System.out.println("Large problem");
		System.out.println("  " + problem);
		System.out.println("  Solution: " + solution.optimalValue());
	}
	
	private static void validate(final KspSolution expected, final KspSolution actual) {
		boolean failed = false;
		final StringBuilder sb = new StringBuilder();
		if (actual.optimalValue() != expected.optimalValue()) {
			sb.append("Optimal value: ").append(actual.optimalValue())
			.append(" Expected: ").append(expected.optimalValue()).append("\n");
			failed = true;
		}
		if (validateSelection(actual.flag(), expected.flag(), sb)) {
			failed = true;
		}
		if (failed) {
			System.out.println(sb.toString());
			Assert.fail("KnapSack solution invalid");
		}
	}
	
	private static boolean validateSelection(final boolean[] actual, final boolean[] expected, StringBuilder sb) {
		boolean failed = false;
		for (int i=0; i<actual.length; i++) {
			if (actual[i] != expected[i]) {
				sb.append("item[").append(i).append("]=")
					.append(actual[i]).append(" expected=")
					.append(expected[i]).append("\n");
				failed = true;
			}
		}
		return failed;
	}
}
