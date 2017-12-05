package com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults;

import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public class PathFinderFactoryJgraphtDefault 
	extends PathFinderFactoryJgrapht<
			PathFinderDefault, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			PathDefault,
			EdgeDefault, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactoryDefault 
{
	public PathFinderFactoryJgraphtDefault() {
		super(PathFinderJgraphtDefault.class);
	}
}