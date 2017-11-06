package com.programmerare.shortestpaths.adapter;

import java.util.List;

/**
 * @author Tomas Johansson
 */
public interface Path {
	
	Weight getTotalWeightForPath();
	
	List<Edge> getEdgesForPath();
}