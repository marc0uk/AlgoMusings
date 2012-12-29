package org.tullio.algo.graph;

import org.tullio.algo.ds.UnionFind;

public class Clustering {

	/**
	 * Find the maximum cost k-clustering for the supplied graph.
	 * 
	 * @param k Desired number of clusters.
	 * @param graph Input graph.
	 * @return The maximum cost of the desired k-clustering.
	 */
	public static <V> int findMaxCost(final int k, final Graph<V> graph) {
		int clusters = graph.n();
        final UnionFind<V> UF = new UnionFind<>(graph.vertices());
        for (final Edge<V> e : graph) {
        	final V lu = UF.find(e.u());
        	final V lv = UF.find(e.v());
        	if (clusters == k && (!lu.equals(lv))) {
                return e.cost();
        	}
        	if (!lu.equals(lv)) {
                UF.union(lu, lv);
                clusters--;
        	}
        }
        throw new RuntimeException("Didn't work");
	}
}
