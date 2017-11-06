package com.programmerare.shortestpaths.adapter;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Graph {
	
	List<Path> findShortestPaths(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths);

}
