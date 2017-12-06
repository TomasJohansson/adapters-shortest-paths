package com.programmerare.shortestpaths.core.pathfactories;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathImpl;

public final class PathFactoryDefault
	implements PathFactory<Path , Edge , Vertex , Weight>
{
	public Path createPath(final Weight totalWeight, final List<Edge> edges) {
		final Path pathDefault = PathImpl.createPathDefault((Weight)totalWeight, edges);
		// System.out.println("PathFactory created PathFactoryDefault " + pathDefault.getClass());
		return pathDefault;
	}
}