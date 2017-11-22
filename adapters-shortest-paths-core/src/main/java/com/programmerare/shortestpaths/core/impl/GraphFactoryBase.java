package com.programmerare.shortestpaths.core.impl;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

public abstract class GraphFactoryBase<T extends Edge> implements GraphFactory<T> {
	
	private final GraphEdgesValidator<T> graphEdgesValidator;
	
	protected GraphFactoryBase() {
		graphEdgesValidator = new GraphEdgesValidator<T>();
	}

	@Deprecated
	public final Graph<T> createGraph(final List<T> edges) {
		return createGraph(edges, GraphEdgesValidationDesired.YES);
	}

	/**
	 * @param edges
	 * @param graphEdgesValidationDesired should be NO (for performance reason) if validation has already been done
	 * @return
	 */
	public final Graph<T> createGraph(
		final List<T> edges, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		// Reason for avoiding the validation: If multiple invocations will be used, it is unnecessary to do the validation multiple times.
		// However, it is convenient if the default is to do validation internally without having to specify it.	
		if(graphEdgesValidationDesired == GraphEdgesValidationDesired.YES) {
			graphEdgesValidator.validateEdgesAsAcceptableInputForGraphConstruction(edges);	
		}
		// Prevondition to method below is that validation (as above) is performed i.e. 
		// the method below will NOT try to validate,
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
		
		return createGraphHook(edges, edgeMapper);	
	}

	protected abstract Graph<T> createGraphHook(List<T> edges, EdgeMapper<T> edgeMapper);
}