package com.programmerare.shortestpaths.core.api;

import java.util.List;

import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * "Target" interface for Adapter implementations 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
  */
public interface PathFinderFactory<F extends PathFinder<P,E,V,W> , P extends Path<E, V, W> ,  E extends Edge<V, W> , V extends Vertex , W extends Weight> {  
	
	F createPathFinder(
		Graph<E, V, W> graph, 
		GraphEdgesValidationDesired graphEdgesValidationDesired
	);
	
	F createPathFinder(
		List<E> edges, 
		GraphEdgesValidationDesired graphEdgesValidationDesired
	);
}