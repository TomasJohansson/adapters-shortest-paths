package com.programmerare.shortestpaths.adapter.jgrapht;

import com.programmerare.shortestpaths.adapter.jgrapht.generics.PathFinderFactoryJgraphtGenerics;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;

public class PathFinderFactoryJgrapht 
	extends PathFinderFactoryJgraphtGenerics<
			PathFinder, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			Path,
			Edge, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactory
{
	private final JGraphtAlgorithm jGraphtAlgorithm;
	
	public PathFinderFactoryJgrapht(final JGraphtAlgorithm jGraphtAlgorithm) {
		super();
		this.jGraphtAlgorithm = jGraphtAlgorithm;
	}
	public PathFinderFactoryJgrapht() {
		this(JGraphtAlgorithm.getDefault());
	}	

	@Override
	public PathFinder createPathFinder(
		final GraphGenerics<Edge, Vertex, Weight> graph
	) {
		return new PathFinderJgrapht(graph, this.jGraphtAlgorithm);
	}
}