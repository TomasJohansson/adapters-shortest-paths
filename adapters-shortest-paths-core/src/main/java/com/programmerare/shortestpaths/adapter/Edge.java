package com.programmerare.shortestpaths.adapter;

/**
 * @author Tomas Johansson
 */
public interface Edge {
	
	String getEdgeId();
	
	Vertex getStartVertex();
	Vertex getEndVertex();
	
	Weight getEdgeWeight();
}
