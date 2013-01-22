package org.tullio.algo.dp.tsp;

import static com.google.common.base.Preconditions.checkArgument;

public class TspCity {

	private final byte id;
	private final double x;
	private final double y;
	
	/**
	 * @param idx City identifier, starting from 1.
	 * @param xCoord X coordinate of the city.
	 * @param yCoord Y coordinate of the city.
	 */
	public TspCity(final byte idx, final double xCoord, final double yCoord) {
		checkArgument(idx > 0, "Argument was %s but expected strictly positive", idx);
		id = idx;
		x = xCoord;
		y = yCoord;
	}
	
	/**
	 * @return Unique city identifier.
	 */
	public byte id() {
		return id;
	}
	
	/**
	 * Compute the Euclidean distance between this and another city.
	 * 
	 * @param other The other city.
	 * @return The euclidean distance.
	 */
	public double distance(final TspCity other) {
		final double dx = x - other.x;
		final double dy = y - other.y;
		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	@Override
	public String toString() {
		return String.format("City[%d]={%.5f, %.5f}", id, x, y);
	}
}
