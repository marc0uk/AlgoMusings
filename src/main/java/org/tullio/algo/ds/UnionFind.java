package org.tullio.algo.ds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Union find data structure.
 * 
 * Mainly for use in Kruskal's algorithm.
 */
public final class UnionFind<T> {

	private final Map<T, UnionFindItem<T>> items;
	
	private static <T> Map<T, UnionFindItem<T>> makeSet(final Iterable<T> data) {
		final Map<T, UnionFindItem<T>> map = new HashMap<>();
		for (final T item : data) {
			map.put(item, new UnionFindItem<>(item));
		}
		return Collections.unmodifiableMap(map);
	}
	
	public UnionFind(final Iterable<T> data) {
		items = makeSet(data);
	}
	
	/**
	 * Find the parent of item x applying path compression.
	 * 
	 * @param x The item for which the parent shall be found.
	 * @return The parent.
	 */
	public T find(final T x) {
		final UnionFindItem<T> ufx = items.get(x);
		if (!ufx.parent().equals(x)) {
			ufx.setParent(find(ufx.parent()));
		}
		return ufx.parent();
	}
	
	/**
	 * Fuse the two partitions represented by {@code x} and {@code y}
	 * using union-by-rank.
	 * 
	 * @param x Parent of first partition.
	 * @param y Parent of second partition.
	 */
	public void union(final T x, T y) {
		final UnionFindItem<T> ufx = items.get(x);
		final UnionFindItem<T> ufy = items.get(y);
		if (ufx.parent().equals(ufy.parent())) {
			return;
		}
		final UnionFindItem<T> xp = items.get(ufx.parent());
		final UnionFindItem<T> yp = items.get(ufy.parent());
		// x and y are not already in same set. Merge them.
		if (xp.rank() < yp.rank()) {
			ufx.setParent(ufy.parent());
		} else if (xp.rank() > yp.rank()) {
			ufy.setParent(ufx.parent());
		} else {
			ufy.setParent(ufx.parent());
			xp.incrementRank();
		}
	}
}
