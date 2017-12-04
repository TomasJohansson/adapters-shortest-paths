package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathFinderFactoryBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * @author Tomas Johansson
 */
public final class PathFinderFactoryJgrapht<F extends PathFinder<E,V,W> , E extends Edge<V, W> , V extends Vertex , W extends Weight>
extends PathFinderFactoryBase<F , E , V  , W > 
implements PathFinderFactory<F , E , V  , W >
{

	public F createPathFinder(
		final Graph<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		final PathFinder<E, V, W> pathFinderBsmock = new PathFinderJgrapht<E, V, W>(
			graph, 
			graphEdgesValidationDesired
		);
		return (F)pathFinderBsmock;		
	}
	
}