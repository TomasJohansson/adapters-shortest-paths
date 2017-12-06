package com.programmerare.shortestpaths.adapter.impl.yanqi.defaults;

import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderFactoryYanQiDefault 
	extends PathFinderFactoryYanQi<
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
		return new PathFinderYanQiDefault(graph, graphEdgesValidationDesired);
	}	
}