package com.programmerare.shortestpaths.core.api;

import java.util.List;

import com.programmerare.shortestpaths.core.impl.PathImpl;

public class PathDefaultImpl extends PathImpl<EdgeDefault , Vertex , Weight> implements PathDefault {

	protected PathDefaultImpl(final Weight totalWeight, final List<EdgeDefault> edges) {
		super(totalWeight, edges);
	}

	public static PathDefault createPathDefault(final Weight totalWeight, final List<EdgeDefault> edges) {
		return new PathDefaultImpl(totalWeight, edges);
	}

}
