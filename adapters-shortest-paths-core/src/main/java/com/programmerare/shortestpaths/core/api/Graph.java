package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Graph<E extends Edge<V, W> , V extends Vertex , W extends Weight> {

	/**
	 * @return all edges in the graph
	 */
	List<E> getEdges();

	/**
	 * @return all vertices in the graph
	 */
	List<V> getVertices();

	/**
	 * @param vertex 
	 * @return true if there is a vertex in the graph with the same id as the parameter.
	 */
	boolean containsVertex(V vertex);
	
	boolean containsEdge(E edge);
}