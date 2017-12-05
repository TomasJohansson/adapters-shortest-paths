package com.programmerare.shortestpaths.core.api;

import com.programmerare.shortestpaths.core.impl.EdgeImpl;

public class EdgeDefaultImpl extends EdgeImpl<Vertex, Weight> implements EdgeDefault {

	protected EdgeDefaultImpl(String edgeId, Vertex startVertex, Vertex endVertex, Weight weight) {
		super(edgeId, startVertex, endVertex, weight);
	}

	public static EdgeDefault createEdgeDefault(final Vertex startVertex, Vertex endVertex, Weight weight) {
		final String edgeId = createEdgeIdValue(startVertex.getVertexId(), endVertex.getVertexId());
		return createEdgeDefault(edgeId, startVertex, endVertex, weight);
	}

	public static EdgeDefault createEdgeDefault(final String edgeId, final Vertex startVertex, Vertex endVertex, Weight weight) {
		return new EdgeDefaultImpl(edgeId, startVertex, endVertex, weight);
	}	
}
