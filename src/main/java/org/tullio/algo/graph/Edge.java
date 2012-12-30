package org.tullio.algo.graph;

/**
 * A weighted edge in a undirected graph. Implements ascending cost
 * total order.
 *
 * @param <V> Type of the vertex.
 */
public class Edge<V> implements Comparable<Edge<V>> {

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
	public int compareTo(Edge<V> o) {
		return cost < o.cost ?
				-1 :
				cost > o.cost ?
					1 :
					0;
	}
	
	@Override
    public String toString() {
            return String.format("E(%s,%s) C[%d]", u, v, cost);
    }
}
