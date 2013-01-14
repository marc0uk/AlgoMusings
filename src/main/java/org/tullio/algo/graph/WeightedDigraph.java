package org.tullio.algo.graph;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class WeightedDigraph {

	private final int n;
	private final int m;
	private final SetMultimap<Integer, DirectedEdge> adj;
	
	public WeightedDigraph(final int vertices, final int edges, final Collection<DirectedEdge> allEdges) {
		n = vertices;
		m = edges;
		adj = HashMultimap.create();
		for (final DirectedEdge e : allEdges) {
			adj.put(e.source(), e);
		}
	}
	
	public int n() {
		return n;
	}
	
	public int m() {
		return m;
	}
	
	public Set<DirectedEdge> adj(final int v) {
		return adj.get(v);
	}
	
}
