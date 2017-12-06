package com.programmerare.shortestpaths.core.pathfactories;

import java.util.List;

import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathDefaultImpl;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public final class PathFactoryDefault
	implements PathFactory<PathDefault , EdgeDefault , Vertex , Weight>
{
	public PathDefault createPath(final Weight totalWeight, final List<EdgeDefault> edges) {
		final PathDefault pathDefault = PathDefaultImpl.createPathDefault((Weight)totalWeight, edges);
		// System.out.println("PathFactory created PathFactoryDefault " + pathDefault.getClass());
		return pathDefault;
	}
}