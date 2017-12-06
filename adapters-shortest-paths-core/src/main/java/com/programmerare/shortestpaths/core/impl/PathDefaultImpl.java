package com.programmerare.shortestpaths.core.impl;

import java.util.List;

import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.generics.PathGenericsImpl;

public class PathDefaultImpl extends PathGenericsImpl<EdgeDefault , Vertex , Weight> implements PathDefault {

	protected PathDefaultImpl(final Weight totalWeight, final List<EdgeDefault> edges) {
		super(totalWeight, edges);
	}

	public static PathDefault createPathDefault(final Weight totalWeight, final List<EdgeDefault> edges) {
		return new PathDefaultImpl(totalWeight, edges);
	}

}
