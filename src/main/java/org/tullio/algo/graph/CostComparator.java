package org.tullio.algo.graph;

import java.util.Comparator;

public final class CostComparator<V> implements Comparator<Edge<V>> {

	@Override
	public int compare(Edge<V> o1, Edge<V> o2) {
		return o1.cost() - o2.cost();
	}

}
