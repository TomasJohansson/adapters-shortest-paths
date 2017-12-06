package com.programmerare.shortestpaths.adapter.jgrapht.generics;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderFactoryGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderFactoryGenericsBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * @author Tomas Johansson
 */
public class PathFinderFactoryJgraphtGenerics<F extends PathFinderGenerics<P,E,V,W> , P extends PathGenerics<E, V, W> ,  E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight>
	extends PathFinderFactoryGenericsBase<F,P,E,V,W> 
	implements PathFinderFactoryGenerics<F,P,E,V,W>
{
	public F createPathFinder(
		final GraphGenerics<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		// TODO: try to get rid of the casting below ( warning: "Type safety: Unchecked cast from PathFinderJgrapht<P,E,V,W> to F" )
		return (F) new PathFinderJgraphtGenerics<P, E, V, W>(
			graph, 
			graphEdgesValidationDesired
		);
	}
}