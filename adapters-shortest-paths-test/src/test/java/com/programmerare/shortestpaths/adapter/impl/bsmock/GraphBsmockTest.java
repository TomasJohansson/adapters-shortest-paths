package com.programmerare.shortestpaths.adapter.impl.bsmock;

import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.GraphFactory;

public class GraphBsmockTest extends GraphTestBase {
	@Override
	protected GraphFactory<Edge> createGraphFactory() {
		return new GraphFactoryBsmock<Edge>();
	}
}