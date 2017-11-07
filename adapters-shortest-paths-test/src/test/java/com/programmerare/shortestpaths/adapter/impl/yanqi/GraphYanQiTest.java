package com.programmerare.shortestpaths.adapter.impl.yanqi;

import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.impl.GraphTestBase;

public class GraphYanQiTest extends GraphTestBase {
	@Override
	protected GraphFactory createGraphFactory() {
		return new GraphFactoryYanQi();
	}
}
