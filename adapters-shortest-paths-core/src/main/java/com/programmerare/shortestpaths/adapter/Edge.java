package com.programmerare.shortestpaths.adapter;

/**
 * @author Tomas Johansson
 */
public interface Edge extends StringRenderable {

	String getEdgeId();
	
	Vertex getStartVertex();
	Vertex getEndVertex();
	
	Weight getEdgeWeight();
}
