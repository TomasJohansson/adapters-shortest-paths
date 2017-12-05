package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface PathFinder<P extends Path<E, V, W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> {
	
	List<P> findShortestPaths(V startVertex, V endVertex, int maxNumberOfPaths);

}
