package org.tullio.algo.graph;

public final class DirectedEdge {

	private final int source;
	private final int destination;
	private final double length;
	
	public DirectedEdge(final int sourceVertex, final int destinationVertex, final double edgeLength) {
		source = sourceVertex;
		destination = destinationVertex;
		length = edgeLength;
	}
	
	public int source() {
		return source;
	}
	
	public int destination() {
		return destination;
	}
	
	public double length() {
		return length;
	}
}
