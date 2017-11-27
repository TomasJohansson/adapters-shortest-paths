package com.programmerare.shortestpaths.adapter.impl.yanqi;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.impl.PathFinderFactoryBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * @author Tomas Johansson
 */
public final class PathFinderFactoryYanQi<T extends Edge> extends PathFinderFactoryBase<T> implements PathFinderFactory<T> {
	
	public PathFinder<T> createPathFinder(
		final Graph<T> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		return new PathFinderYanQi<T>(
			graph, 
			graphEdgesValidationDesired
		);		
	}
	
}