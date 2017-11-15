package com.programmerare.shortestpaths.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.GraphFactory;

public final class GraphFactories {
	
	/**
	 * @return a list of implementations that should be used for searching the best paths,
	 * and the implementation results will be verified with each other and the test will cause a failure 
	 * if there is any mismatch found in the results.
	 */
	public static List<GraphFactory<Edge>> createGraphFactories() {
		final List<GraphFactory<Edge>> list = new ArrayList<GraphFactory<Edge>>();
		list.add(new GraphFactoryYanQi<Edge>());
		list.add(new GraphFactoryBsmock<Edge>());
		list.add(new GraphFactoryJgrapht<Edge>());
		return list;
	}
}
