package com.programmerare.shortestpaths.core.pathfactories;

import java.util.List;

import com.programmerare.shortestpaths.core.api.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.PathGenerics;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathGenericsImpl;

public final class PathFactoryGenerics<P extends PathGenerics<E, V, W> , E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> 
	implements PathFactory<P, E, V, W>
{
	public P createPath(final W totalWeight, final List<E> edges) {
		final PathGenerics<E, V, W> path = PathGenericsImpl.createPath(totalWeight, edges);
		final P p = (P) path;
		// System.out.println("PathFactory created PathFactoryGenerics " + path.getClass());
		return p;
	}
}