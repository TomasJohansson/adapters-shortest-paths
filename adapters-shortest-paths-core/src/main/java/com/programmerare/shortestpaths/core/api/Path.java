package com.programmerare.shortestpaths.core.api;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Path<T extends Edge> {
	
	Weight getTotalWeightForPath();
	
	List<T> getEdgesForPath();
}