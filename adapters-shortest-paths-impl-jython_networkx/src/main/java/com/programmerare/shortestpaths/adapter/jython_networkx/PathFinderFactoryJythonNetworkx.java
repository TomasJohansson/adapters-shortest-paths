package com.programmerare.shortestpaths.adapter.jython_networkx;

import com.programmerare.shortestpaths.adapter.jython_networkx.generics.PathFinderFactoryJythonNetworkxGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;

public class PathFinderFactoryJythonNetworkx 
	extends PathFinderFactoryJythonNetworkxGenerics<
		PathFinder, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
		Path,
		Edge, // Edge<Vertex , Weight> ,  
		Vertex,
		Weight
	>
	implements PathFinderFactory
{
	public PathFinderFactoryJythonNetworkx() {
		super();
	}

	public PathFinderFactoryJythonNetworkx(
		boolean shouldInitializeSlowInstanceCreation
	) {
		super(shouldInitializeSlowInstanceCreation);
	}

	@Override
	public PathFinder createPathFinder(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		return new PathFinderJythonNetworkx(graph);
	}	
}
