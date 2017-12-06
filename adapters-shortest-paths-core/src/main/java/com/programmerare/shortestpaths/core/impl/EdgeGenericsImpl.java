package com.programmerare.shortestpaths.core.impl;

import com.programmerare.shortestpaths.core.api.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

/**
 * @author Tomas Johansson
 */
public class EdgeGenericsImpl<V extends Vertex , W extends Weight> implements EdgeGenerics<V , W> {

	private final String id;
	private final V startVertex;
	private final V endVertex;
	private final W weight;

	//public static <E extends Edge<V,W> , V extends Vertex , W extends Weight> E createEdge(
	public static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> EdgeGenerics<V,W> createEdge(
		final String edgeId,
		final V startVertex, 
		final V endVertex, 
		final W weight			
	) {
		EdgeGenerics<V, W> e = new EdgeGenericsImpl<V, W>(
			edgeId,
			startVertex, 
			endVertex, 
			weight				
		);
		return e;
	}

	
	//public static <T extends Edge<V,W> , V extends Vertex , W extends Weight> T createEdge(
	public static <V extends Vertex , W extends Weight> EdgeGenerics<V,W> createEdge(
		final V startVertex, 
		final V endVertex, 
		final W weight			
	) {
		return createEdge(
			createEdgeIdValue(startVertex.getVertexId(), endVertex.getVertexId()),
			startVertex, 
			endVertex, 
			weight					
		);
	}

	protected EdgeGenericsImpl(
		final String edgeId,
		final V startVertex, 
		final V endVertex, 
		final W weight
	) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = weight;
		this.id = edgeId;
	}
	
	public V getStartVertex() {
		return startVertex;
	}

	public V getEndVertex() {
		return endVertex;
	}

	public W getEdgeWeight() {
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
		if (!(obj instanceof EdgeGenericsImpl))
			return false;
		EdgeGenericsImpl other = (EdgeGenericsImpl) obj;
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


	public String renderToString() {
		return toString();
	}


//	public E create(String edgeId, V startVertex, V endVertex, W weight) {
//		return createEdge(edgeId, startVertex, endVertex, weight);
//	}

}
