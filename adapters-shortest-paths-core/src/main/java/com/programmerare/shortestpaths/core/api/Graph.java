package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Graph<T extends Edge> {

	/**
	 * @return all edges in the graph
	 */
	List<T> getEdges();

	/**
	 * @return all vertices in the graph
	 */
	List<Vertex> getVertices();

	/**
	 * @param vertex 
	 * @return true if there is a vertex in the graph with the same id as the parameter.
	 */
	boolean containsVertex(Vertex vertex);
	
	boolean containsEdge(T edge);
}