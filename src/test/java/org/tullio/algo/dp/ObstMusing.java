package org.tullio.algo.dp;

import org.junit.Test;

public class ObstMusing {

	@Test
	public void test() {
		int n = 7;		// number of keys
		double[][] cost = new double[n + 2][n+1];
		int[][] root = new int[n+2][n+1];
		
		optimalBST( prob, n, cost, root );
		
		for (int i = 1; i < n + 2; i++ ) {
			for (int j = 0; j < n + 1; j++ ) { 
				if ( j >= i - 1 ) {
					
					System.out.println(String.format( "%7.3f", cost[i][j]));
				} else {
					System.out.print("       ");
				}
			}
			System.out.println();
		}
		System.out.println();
		
		for (int i = 1; i < n + 2; i++ ) {
			for (int j = 0; j < n + 1; j++ ) {
				if ( j >= i - 1 ) {
					System.out.println(String.format( "%7d", root[i][j] ));
				} else {
					System.out.print("       ");
				}
			}
			System.out.println();
		}
		System.out.println("COST=" + cost[1][7]);
	}
	
	public static void optimalBST( double[] prob, int n, double[][] cost, int[][] root ) {
		for ( int low = n + 1; low >= 1; low-- )
			for ( int high = low - 1; high <= n; high++ )
				bestChoice( prob, cost, root, low, high );		
	}
	
	public static void bestChoice( double[] prob, double[][] cost, int[][] root, int low, int high ) {
		double bestCost;
		int bestRoot = -1;
		
		if ( high < low ) {
			bestCost = 0;	// empty tree
			bestRoot = -1;
		} else {	
			bestCost = Double.POSITIVE_INFINITY;
		}
		for ( int r = low; r <= high; r++ ) {
			double rCost = p( prob, low, high ) + cost[low][r-1] + cost[r+1][high];
			if (rCost < bestCost) {
				bestCost = rCost;
				bestRoot = r;
			}
		}

		cost[low][high] = bestCost;
		root[low][high] = bestRoot;
	}
	
	public static double p( double[] prob, int low, int high ) {
		double weight = 0.0;
		if ( low <= high ) { 
			for ( int i = low; i <= high; i++ ) {
				weight += prob[i];
			}
		}
		return weight;
	}

	private static double[] prob = {0, 0.05, 0.40, 0.08, 0.04, 0.10, 0.10, 0.23};
	
	
}
