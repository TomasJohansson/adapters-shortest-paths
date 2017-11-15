package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.GraphFactory;

public class GraphJgraphtTest extends GraphTestBase {
	@Override
	protected GraphFactory<Edge> createGraphFactory() {
		return new GraphFactoryJgrapht<Edge>();
	}
}