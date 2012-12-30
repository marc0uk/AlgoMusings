package org.tullio.algo.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Graph<V> implements Iterable<Edge<V>> {
	
	private int n;
	private final Set<V> vertices = new HashSet<>();
	private final List<Edge<V>> edges;
	
	private static <V> List<Edge<V>> sort(
			final List<Edge<V>> original,
			final Comparator<Edge<V>> comparator) {
		final List<Edge<V>> copy = new ArrayList<>(original);
		Collections.sort(copy, comparator);
		return copy;
	}
	
	public Graph(
			final int numberOfVertices,
			final List<Edge<V>> edgeList) {
		n = numberOfVertices;
		final List<Edge<V>> tmp = new ArrayList<>(edgeList);
		Collections.sort(tmp);
		edges = Collections.unmodifiableList(tmp);
	}
	
	public Graph(
			final int numberOfVertices, 
			final List<Edge<V>> edgeList, 
			final Comparator<Edge<V>> edgeComparator) {
		this(numberOfVertices, sort(edgeList, edgeComparator));
	}
	
	public int n() {
		return n;
	}
	
	public int m() {
		return edges.size();
	}

	@Override
	public Iterator<Edge<V>> iterator() {
		return edges.iterator();
	}
	
	public Iterable<V> vertices() {
		if (vertices.isEmpty()) {
			for (final Edge<V> e : edges) {
				vertices.add(e.u());
				vertices.add(e.v());
			}
		}
		return vertices;
	}
	
	@Override
    public String toString() {
		return String.format("G[n=%d,m:=%d] FIRST{%s} LAST{%s}", 
				n, 
				edges.size(),
				edges.get(0),
				edges.get(edges.size() - 1));
    }
	
	public String toStringFull() {
		final StringBuilder sb = new StringBuilder(this.toString()).append("\n");
        for (final Edge<V> e : edges) {
        	sb.append("  ").append(e).append("\n");
        }
        return sb.toString();
	}
}
