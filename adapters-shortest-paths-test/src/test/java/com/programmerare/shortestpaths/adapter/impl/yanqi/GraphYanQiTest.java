package com.programmerare.shortestpaths.adapter.impl.yanqi;

import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.GraphFactory;

public class GraphYanQiTest extends GraphTestBase {
	@Override
	protected GraphFactory<Edge> createGraphFactory() {
		return new GraphFactoryYanQi<Edge>();
	}
}
