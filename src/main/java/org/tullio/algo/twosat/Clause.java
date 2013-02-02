package org.tullio.algo.twosat;

public class Clause {

	private final Literal one;
	private final Literal two;
	
	public Clause(final Literal first, final Literal second) {
		one = first;
		two = second;
	}
	
	public Literal first() {
		return one;
	}
	
	public Literal second() {
		return two;
	}
	
	@Override
	public String toString() {
		return String.format("(%d v %d)", one.value(), two.value());
	}
}
