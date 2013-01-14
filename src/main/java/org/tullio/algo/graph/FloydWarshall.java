package org.tullio.algo.graph;

import com.google.common.base.Optional;

public class FloydWarshall {

	private final WeightedDigraph g;
	
	public FloydWarshall(final WeightedDigraph graph) {
		g = graph;
	}
	
	public Optional<Double> solve() {
		final double[][] dist = initialise();
		for (int k=1; k<=g.n(); k++) {
			for (int i=1; i<=g.n(); i++) {
				for (int j=1; j<=g.n(); j++) {
					if (!Double.isInfinite(dist[i][k]) && !Double.isInfinite(dist[k][j])) {
						final double d = dist[i][k] + dist[k][j];
						if (d < dist[i][j]) {
							dist[i][j] = d;
						}
					}
				}
				if (dist[i][i] < 0) {
					return Optional.absent();
				}
			}
		}
		return Optional.of(findShortest(dist));
	}
	
	private double findShortest(final double[][] dist) {
		final int n = dist.length;
		double min = Double.MAX_VALUE;
		for (int i=1; i<n; i++) {
			for (int j=1; j<n; j++) {
				if (i != j) {
					if (dist[i][j] < min) {
						min = dist[i][j];
					}
				}
			}
		}
		return min;
	}
	
	private double[][] initialise() {
		final double[][] A = new double[g.n() + 1][g.n() + 1];
		for (int i=0; i<=g.n(); i++) {
			for (int j=0; j<=g.n(); j++) {
				if (i != j) {
					A[i][j] = Double.POSITIVE_INFINITY;
				}
			}
			for (final DirectedEdge e : g.adj(i)) {
				A[i][e.destination()] = e.length();
			}
		}
		return A;
	}
}
