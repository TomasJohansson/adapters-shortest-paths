package com.programmerare.shortestpaths.graph.utils;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;

public final class PathFinderFactories {
	
	/**
	 * @return a list of implementations that should be used for searching the best paths,
	 * and the implementation results will be verified with each other and the test will cause a failure 
	 * if there is any mismatch found in the results.
	 */
	public static List<PathFinderFactory<Edge>> createPathFinderFactories() {
		final List<PathFinderFactory<Edge>> list = new ArrayList<PathFinderFactory<Edge>>();
		list.add(new PathFinderFactoryYanQi<Edge>());
		list.add(new PathFinderFactoryBsmock<Edge>());
		list.add(new PathFinderFactoryJgrapht<Edge>());
		return list;
	}
}
