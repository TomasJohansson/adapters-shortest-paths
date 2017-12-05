package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;

import java.lang.reflect.Constructor;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

// PathFinderFactory<F extends PathFinder<P,E,V,W> , P extends Path<E,V,W> , E extends Edge<V, W> , V extends Vertex , W extends Weight>
public abstract class PathFinderFactoryBase
<F extends PathFinder<P, E,V,W> , P extends Path<E, V, W> ,  E extends Edge<V, W> , V extends Vertex , W extends Weight> 
implements PathFinderFactory<F , P, E , V , W> 
{

	private Constructor<F> declaredConstructor;
	
	public PathFinderFactoryBase(final Class clazz) {
		try {
			declaredConstructor = clazz.getDeclaredConstructor(Graph.class, GraphEdgesValidationDesired.class);
			declaredConstructor.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	protected boolean shouldUseReflectionConstructor() {
		return declaredConstructor != null;
	}	
	
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
	
	// TOOD: improve this reflection stuff ...
	protected F createWithReflectionConstructor(
		final Graph<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired			
	) {
		try {
			F newInstance = declaredConstructor.newInstance(graph, graphEdgesValidationDesired);
			return newInstance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}