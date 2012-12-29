package org.tullio.algo.graph;

public class Edge<V> {

	private final V u;
	private final V v;
	private int cost;
	
	public Edge(final V uVertex, final V vVertex, final int edgeCost) {
		u = uVertex;
		v = vVertex;
		cost = edgeCost;
	}
	
	public V u() {
		return u;
	}
	
	public V v() {
		return v;
	}
	
	public int cost() {
		return cost;
	}
	
	@Override
    public String toString() {
            return String.format("E(%s,%s) C[%d]", u, v, cost);
    }
}
