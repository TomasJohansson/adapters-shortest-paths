package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Vertex;

/**
 * @author Tomas Johansson
 */
public final class VertexUtility {

	public final static List<Vertex> getAllVerticesFromEdgesButWithoutDuplicates(final List<? extends Edge> edges) {

		final List<Vertex> vertices = new ArrayList<Vertex>();
		final Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (final Edge edge : edges) {
			if(shouldAdd(edge.getStartVertex(), map)) {
				vertices.add(edge.getStartVertex());
			}
			if(shouldAdd(edge.getEndVertex(), map)) {
				vertices.add(edge.getEndVertex());
			}			
		}
		return vertices;
	}

	private final static boolean shouldAdd(final Vertex startVertex, final Map<String, Boolean> map) {
		if(map.containsKey(startVertex.getVertexId())) {
			return false;	
		}
		else {
			map.put(startVertex.getVertexId(), true);
			return true;	
		}
	}
}
