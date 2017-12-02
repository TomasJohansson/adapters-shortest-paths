package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * A Graph represents a collection of Edge instances.
 *  
 * 
 * @author Tomas Johansson
 */
public interface Graph<T extends Edge> {

	/**
	 * @return all edges in the graph
	 */
	List<T> getEdges();

	/**
	 * @return all vertices in the graph. 
	 * 	The vertices can be derived from the edges, i.e. the method might be implemented as iterating the edges 
	*  	as a way to create a list of all vertices.
	* 	However, for performance reasons should it not be done every time the method is invoked, but preferably implemented with lazy loading.  
	 */
	List<Vertex> getVertices();

	/**
	 * @param vertex an instance of a Vertex. When the method is implemented, it should use the Vertex id for the comparison,  
	 * i.e. the implementation should NOT use object equality when determining whether the Vertex is part of the Graph or not.  
	 * @return true if there is a Vertex in the graph with the same id as the Vertex parameter.
	 */
	boolean containsVertex(Vertex vertex);

	/**
	 * @param edge an instance of an Edge. When the method is implemented, it should use the Edge id for the comparison,  
	 * i.e. the implementation should NOT use object equality when determining whether the Edge is part of the Graph or not.  
	 * @return true if there is an Edge in the graph with the same id as the Edge parameter.
	 */
	boolean containsEdge(T edge);
}