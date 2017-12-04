package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Path<E extends Edge<V, W> , V extends Vertex , W extends Weight> {
	
	W getTotalWeightForPath();
	
	List<E> getEdgesForPath();
}