package org.tullio.algo.twosat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TwoSatTest {
	
	private static final String P1 = "org/tullio/algo/twosat/2sat1.txt";
	private static final String P2 = "org/tullio/algo/twosat/2sat2.txt";
	private static final String P3 = "org/tullio/algo/twosat/2sat3.txt";
	private static final String P4 = "org/tullio/algo/twosat/2sat4.txt";
	private static final String P5 = "org/tullio/algo/twosat/2sat5.txt";
	private static final String P6 = "org/tullio/algo/twosat/2sat6.txt";
	
	@Test
	public void problemSet() {
		final boolean sol1 = isSatisfied(P1);
		final boolean sol2 = isSatisfied(P2);
		final boolean sol3 = isSatisfied(P3);
		final boolean sol4 = isSatisfied(P4);
		final boolean sol5 = isSatisfied(P5);
		final boolean sol6 = isSatisfied(P6);
		System.out.println(String.format("%d%d%d%d%d%d",
				toInt(sol1),
				toInt(sol2),
				toInt(sol3),
				toInt(sol4),
				toInt(sol5),
				toInt(sol6)));
	}
	
	private int toInt(final boolean b) {
		if (b) {
			return 1;
		}
		return 0;
	}
	
	private boolean isSatisfied(final String input) {
		final long start = System.currentTimeMillis();
		final List<Clause> formula = readFormula(ClassLoader.getSystemResourceAsStream(input));
		final TwoSat ts = new TwoSat(formula);
		final boolean sat = ts.isSatiafiable();
		System.out.println(String.format("2Sat solution in %.3f [sec]", 
				(System.currentTimeMillis() - start)/1000D));
		System.gc();
		return sat;
	}

	private List<Clause> readFormula(final InputStream stream) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final int size = Integer.parseInt(br.readLine().trim());
			final List<Clause> formula = new ArrayList<>(size);
			String line;
			while ((line = br.readLine()) != null) {
				final String[] tokens = line.split("\\s+");
				if (tokens.length != 2) {
                	throw new IOException("Invalid line, expecting 2 tokens:\n" + line);
                }
				formula.add(new Clause(
						new Literal(Integer.parseInt(tokens[0])), 
						new Literal(Integer.parseInt(tokens[1]))));
			}
			return formula;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load formula", e);
		}
	}
}
