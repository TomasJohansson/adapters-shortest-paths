package com.programmerare.shortestpaths.adapter.jgrapht.yen;

import com.programmerare.shortestpaths.adapter.jgrapht.yen.generics.PathFinderJgraphtGenericsYen;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;

public class PathFinderJgraphtYen 
	extends PathFinderJgraphtGenericsYen<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderJgraphtYen(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		super(graph, new PathFactoryDefault());
	}
}