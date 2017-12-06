package com.programmerare.shortestpaths.core.pathfactories;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathImpl;

public final class PathFactoryGenerics<P extends Path<E, V, W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> 
	implements PathFactory<P, E, V, W>
{
	public P createPath(final W totalWeight, final List<E> edges) {
		final Path<E, V, W> path = PathImpl.createPath(totalWeight, edges);
		final P p = (P) path;
		// System.out.println("PathFactory created PathFactoryGenerics " + path.getClass());
		return p;
	}
}