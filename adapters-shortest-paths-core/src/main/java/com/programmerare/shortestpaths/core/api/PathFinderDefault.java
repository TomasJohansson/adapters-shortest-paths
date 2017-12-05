package com.programmerare.shortestpaths.core.api;

public interface PathFinderDefault 
	extends PathFinder<
		PathDefault,
		EdgeDefault,  // Edge<Vertex , Weight>
		Vertex , 
		Weight
	>
{

}
