package com.programmerare.shortestpaths.adapter.impl.bsmock.defaults;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderBsmock;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.Graph;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderBsmockDefault 
	extends PathFinderBsmock<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderBsmockDefault(
		final Graph<Edge, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		super(graph, graphEdgesValidationDesired, new PathFactoryDefault());
	}
}