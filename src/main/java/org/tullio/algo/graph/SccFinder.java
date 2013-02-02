/**
 * 
 */
package org.tullio.algo.graph;

import java.util.Map;

/**
 * Compute the strongly connected components of a directed graph.
 */
public interface SccFinder {

	/**
	 * @param g Directed graph.
     * @return A map from the vertices of that graph to the connected component 
     * they belong identified by an integer.
	 */
	public <T> Map<T, Integer> stronglyConnectedComponents(final GenDigraph<T> g);
}
