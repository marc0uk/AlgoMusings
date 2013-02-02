/**
 * 
 */
package org.tullio.algo.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of Kosaraju's algorithm for finding strongly-connected
 * components (SCCs) of a graph in linear time.
 */
public final class Kosaraju implements SccFinder {
	
	@Override
	public <T> Map<T, Integer> stronglyConnectedComponents(GenDigraph<T> g) {
		
		/* 
		 * Run a DFS in the reverse graph to get the order in
         * which the nodes should be processed.
         */
        Stack<T> orderQueue = dfsVisitOrder(g.reverse());
        
        Map<T, Integer> result = new HashMap<T, Integer>();
        int iteration = 0;
        
        // Process the vertex queue : run a DFS on each unexplored vertex.
        while (!orderQueue.isEmpty()) {
            T vertex = orderQueue.pop();
            if (result.containsKey(vertex)) {
            	// Already explored : move on
                continue;
            }

            /* Run a DFS from this node, recording everything we visit as being
             * at the current level.
             */
            markReachableVertex(vertex, g, result, iteration);

            /* Bump up the number of the next SCC to label. */
            iteration++;
        }

        return result;
	}

	/**
     * @param g Graph to for which the DFS order is needed.
     * @return A stack of vertices in the order in which the DFS finished exploring them.
     */
    private <T> Stack<T> dfsVisitOrder(GenDigraph<T> g) {
        /* The resulting ordering of the vertices. */
        Stack<T> result = new Stack<T>();

        /* The set of vertices that we've visited so far. */
        Set<T> visited = new HashSet<T>();

        /* Fire off a DFS from each node. */
        for (T node: g)
            recExplore(node, g, result, visited);

        return result;
    }
    
    /**
     * Recursively marks all nodes reachable from the given node by a DFS with
     * the current label.
     *
     * @param v The starting vertex of the search.
     * @param g The graph to search.
     * @param result A map in which to associate nodes with labels.
     * @param label The label that we should assign each node in this SCC.
     */
    private <T> void markReachableVertex(T v, GenDigraph<T> g, Map<T, Integer> result, int label) {
        if (result.containsKey(v)) {
        	// Already explored, nothing to do.
        	return;
        }

        result.put(v, label);

        /* Explore all vertices reachable from here. */
        for (T dest: g.edgesFrom(v))
            markReachableVertex(dest, g, result, label);
    }
    
    /**
     * Recursively explores the given vertex id DFS fashion, adding it to the output
     * list once the exploration is complete.
     *
     * @param vertex Start vertex.
     * @param g Graph to traverse.
     * @param result Final vertex ordering.
     * @param visited Set of vertices visited so far.
     */
    private <T> void recExplore(T vertex, GenDigraph<T> g, Stack<T> result, Set<T> visited) {
        /* Vertex already explored, nothing to do. */
        if (visited.contains(vertex)) {
        	return;
        }

        visited.add(vertex);

        /* Recursively explore all the children of this vertex. */
        for (T destination: g.edgesFrom(vertex)) {
            recExplore(destination, g, result, visited);
        }

        // Exploration completed: add it to the list of visited vertices.
        result.push(vertex);
    }
}
