package org.tullio.algo.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public final class GraphBuilder<V> {

	private final int n;
	private List<Edge<V>> edges;
    
    public GraphBuilder() {
    	n = -1;
    	edges = new ArrayList<>();
    }
    
    public GraphBuilder(final int numVertices) {
    	n = numVertices;
    	edges = new ArrayList<>(n);
    }
    
    public void add(final Edge<V> edge) {
    	edges.add(edge);
    }
    
    public Graph<V> build() {
    	return new Graph<>(n(), edges);
    }
    
    public Graph<V> build(final Comparator<Edge<V>> edgeComparator) {
    	return new Graph<>(n(), edges, edgeComparator);
    }
    
    private int n() {
    	return n > 0 ?
    			n : 
    			computeN();
    }
    
    private int computeN() {
        final HashSet<V> vs = new HashSet<>(edges.size());
        for (final Edge<V> e : edges) {
                vs.add(e.u());
                vs.add(e.v());
        }
        return vs.size();
    }
}
