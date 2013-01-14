package org.tullio.algo.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Optional;

public class ShortestShorTest {
	
	private static final String DG_EX1 = "org/tullio/algo/apsp/dgValid.txt";
	private static final String DG_EX2 = "org/tullio/algo/apsp/dgWithNegCycle.txt";
	private static final String DG_1 = "org/tullio/algo/apsp/dg1.txt";
	private static final String DG_2 = "org/tullio/algo/apsp/dg2.txt";
	private static final String DG_3 = "org/tullio/algo/apsp/dg3.txt";
	
	static WeightedDigraph readGraph(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final String[] info =  br.readLine().split("\\s+");
			final int vertices = Integer.parseInt(info[0]);
			final int edges = Integer.parseInt(info[1]);
			final List<DirectedEdge> items = new ArrayList<>(edges);
			String line;
            while ((line = br.readLine()) != null) {
            	final String[] tokens = line.split("\\s+");
            	if (tokens.length != 3) {
                	throw new IOException("Invalid line, expecting 3 tokens:\n" + line);
                }
            	items.add(new DirectedEdge(
            			// Source
            			Integer.parseInt(tokens[0]),
            			// Destination
            			Integer.parseInt(tokens[1]),
            			// Length
            			Integer.parseInt(tokens[2])));
            }
            return new WeightedDigraph(vertices, edges, items);
		} finally {
			stream.close();
		}
	}

	@Test
	public void shortestShortestExists() throws IOException {
		final Optional<Double> ssp = evaluate(DG_EX1);
		Assert.assertTrue(ssp.isPresent());
		Assert.assertEquals(-7D, ssp.get(), 1E-12);
	}

	@Test
	public void hasNegativeCostCycle() throws IOException {
		final Optional<Double> ssp = evaluate(DG_EX2);
		Assert.assertFalse(ssp.isPresent());
	}
	
	@Ignore
	@Test
	public void multiShort() throws IOException {
		System.out.println("Solving problem 1");
		final long s1 = System.currentTimeMillis();
		final Optional<Double> ssp1 = evaluate(DG_1);
		final long eta1 = System.currentTimeMillis() - s1;
		System.out.println("Solving problem 2");
		final long s2 = System.currentTimeMillis();
		final Optional<Double> ssp2 = evaluate(DG_2);
		final long eta2 = System.currentTimeMillis() - s2;
		System.out.println("Solving problem 3");
		final long s3 = System.currentTimeMillis();
		final Optional<Double> ssp3 = evaluate(DG_3);
		final long eta3 = System.currentTimeMillis() - s3;
		System.out.println(String.format("SSP1[%.3f]: ", eta1 / 1000D) + (ssp1.isPresent() ? ssp1.get() : "NegCyc"));
		System.out.println(String.format("SSP2[%.3f]: ", eta2 / 1000D) + (ssp2.isPresent() ? ssp2.get() : "NegCyc"));
		System.out.println(String.format("SSP3[%.3f]: ", eta3 / 1000D) + (ssp3.isPresent() ? ssp3.get() : "NegCyc"));
	}
	
	private Optional<Double> evaluate(final String resource) {
		try {
			final WeightedDigraph g = readGraph(ClassLoader.getSystemResourceAsStream(resource));
			final FloydWarshall fw = new FloydWarshall(g);
			return fw.solve();
		} catch (IOException e) {
			throw new RuntimeException("Failed to read graph", e);
		}
	}
}
