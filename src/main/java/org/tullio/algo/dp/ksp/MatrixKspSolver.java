package org.tullio.algo.dp.ksp;


public class MatrixKspSolver implements KspSolver {

	@Override
	public KspSolution solve(KnapSackProblem problem) {
		final int[][] sol = solutionMatrix(problem);
		return new KspSolution(
				sol[problem.numItems()][problem.sackSize()], 
				optimalSubSet(sol, problem));
	}
	
	private boolean[] optimalSubSet(final int[][] sol, final KnapSackProblem problem) {
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
	
	private int[][] init(final int numItems, final int sackSize) {
		final int[][] A = new int[numItems + 1][];
		for (int i=0; i<=numItems; i++) {
			A[i] = new int[sackSize + 1];
		}
		return A;
	}
}
