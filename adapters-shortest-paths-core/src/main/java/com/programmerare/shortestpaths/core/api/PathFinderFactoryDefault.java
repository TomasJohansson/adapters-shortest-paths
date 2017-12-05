package com.programmerare.shortestpaths.core.api;


public interface PathFinderFactoryDefault 
	extends PathFinderFactory<
			PathFinderDefault , // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			PathDefault,
			EdgeDefault, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>
{

}
