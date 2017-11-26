package com.programmerare.shortestpaths.core.impl;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

public abstract class PathFinderFactoryBase<T extends Edge> implements PathFinderFactory<T> {
	
	protected PathFinderFactoryBase() { }

	/**
	 * @param edges
	 * @param graphEdgesValidationDesired should be NO (for performance reason) if validation has already been done
	 * @return
	 */
	public final PathFinder<T> createGraph(
		final List<T> edges, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		// Reason for avoiding the validation: If multiple invocations will be used, it is unnecessary to do the validation multiple times.
		// However, it is convenient if the default is to do validation internally without having to specify it.	
		if(graphEdgesValidationDesired == GraphEdgesValidationDesired.YES) {
			GraphEdgesValidator.validateEdgesForGraphCreation(edges);
		}
		// Prevondition to method below is that validation (as above) is performed i.e. 
		// the method below will NOT try to validate,
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
		
		return createGraphHook(edges, edgeMapper);	
	}

	protected abstract PathFinder<T> createGraphHook(List<T> edges, EdgeMapper<T> edgeMapper);
}