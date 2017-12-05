package com.programmerare.shortestpaths.adapter.impl.yanqi;

import java.lang.reflect.Constructor;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathFinderFactoryBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * @author Tomas Johansson
 */
public class PathFinderFactoryYanQi<F extends PathFinder<P, E,V,W> , P extends Path<E, V, W> ,  E extends Edge<V, W> , V extends Vertex , W extends Weight> 
	extends PathFinderFactoryBase<F,P,E,V,W> 
	implements PathFinderFactory<F,P,E,V,W> 
{
	public F createPathFinder(
		final Graph<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		// TODO: try to get rid of the casting below ( warning: "Type safety: Unchecked cast from PathFinderYanQi<P,E,V,W> to F" )
		return (F) new PathFinderYanQi<P, E, V, W>(
			graph, 
			graphEdgesValidationDesired
		);
	}	
}