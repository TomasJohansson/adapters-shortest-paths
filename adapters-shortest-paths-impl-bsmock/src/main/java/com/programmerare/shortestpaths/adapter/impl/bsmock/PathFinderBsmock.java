package com.programmerare.shortestpaths.adapter.impl.bsmock;

import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathFinderBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

import edu.ufl.cise.bsmock.graph.ksp.Yen;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class PathFinderBsmock<T extends Edge> extends PathFinderBase<T> implements PathFinder<T> {
	
	private final edu.ufl.cise.bsmock.graph.Graph graphAdaptee;
	private final Yen yenAlgorithm;	
	
	PathFinderBsmock(
		final Graph<T> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired		
	) {
		super(graph, graphEdgesValidationDesired);
		this.yenAlgorithm = new Yen();
		this.graphAdaptee = new edu.ufl.cise.bsmock.graph.Graph();
		populateGraphAdapteeWithEdges();
	}
	
	private void populateGraphAdapteeWithEdges() {
		final List<T> edges = this.getGraph().getEdges();
		for (final T edge : edges) {
			this.graphAdaptee.addEdge(
				edge.getStartVertex().getVertexId(), 
				edge.getEndVertex().getVertexId(), 
				edge.getEdgeWeight().getWeightValue()
			);	
		}
	}

	@Override
	protected List<Path<T>> findShortestPathHook(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path<T>> paths = new ArrayList<Path<T>>();		
		final List<edu.ufl.cise.bsmock.graph.util.Path> pathList = yenAlgorithm.ksp(
			graphAdaptee, 
			startVertex.getVertexId(), 
			endVertex.getVertexId(), 
			maxNumberOfPaths
		);
		for (edu.ufl.cise.bsmock.graph.util.Path path : pathList) {
			final LinkedList<edu.ufl.cise.bsmock.graph.Edge> listOfEdges = path.getEdges();
			final List<T> edges = new ArrayList<T>();
			for (edu.ufl.cise.bsmock.graph.Edge edgeAdaptee : listOfEdges) {
				final T edge = getOriginalEdgeInstance(edgeAdaptee);
				edges.add(
					edge
				);				
			}
			final Weight totalWeight = createWeight(path.getTotalCost());
			paths.add(createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private T getOriginalEdgeInstance(final edu.ufl.cise.bsmock.graph.Edge edgeAdaptee) {
		return super.getOriginalEdgeInstance(edgeAdaptee.getFromNode(), edgeAdaptee.getToNode());
	}
}