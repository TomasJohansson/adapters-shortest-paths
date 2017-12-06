package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;

import java.util.List;

import com.programmerare.shortestpaths.core.api.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathGenerics;
import com.programmerare.shortestpaths.core.api.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public abstract class PathFinderFactoryBase
	<F extends PathFinderGenerics<P, E,V,W> , P extends PathGenerics<E, V, W> ,  E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> 
	implements PathFinderFactory<F , P, E , V , W> 
{
	protected PathFinderFactoryBase() { }

	/**
	 * @param edges
	 * @param graphEdgesValidationDesired should be NO (for performance reason) if validation has already been done
	 * @return
	 */
	public final F createPathFinder(
		final List<E> edges, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		final Graph<E, V, W> graph = createGraph(edges);
		return createPathFinder(graph, graphEdgesValidationDesired); // the overloaded method must be implemented by subclasses
	}
}