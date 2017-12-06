package com.programmerare.shortestpaths.graph.utils;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.defaults.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.defaults.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;

public final class PathFinderFactories {
	
	/**
	 * @return a list of implementations that should be used for searching the best paths,
	 * and the implementation results will be verified with each other and the test will cause a failure 
	 * if there is any mismatch found in the results.
	 */	
	public static List<PathFinderFactory>  createPathFinderFactories() {
		List<PathFinderFactory> list = new ArrayList<PathFinderFactory>();
		list.add(new PathFinderFactoryYanQi());
		list.add(new PathFinderFactoryBsmock());
		list.add(new PathFinderFactoryJgrapht());		
		return list;		
	}
}
