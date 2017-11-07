package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;

public class GraphJgraphtTest extends GraphTestBase {
	@Override
	protected GraphFactory createGraphFactory() {
		return new GraphFactoryJgrapht();
	}
}