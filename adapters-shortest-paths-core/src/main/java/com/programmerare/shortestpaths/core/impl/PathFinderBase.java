package com.programmerare.shortestpaths.core.impl;

import java.lang.reflect.Constructor;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;
import com.programmerare.shortestpaths.core.validation.GraphValidationException;

// public interface PathFinder<P extends Path<E,V,W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> {
public abstract class PathFinderBase<E extends Edge<V, W> , V extends Vertex , W extends Weight> implements PathFinder<E, V, W> {

	private final Graph<E, V, W> graph;
	private final EdgeMapper<E, V, W> edgeMapper;
	
	protected PathFinderBase(
		final Graph<E, V, W> graph, 
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
	public final List<Path<E, V, W>> findShortestPaths(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		validateThatBothVerticesArePartOfTheGraph(startVertex, endVertex);
		
		final List<Path<E, V, W>> shortestPaths = findShortestPathHook(
			startVertex, 
			endVertex, 
			maxNumberOfPaths				
		);

		// TODO: Maybe it should be optional to perform this test (in a parameter)
		validateThatAllEdgesInAllPathsArePartOfTheGraph(shortestPaths);
		
		return shortestPaths;
	}

	void validateThatAllEdgesInAllPathsArePartOfTheGraph(final List<Path<E, V, W>> paths) {
		for (Path<E, V, W> path : paths) {
			List<E> edgesForPath = path.getEdgesForPath();
			for (E e : edgesForPath) {
				if(!graph.containsEdge(e)) {
					// potential improvement: Use Notification pattern to collect all (if more than one) errors instead of throwing at the first error
					throw new GraphValidationException("Edge in path is not part of the graph: " + e);
				}
			}
		}
	}


	private void validateThatBothVerticesArePartOfTheGraph(final V startVertex, final V endVertex) {
		// potential improvement: Use Notification pattern to collect all (if more than one) errors instead of throwing at the first error
		if(!graph.containsVertex(startVertex)) {
			throwExceptionBecauseVertexNotIncludedInGraph("start", startVertex);
		}
		if(!graph.containsVertex(endVertex)) {
			throwExceptionBecauseVertexNotIncludedInGraph("end", endVertex);
		}		
	}


	/**
	 * @param startOrEndmessagePrefix intended to be one of the strings "start" or "end"
	 * @param startVertex
	 */
	private void throwExceptionBecauseVertexNotIncludedInGraph(final String startOrEndmessagePrefix, final V vertex) {
		throw new GraphValidationException(startOrEndmessagePrefix + " vertex is not part of the graph: " + vertex);
	}


	public E getOriginalEdgeInstance(final String startVertexId, final String endVertexId) {
		return edgeMapper.getOriginalEdgeInstance(startVertexId, endVertexId);
	}

	protected Graph<E, V, W> getGraph() {
		return graph;
	}

	// "Hook" : see the Template Method Design Pattern
	protected abstract List<Path<E, V, W>> findShortestPathHook(V startVertex, V endVertex, int maxNumberOfPaths);
	
	protected W createWeightInstance(final double totalCost, final Class<? extends Weight> weightClass) {
		// TODO maybe: reflection is currently ALWAYS used.  Maybe use a special case for direct instantiating Weight if it is WeightImpl
		try {
			final Constructor<? extends Weight> constructor = weightClass.getDeclaredConstructor(double.class);
			constructor.setAccessible(true);
			final W w = (W)constructor.newInstance(totalCost);
			return w;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// convenience method
	protected W createWeightInstance(final double totalCost, final List<E> edgesUsedForDeterminingWeightClass) {
		// TODO maybe: reflection is currently ALWAYS used.  Maybe use a special case for direct instantiating Weight if it is WeightImpl
		final W w = edgesUsedForDeterminingWeightClass.get(0).getEdgeWeight();
		final Class<? extends Weight> weightClass = w.getClass();
		return createWeightInstance(totalCost, weightClass);
	}
	
}