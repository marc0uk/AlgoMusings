package org.tullio.algo.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class KspUtil {
	
	static KnapSackProblem readInput(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final String[] info =  br.readLine().split("\\s+");
			final int sackSize = Integer.parseInt(info[0]);
			final int numItems = Integer.parseInt(info[1]);
			final List<KspItem> items = new ArrayList<>(numItems);
			String line;
            while ((line = br.readLine()) != null) {
            	final String[] tokens = line.split("\\s+");
            	if (tokens.length != 2) {
                	throw new IOException("Invalid line, expecting 2 tokens:\n" + line);
                }
            	items.add(new KspItem(
            			// Value
            			Integer.parseInt(tokens[0]),
            			// Weight
            			Integer.parseInt(tokens[1])));
            }
            return new KnapSackProblem(sackSize, items);
		} finally {
			stream.close();
		}
	}
	
	static KspSolution readExpected(final InputStream stream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			final String[] info = br.readLine().split("\\s+");
			final int sol = Integer.parseInt(info[0]);
			final int numItems = Integer.parseInt(info[1]);
			final boolean[] flag = new boolean[numItems];
			int usedItems = 0;
			String line;
			int idx=0;
			while ((line = br.readLine()) != null) {
				final boolean used = Integer.parseInt(line) == 1;
				if (used) {
					usedItems++;
					flag[idx] = true;
				}
				idx++;
			}
			return new KspSolution(sol, usedItems, flag);
		} finally {
			stream.close();
		}
	}
}
