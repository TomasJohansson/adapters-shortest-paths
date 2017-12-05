package com.programmerare.shortestpaths.adapter.impl.yanqi.defaults;

import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public class PathFinderFactoryYanQiDefault 
	extends PathFinderFactoryYanQi<
			PathFinderDefault, // PathFinder< Edge<Vertex , Weight> , Vertex , Weight> ,
			PathDefault,
			EdgeDefault, // Edge<Vertex , Weight> ,  
			Vertex,
			Weight
		>	
	implements PathFinderFactoryDefault 
{
	public PathFinderFactoryYanQiDefault() {
		super(PathFinderYanQiDefault.class);
	}
}