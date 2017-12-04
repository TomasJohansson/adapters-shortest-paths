package com.programmerare.shortestpaths.core.api;

/**
 * @author Tomas Johansson
 */
public interface Edge<V extends Vertex , W extends Weight> extends StringRenderable { 

	String getEdgeId();
	
	V getStartVertex();
	V getEndVertex();
	
	W getEdgeWeight();
	
//	E create(
//		String edgeId,
//		V startVertex, 
//		V endVertex, 
//		W weight			
//	);	
}