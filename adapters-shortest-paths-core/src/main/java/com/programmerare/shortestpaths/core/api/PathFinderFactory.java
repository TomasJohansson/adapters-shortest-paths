package com.programmerare.shortestpaths.core.api;

import java.util.List;

import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * "Target" interface for Adapter implementations 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
  */
public interface PathFinderFactory<T extends Edge> {

	PathFinder<T> createGraph(
		List<T> edges, 
		GraphEdgesValidationDesired graphEdgesValidationDesired
	);
	
}
