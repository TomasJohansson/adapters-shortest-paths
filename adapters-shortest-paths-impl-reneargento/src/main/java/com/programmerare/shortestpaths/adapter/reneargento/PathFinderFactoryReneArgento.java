package com.programmerare.shortestpaths.adapter.reneargento;

import com.programmerare.shortestpaths.adapter.reneargento.generics.PathFinderFactoryReneArgentoGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;

public class PathFinderFactoryReneArgento 
	extends PathFinderFactoryReneArgentoGenerics<
			PathFinder, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			Path,
			Edge, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactory 
{
	@Override
	public PathFinder createPathFinder(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		return new PathFinderReneArgento(graph);
	}	
}