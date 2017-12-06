package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.api.generics.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderFactoryBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * @author Tomas Johansson
 */
public class PathFinderFactoryJgrapht<F extends PathFinderGenerics<P,E,V,W> , P extends PathGenerics<E, V, W> ,  E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight>
	extends PathFinderFactoryBase<F,P,E,V,W> 
	implements PathFinderFactory<F,P,E,V,W>
{
	public F createPathFinder(
		final Graph<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		// TODO: try to get rid of the casting below ( warning: "Type safety: Unchecked cast from PathFinderJgrapht<P,E,V,W> to F" )
		return (F) new PathFinderJgrapht<P, E, V, W>(
			graph, 
			graphEdgesValidationDesired
		);
	}
}