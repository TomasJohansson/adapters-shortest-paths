package com.programmerare.shortestpaths.core.api;

/**
 * A Vertex represents some point in the Graph.
 * One instance may be part of many edges.
 * One Vertex can be start vertex for many edges, and can also be end vertex for many edges.  
 * 
 * @author Tomas Johansson
 */
public interface Vertex extends StringRenderable {

	/**
	 * @return an id which must be unique within a Graph, i.e. a Graph should not have more than one Vertex with the same id.
	 */
	String getVertexId();
}