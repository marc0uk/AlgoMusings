package org.tullio.algo.dp.tsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TspMusings {
	
	private static final String TSP_4 = "org/tullio/algo/dp/tsp/tsp04.txt";
	private static final String TSP_5 = "org/tullio/algo/dp/tsp/tsp05.txt";
	private static final String TSP_6 = "org/tullio/algo/dp/tsp/tsp06.txt";
	private static final String TSP_25 = "org/tullio/algo/dp/tsp/tsp25.txt";
	
	@Test
	public void tsp4Cities() throws IOException {
		solveAndValidate(TSP_4, 22D);
	}
	
	@Test
	public void tsp5Cities() throws IOException {
		solveAndValidate(TSP_5, 15D);
	}
	
	@Test
	public void tsp6Cities() throws IOException {
		solveAndValidate(TSP_6, 32D);
	}
	
	@Test
	@Ignore
	public void tsp25Cities() throws IOException {
		final double result = solve(TSP_25);
		System.out.println(String.format("Solution: %.3f", result));
		System.out.println(String.format("Rounded : %d", Math.floor(result)));
	}
	
	private void solveAndValidate(final String input, final double expected) {
		try {
			final double actual = solve(input);
			Assert.assertEquals("Wrong tour length", expected, actual, 1E-10);
			System.out.println(String.format("Tour length = %.1f", actual));
		} catch (RuntimeException e) {
			e.printStackTrace();
			Assert.fail("Exception");
		}
	}
	
	private double solve(final String input) {
		try {
			final long start = System.currentTimeMillis();
			final TspDp tsp = loadProblem(ClassLoader.getSystemResourceAsStream(input));
			final double actual = tsp.solve();
			final long elapsed = System.currentTimeMillis() - start;
			System.out.println(String.format("Solution in: %.5f[sec]", (elapsed/1000D)));
			return actual;
		} catch (IOException e) {
			throw new RuntimeException("Failed", e);
		}
	}

	private static TspDp loadProblem(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final int numCities = Integer.parseInt(br.readLine().trim());
			String line;
			byte idx = 1;
			final List<TspCity> cities = new ArrayList<>(numCities);
            while ((line = br.readLine()) != null) {
            	String[] tokens = line.split("\\s+");
            	if (tokens.length != 2) {
                	throw new IOException("Invalid line, expecting 2 tokens:\n" + line);
                }
            	cities.add(new TspCity(idx++, Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])));
            }
            if (cities.size() != numCities) {
            	throw new IOException(String.format("Corrupted file expecting %d cities but read only %d", numCities, cities.size()));
            }
            return new TspDp(cities);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read problem", e);
		}
	}
}
