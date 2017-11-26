package com.programmerare.shortestpaths.utils;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.impl.GraphImpl;

@Deprecated 
public final class VertexUtility {

	// TODO: Refactor away this method into the new Graph class which is the natural place for it now when that class has been created
	@Deprecated // now this method has been moved into Graph, so use it instead
	public final static List<Vertex> getAllVerticesFromEdgesButWithoutDuplicates(final List<? extends Edge> edges) {
		Graph<? extends Edge> graph = GraphImpl.createGraph(edges);
		return graph.getAllVertices();
	}

}
