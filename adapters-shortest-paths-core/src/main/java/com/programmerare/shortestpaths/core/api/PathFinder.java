package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface PathFinder<T extends Edge> {
	
	List<Path<T>> findShortestPaths(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths);

}
