package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public abstract class PathFinderFactoryBase<T extends Edge> implements PathFinderFactory<T> {
	
	protected PathFinderFactoryBase() { }

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
		return createPathFinder(graph, graphEdgesValidationDesired); // the overloaded method must be implemented by subclasses
	}

}