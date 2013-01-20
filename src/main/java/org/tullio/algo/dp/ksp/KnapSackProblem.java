package org.tullio.algo.dp.ksp;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class KnapSackProblem implements Iterable<KspItem> {

	private final int capacity;
	private final List<KspItem> items;
	
	public KnapSackProblem(final int sackSize, final List<KspItem> problemItems) {
		capacity = sackSize;
		items = Collections.unmodifiableList(problemItems);
	}
	
	public int sackSize() {
		return capacity;
	}
	
	public int numItems() {
		return items.size();
	}
	
	public KspItem item(int i) {
		return items.get(i);
	}
	
	public Iterator<KspItem> iterator() {
		return items.iterator();
	}
	
	@Override
	public String toString() {
		return String.format("KSP[N=%d,W=%d]", items.size(), capacity);
	}
}
