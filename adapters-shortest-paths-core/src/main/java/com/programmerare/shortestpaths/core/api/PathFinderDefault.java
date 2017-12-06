package com.programmerare.shortestpaths.core.api;

import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;

public interface PathFinderDefault 
	extends PathFinderGenerics<
		PathDefault,
		EdgeDefault,  // Edge<Vertex , Weight>
		Vertex , 
		Weight
	>
{

}
