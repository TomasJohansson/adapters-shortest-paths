package com.programmerare.shortestpaths.core.impl;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

public abstract class PathFinderBase <T extends Edge> implements PathFinder<T> {

	private final Graph<T> graph;
	private final EdgeMapper<T> edgeMapper;
	
	protected PathFinderBase(
		final Graph<T> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired			
	) {
		this.graph = graph;
		//final List<T> edges = graph.getAllEdges();
		
		// Reason for avoiding the validation: If multiple invocations will be used, it is unnecessary to do the validation multiple times.
		// However, it is convenient if the default is to do validation internally without having to specify it.	
		if(graphEdgesValidationDesired == GraphEdgesValidationDesired.YES) {
			GraphEdgesValidator.validateEdgesForGraphCreation(graph.getEdges());
		}
		// Prevondition to method below is that validation (as above) is performed i.e. 
		// the method below will NOT try to validate,
//		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);		
		edgeMapper = EdgeMapper.createEdgeMapper(graph.getEdges());
	}


	/**
	 * final method to enforce the validation, and then forward to the hook method for the implementations
	 */
	public final List<Path<T>> findShortestPaths(
			final Vertex startVertex, 
			final Vertex endVertex, 
			final int maxNumberOfPaths
		) {
		// TODO: validate bot input and ouutput (maybe)
		
		// TODO 1 : validate here first that the vertices are part of the graph

		final List<Path<T>> shortestPaths = findShortestPathHook(
			startVertex, 
			endVertex, 
			maxNumberOfPaths				
		);

	    // TDOO 2 maybe: use graphEdgesValidator.validateAllPathsOnlyContainEdgesDefinedInGraph
	    // but maybe also it should be optional to perform that validation of the output list of paths
		//Graph<T> graph = getGraph();
		
		return shortestPaths;
	}

	public T getOriginalEdgeInstance(final String startVertexId, final String endVertexId) {
		return edgeMapper.getOriginalEdgeInstance(startVertexId, endVertexId);
	}

	protected Graph<T> getGraph() {
		return graph;
	}

	// "Hook" : see the Template Method Design Pattern
	protected abstract List<Path<T>> findShortestPathHook(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths);	
}