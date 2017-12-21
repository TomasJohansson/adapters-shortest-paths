package com.programmerare.shortestpaths.adapter.mulavito;

import com.programmerare.shortestpaths.adapter.mulavito.generics.PathFinderMulavitoGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;

public class PathFinderMulavito 
	extends PathFinderMulavitoGenerics<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderMulavito(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		super(graph, new PathFactoryDefault());
	}
}