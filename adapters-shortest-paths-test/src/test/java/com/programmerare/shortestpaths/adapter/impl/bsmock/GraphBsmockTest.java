package com.programmerare.shortestpaths.adapter.impl.bsmock;

import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;

public class GraphBsmockTest extends GraphTestBase {
	@Override
	protected GraphFactory createGraphFactory() {
		return new GraphFactoryBsmock();
	}
}