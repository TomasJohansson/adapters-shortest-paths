package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

public abstract class PathFinderFactoryBase<T extends Edge> implements PathFinderFactory<T> {
	
	protected PathFinderFactoryBase() { }

	public PathFinder<T> createPathFinder(
		final Graph<T> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		final List<T> edges = graph.getAllEdges();

		// Reason for avoiding the validation: If multiple invocations will be used, it is unnecessary to do the validation multiple times.
		// However, it is convenient if the default is to do validation internally without having to specify it.	
		if(graphEdgesValidationDesired == GraphEdgesValidationDesired.YES) {
			GraphEdgesValidator.validateEdgesForGraphCreation(edges);
		}
		// Prevondition to method below is that validation (as above) is performed i.e. 
		// the method below will NOT try to validate,
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
		
		return createPathFinderHook(graph, edgeMapper);
	}
	
	/**
	 * @param edges
	 * @param graphEdgesValidationDesired should be NO (for performance reason) if validation has already been done
	 * @return
	 */
	public final PathFinder<T> createPathFinder(
		final List<T> edges, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		final Graph<T> graph = createGraph(edges);
		return createPathFinder(graph, graphEdgesValidationDesired);
	}

	protected abstract PathFinder<T> createPathFinderHook(Graph<T> graph, EdgeMapper<T> edgeMapper);
}