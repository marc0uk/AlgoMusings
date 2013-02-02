package org.tullio.algo.dp.tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TspDriver {
	
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
	
	private static double solve(final File file) {
		try {
			final long start = System.currentTimeMillis();
			final TspDp tsp = loadProblem(new FileInputStream(file));
			final double actual = tsp.solve();
			final long elapsed = System.currentTimeMillis() - start;
			System.out.println(String.format("Solution in: %.5f[sec]", (elapsed/1000D)));
			return actual;
		} catch (IOException e) {
			throw new RuntimeException("Failed", e);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("Please specify input file to read");
		}
		final File file = new File(args[0]);
		final double result = solve(file);
		System.out.println(String.format("Solution: %.3f", result));
		System.out.println(String.format("Rounded down : %d", ((int) Math.floor(result))));
		System.out.println(String.format("Rounded up   : %d", ((int) Math.ceil(result))));
	}
}
