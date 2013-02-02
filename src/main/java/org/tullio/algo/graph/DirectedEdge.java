package org.tullio.algo.graph;

/**
 * A directed edge using {@code int} vertex identifiers
 * and a floating point length (can be negative).
 */
public final class DirectedEdge {

	private final int source;
	private final int destination;
	private final double length;
	
	/**
	 * @param sourceVertex Identifier of the source vertex.
	 * @param destinationVertex Identifier of the destination vertex.
	 * @param edgeLength Edge length.
	 */
	public DirectedEdge(final int sourceVertex, final int destinationVertex, final double edgeLength) {
		source = sourceVertex;
		destination = destinationVertex;
		length = edgeLength;
	}
	
	/**
	 * @return Identifier of the source vertex.
	 */
	public int source() {
		return source;
	}
	
	/**
	 * @return Identifier of the destination vertex.
	 */
	public int destination() {
		return destination;
	}
	
	/**
	 * @return Edge length.
	 */
	public double length() {
		return length;
	}
}
