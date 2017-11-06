package com.programmerare.shortestpaths.adapter;

/**
 * @author Tomas Johansson
 */
public interface Edge {

	/**
	 * @return a concatenation of the the vertex ids separated by underscore, i.e. like this:
	 * 	startVertex.getVertexId() + "_" + endVertex.getVertexId()
	 */
	String getEdgeId();
	
	Vertex getStartVertex();
	Vertex getEndVertex();
	
	Weight getEdgeWeight();
}
