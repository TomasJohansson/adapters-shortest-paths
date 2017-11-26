package com.programmerare.shortestpaths.adapter.impl.bsmock;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.impl.EdgeMapper;
import com.programmerare.shortestpaths.core.impl.PathFinderFactoryBase;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class PathFinderFactoryBsmock<T extends Edge> extends PathFinderFactoryBase<T> implements PathFinderFactory<T> {

	protected PathFinder<T> createPathFinderHook(final List<T> edges, final EdgeMapper<T> edgeMapper) {

		// "Adaptee" https://en.wikipedia.org/wiki/Adapter_pattern
		final edu.ufl.cise.bsmock.graph.Graph graphAdaptee = new edu.ufl.cise.bsmock.graph.Graph();
		
		for (final T edge : edges) {
			graphAdaptee.addEdge(
				edge.getStartVertex().getVertexId(), 
				edge.getEndVertex().getVertexId(), 
				edge.getEdgeWeight().getWeightValue()
			);	
		}
		return new PathFinderBsmock(graphAdaptee, edgeMapper);
	}
}