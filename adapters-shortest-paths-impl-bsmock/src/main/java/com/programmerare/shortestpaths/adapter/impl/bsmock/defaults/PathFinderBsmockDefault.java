package com.programmerare.shortestpaths.adapter.impl.bsmock.defaults;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderBsmock;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderBsmockDefault 
	extends PathFinderBsmock<
		PathDefault,
		EdgeDefault, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinderDefault
{
	protected PathFinderBsmockDefault(
		final Graph<EdgeDefault, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		super(graph, graphEdgesValidationDesired, new PathFactoryDefault());
	}
}