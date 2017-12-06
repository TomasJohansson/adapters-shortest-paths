package com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults;

import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderJgrapht;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderJgraphtDefault 
	extends PathFinderJgrapht<
		PathDefault,
		EdgeDefault, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinderDefault
{
	protected PathFinderJgraphtDefault(
		final Graph<EdgeDefault, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		super(graph, graphEdgesValidationDesired, new PathFactoryDefault());
	}
}