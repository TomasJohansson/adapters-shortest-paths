package com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults;

import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderJgrapht;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderJgraphtDefault 
	extends PathFinderJgrapht<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderJgraphtDefault(
		final Graph<Edge, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		super(graph, graphEdgesValidationDesired, new PathFactoryDefault());
	}
}