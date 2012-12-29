package org.tullio.algo.ds;

/**
 * Internal representation of a UnionFind item
 * wrapping an actual type {@code <T>}s
 * 
 * @param <T> Set type
 */
final class UnionFindItem<T> {

	private T parent;
	private int rank;
	
	UnionFindItem(final T parentItem) {
		parent = parentItem;
		rank = 0;
	}
	
	void setParent(final T newParent) {
		parent = newParent;
	}
	
	void incrementRank() {
		rank++;
	}
	
	public T parent() {
		return parent;
	}
	
	public int rank() {
		return rank;
	}
}
