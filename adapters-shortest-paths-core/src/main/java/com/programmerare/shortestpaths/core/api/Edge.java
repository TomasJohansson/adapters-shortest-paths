package com.programmerare.shortestpaths.core.api;

/**
 * @author Tomas Johansson
 */
public interface Edge extends StringRenderable {

	String getEdgeId();
	
	Vertex getStartVertex();
	Vertex getEndVertex();
	
	Weight getEdgeWeight();
}
