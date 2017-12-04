package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface PathFinder<E extends Edge<V, W> , V extends Vertex , W extends Weight> {
	
	List<Path<E, V, W>> findShortestPaths(V startVertex, V endVertex, int maxNumberOfPaths);

}
