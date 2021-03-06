package com.programmerare.shortestpaths.adapter.jgrapht;

import com.programmerare.shortestpaths.adapter.jgrapht.generics.PathFinderJgraphtGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;

public class PathFinderJgrapht 
	extends PathFinderJgraphtGenerics<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderJgrapht(
		final GraphGenerics<Edge, Vertex, Weight> graph,
		final JGraphtAlgorithm jGraphtAlgorithm
	) {
		super(graph, new PathFactoryDefault(), jGraphtAlgorithm);
	}

}