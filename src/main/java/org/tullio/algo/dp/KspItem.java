package org.tullio.algo.dp;

public class KspItem {

	public final int value;
	public final int weight;
	
	public KspItem(final int itemValue, final int itemWeight) {
	value = itemValue;
		weight = itemWeight;
	}
	
	public int value() {
		return value;
	}
	
	public int weight() {
		return weight;
	}
}
