package com.programmerare.shortestpaths.core.api;

import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;

/**
 * @see PathFinderGenerics
 * @author Tomas Johansson
 */
public interface PathFinder 
	extends PathFinderGenerics<
		Path,
		Edge,  // Edge<Vertex , Weight>
		Vertex , 
		Weight
	>
{}