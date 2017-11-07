package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;

public class GraphJgraphtTest extends GraphTestBase {
	@Override
	protected GraphFactory<Edge> createGraphFactory() {
		return new GraphFactoryJgrapht<Edge>();
	}
}