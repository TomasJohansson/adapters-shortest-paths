package com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults;

import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderFactoryJgraphtDefault 
	extends PathFinderFactoryJgrapht<
			PathFinder, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			Path,
			Edge, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactoryDefault 
{
	@Override
	public PathFinder createPathFinder(
		final Graph<Edge, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		return new PathFinderJgraphtDefault(graph, graphEdgesValidationDesired);
	}
}