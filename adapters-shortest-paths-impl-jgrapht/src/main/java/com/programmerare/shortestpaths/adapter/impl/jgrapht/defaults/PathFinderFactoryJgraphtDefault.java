package com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults;

import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderFactoryJgraphtDefault 
	extends PathFinderFactoryJgrapht<
			PathFinderDefault, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			PathDefault,
			EdgeDefault, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactoryDefault 
{
	@Override
	public PathFinderDefault createPathFinder(
		final Graph<EdgeDefault, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		return new PathFinderJgraphtDefault(graph, graphEdgesValidationDesired);
	}
}