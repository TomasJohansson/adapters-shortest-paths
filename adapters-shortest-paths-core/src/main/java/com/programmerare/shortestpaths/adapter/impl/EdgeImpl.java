package com.programmerare.shortestpaths.adapter.impl;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
public final class EdgeImpl implements Edge {

	private final String id;
	private final Vertex startVertex;
	private final Vertex endVertex;
	private final Weight weight;

	public static <T extends Edge> T createEdge(
		final String edgeId,
		final Vertex startVertex, 
		final Vertex endVertex, 
		final Weight weight			
	) {
		Edge e = new EdgeImpl(
			edgeId,
			startVertex, 
			endVertex, 
			weight				
		);
		return (T)e;
	}
	
	public static <T extends Edge> T createEdge(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final Weight weight			
	) {
		return createEdge(
			createEdgeIdValue(startVertex.getVertexId(), endVertex.getVertexId()),
			startVertex, 
			endVertex, 
			weight					
		);
	}

	private EdgeImpl(
		final String edgeId,
		final Vertex startVertex, 
		final Vertex endVertex, 
		final Weight weight
	) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = weight;
		this.id = edgeId;
		
	}
	
	public Vertex getStartVertex() {
		return startVertex;
	}

	public Vertex getEndVertex() {
		return endVertex;
	}

	public Weight getEdgeWeight() {
		return weight;
	}
	
	public String getEdgeId() {
		return id;
	}

	private final static String SEPARATOR_BETWEEN_START_AND_END_VERTEX_ID = "_";

	/**
	 * It could be nicer to place this method somewhere else but the important thing is 
	 * to avoid the duplication, i.e. avoid implementing the concatenation in different places. 
	 * @return a string as documented by {@link Vertex#getVertexId()}
	 */
	public static String createEdgeIdValue(String startVertexId, String endVertexId) {
		return startVertexId + SEPARATOR_BETWEEN_START_AND_END_VERTEX_ID + endVertexId;
	}

	@Override
	public String toString() {
		return "EdgeImpl [id=" + id + ", startVertex=" + startVertex + ", endVertex=" + endVertex + ", weight=" + weight
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endVertex == null) ? 0 : endVertex.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startVertex == null) ? 0 : startVertex.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EdgeImpl))
			return false;
		EdgeImpl other = (EdgeImpl) obj;
		if (endVertex == null) {
			if (other.endVertex != null)
				return false;
		} else if (!endVertex.equals(other.endVertex))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startVertex == null) {
			if (other.startVertex != null)
				return false;
		} else if (!startVertex.equals(other.startVertex))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	
}
