package com.programmerare.shortestpaths.core.impl;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.generics.EdgeGenericsImpl;

public class EdgeImpl extends EdgeGenericsImpl<Vertex, Weight> implements Edge {

	protected EdgeImpl(String edgeId, Vertex startVertex, Vertex endVertex, Weight weight) {
		super(edgeId, startVertex, endVertex, weight);
	}

	public static Edge createEdge(final Vertex startVertex, Vertex endVertex, Weight weight) {
		final String edgeId = createEdgeIdValue(startVertex.getVertexId(), endVertex.getVertexId());
		return createEdge(edgeId, startVertex, endVertex, weight);
	}

	public static Edge createEdge(final String edgeId, final Vertex startVertex, Vertex endVertex, Weight weight) {
		return new EdgeImpl(edgeId, startVertex, endVertex, weight);
	}	
}
