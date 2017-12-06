package com.programmerare.shortestpaths.core.api;

import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;

public interface PathFinder 
	extends PathFinderGenerics<
		Path,
		Edge,  // Edge<Vertex , Weight>
		Vertex , 
		Weight
	>
{

}
