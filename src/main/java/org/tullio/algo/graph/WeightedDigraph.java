package org.tullio.algo.graph;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * Directed graph with weighted edges.
 */
public class WeightedDigraph {

	/** Number of vertices. */
	private final int n;
	
	/** Number of edges. */
	private final int m;
	
	/** Adjacency list. */ 
	private final SetMultimap<Integer, DirectedEdge> adj;
	
	/**
	 * @param vertices Number of vertices.
	 * @param edges Number of edges.
	 * @param allEdges All the edges.
	 */
	public WeightedDigraph(final int vertices, final int edges, final Collection<DirectedEdge> allEdges) {
		n = vertices;
		m = edges;
		adj = HashMultimap.create();
		for (final DirectedEdge e : allEdges) {
			adj.put(e.source(), e);
		}
	}
	
	/**
	 * @return Number of vertices.
	 */
	public int n() {
		return n;
	}
	
	/**
	 * @return Number of edges.
	 */
	public int m() {
		return m;
	}
	
	public Set<DirectedEdge> adj(final int v) {
		return adj.get(v);
	}
	
}
