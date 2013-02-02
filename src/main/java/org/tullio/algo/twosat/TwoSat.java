package org.tullio.algo.twosat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tullio.algo.graph.GenDigraph;
import org.tullio.algo.graph.Kosaraju;
import org.tullio.algo.graph.SccFinder;

public class TwoSat {
	
	private final List<Clause> formula;
	private final SccFinder sccFinder;

	public TwoSat(final Collection<Clause> problem) {
		formula = Collections.unmodifiableList(new ArrayList<>(problem));
		sccFinder = new Kosaraju();
	}
	
	public boolean isSatiafiable() {
		final Set<Integer> vars = variables();
		final Map<Literal, Integer> scc = sccFinder.stronglyConnectedComponents(buildImplicationGraph(vars));
		for (int variable: vars) {
			final Literal l = new Literal(variable);
            if (scc.get(l).equals(scc.get(l.negation()))) {
                return false;
            }
		}
		return true;
	}
	
	/**
	 * @return The implication graph for the problem.
	 */
	private GenDigraph<Literal> buildImplicationGraph(final Set<Integer> variables) {
		final GenDigraph<Literal> graph = new GenDigraph<>();
		// First add each literal and its negation
		for (final int v : variables) {
			final Literal l = new Literal(v);
			graph.addVertex(l);
			graph.addVertex(l.negation());
		}
		/* From each Clause (A or B), add to edges to the graph:
		 * (~A -> B) 
		 * (~B -> A)
         */
		for (final Clause clause : formula) {
			graph.addEdge(clause.first().negation(), clause.second());
            graph.addEdge(clause.second().negation(), clause.first());
		}
		return graph;
	}
	
	/**
	 * @return Set of the variables in the problem.
	 */
	private Set<Integer> variables() {
		Set<Integer> variables = new HashSet<>();
		for (Clause clause: formula) {
			variables.add(clause.first().value());
			variables.add(clause.second().value());
		}
		return variables;
	}
}
