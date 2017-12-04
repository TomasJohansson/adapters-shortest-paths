package com.programmerare.shortestpaths.graph.utils;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public final class PathFinderFactories {
	
	/**
	 * @return a list of implementations that should be used for searching the best paths,
	 * and the implementation results will be verified with each other and the test will cause a failure 
	 * if there is any mismatch found in the results.
	 */
	public static List<PathFinderFactory<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>>  createPathFinderFactories() {
		ArrayList<PathFinderFactory<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>> list = new ArrayList<PathFinderFactory<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>>();
		list.add(new PathFinderFactoryYanQi<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
		list.add(new PathFinderFactoryBsmock<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
		list.add(new PathFinderFactoryJgrapht<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
		return list;
	}
}
