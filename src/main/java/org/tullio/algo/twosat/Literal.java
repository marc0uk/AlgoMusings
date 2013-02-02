package org.tullio.algo.twosat;

public class Literal {

	private final int value;
	
	public Literal(final int literal) {
		value = literal;
	}
	
	public int value() {
		return value;
	}
	
	public boolean isPositive() {
		return value > 0;
	}
	
	public Literal negation() {
		return new Literal(-value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Literal)) {
			return false;
		}
		final Literal other = Literal.class.cast(obj);
		return value == other.value;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
}
