package com.programmerare.shortestpaths.adapter.reneargento;

import com.programmerare.shortestpaths.adapter.reneargento.generics.PathFinderReneArgentoGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.pathfactories.PathFactoryDefault;

public class PathFinderReneArgento 
	extends PathFinderReneArgentoGenerics<
		Path,
		Edge, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinder
{
	protected PathFinderReneArgento(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		super(graph, new PathFactoryDefault());
	}
}