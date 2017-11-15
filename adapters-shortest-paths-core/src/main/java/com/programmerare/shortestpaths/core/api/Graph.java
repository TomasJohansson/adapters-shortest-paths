package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Graph<T extends Edge> {
	
	List<Path<T>> findShortestPaths(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths);

}
