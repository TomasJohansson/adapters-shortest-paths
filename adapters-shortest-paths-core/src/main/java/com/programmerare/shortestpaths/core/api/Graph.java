package com.programmerare.shortestpaths.core.api;

import java.util.List;

public interface Graph<T extends Edge> {

	List<T> getAllEdges();

	List<Vertex> getAllVertices();

}
