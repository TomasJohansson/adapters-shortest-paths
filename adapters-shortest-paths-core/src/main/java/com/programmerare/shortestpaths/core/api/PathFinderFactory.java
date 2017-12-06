package com.programmerare.shortestpaths.core.api;

import com.programmerare.shortestpaths.core.api.generics.PathFinderFactoryGenerics;

public interface PathFinderFactory 
	extends PathFinderFactoryGenerics<
			PathFinder , // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			Path,
			Edge, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>
{

}
