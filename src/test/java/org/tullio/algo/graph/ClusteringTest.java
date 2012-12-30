package org.tullio.algo.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

public class ClusteringTest {

	@Test
	public void maxCostIsCorrect() throws IOException {
		final Graph<Integer> g = read(ClassLoader.getSystemResourceAsStream("org/tullio/algo/ds/smallGraph.txt"));
		Assert.assertEquals(63, Clustering.findMaxCost(4, g));
	}
	
	@Test
	public void maxCostFullSizeTest() throws IOException {
		final Graph<Integer> g = read(ClassLoader.getSystemResourceAsStream("org/tullio/algo/ds/largeGraph.txt"));
		Assert.assertEquals(106, Clustering.findMaxCost(4, g));
	}

	/**
	 * Read edge-list ASCII representation of graph where first row is the number of vertices.
	 * 
	 * @param stream Input stream.
	 * @return Parsed grapg.
	 * @throws IOException If the operation fails.
	 */
	public static Graph<Integer> read(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final int n = Integer.parseInt(br.readLine());
			final GraphBuilder<Integer> gb = new GraphBuilder<>(n);
			String line;
            while ((line = br.readLine()) != null) {
            	final String[] tokens = line.split("\\s+");
                if (tokens.length != 3) {
                	throw new IOException("Invalid line, expecting 3 tokens:\n" + line);
                }
                gb.add(new Edge<>(
                        Integer.parseInt(tokens[0]), 
                        Integer.parseInt(tokens[1]),
                        Integer.parseInt(tokens[2])));
            }
            return gb.build();
		} finally {
			stream.close();
		}
	}
}
