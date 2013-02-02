package org.tullio.algo.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GenDigraph<T> implements Iterable<T> {

	/** 
	 * Set of outgoing edges for each vertex.  Each
     * set of edges is represented by a map from edges to doubles.
     */
    private final Map<T, Set<T>> graph = new HashMap<T, Set<T>>();
    
    /**
     * Add a vertex to the graph.
     * 
     * @param vertex Vertex to add.
     */
    public void addVertex(final T vertex) {
    	if (!graph.containsKey(vertex)) {
    		graph.put(vertex, new HashSet<T>());
    	}
    }
    
    /**
     * Add an edge to the graph.
     * 
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @throws InvalidEdgeFailure If any of the vertices is not in the graph.
     */
    public void addEdge(final T source, final T destination) {
    	validateEdge(source, destination);
    	graph.get(source).add(destination);
    }
    
    /**
     * Remove an edge from the graph.
     * 
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @throws InvalidEdgeFailure If any of the vertices is not in the graph.
     */
    public void removeEdge(final T source, final T destination) {
    	validateEdge(source, destination);
    	graph.get(source).remove(destination);
    }
    
    /**
     * Check whether an edge exists.
     * 
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @return {@code true} if the edge exists.
     * @throws InvalidEdgeFailure If any of the vertices is not in the graph.
     */
    public boolean edgeExists(final T source, final T destination) {
    	validateEdge(source, destination);
    	return graph.get(source).contains(destination);
    }
    
    /**
     * Return the destination vertices of all the edges leaving from a
     * given vertex.
     * 
     * @param vertex The source vertex.
     * @return The immutable set of destination vertices of the edges.
     */
    public Set<T> edgesFrom(final T vertex) {
    	final Set<T> out = graph.get(vertex);
    	if (out == null) {
    		return Collections.emptySet();
    	}
    	return Collections.unmodifiableSet(out);
    }
    
    /**
     * The set of vertices in the graph.
     */
    @Override
    public Iterator<T> iterator() {
    	return graph.keySet().iterator();
    }
    
    /**
     * @return A new graph that is the reverse of the current one.
     */
    public GenDigraph<T> reverse() {
    	GenDigraph<T> result = new GenDigraph<T>();

        /* Copy over the vertices. */
        for (T vertex : this)
            result.addVertex(vertex);

        /* Flip all the edges. */
        for (T vertex : this)
            for (T destination : this.edgesFrom(vertex)) {
                result.addEdge(destination, vertex);
            }
        
        return result;
    }
    
    /**
     * Make sure the source and destination vertices of an edge are in the
     * graph.
     * 
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @throws InvalidEdgeFailure If the invariant is not maintained.
     */
    private void validateEdge(final T source, final T destination) {
    	if (!graph.containsKey(source) || !graph.containsKey(destination)) {
    		throw new InvalidEdgeFailure("Both vertices must be in the graph");
    	}    	
    }
    
    public static class InvalidEdgeFailure extends RuntimeException {

		private static final long serialVersionUID = 1L;
    	
    	public InvalidEdgeFailure(final String message) {
    		super(message);
    	}
    }
}
