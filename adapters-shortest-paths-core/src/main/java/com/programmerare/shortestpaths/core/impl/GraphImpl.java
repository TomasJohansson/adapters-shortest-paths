package com.programmerare.shortestpaths.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;

public class GraphImpl<T extends Edge> implements Graph<T> {

	private final List<T> edges;

	private GraphImpl(final List<T> edges) {
		this.edges = Collections.unmodifiableList(edges);
	}

	public static <T extends Edge> Graph<T> createGraph(
		final List<T> edges
	) {
		final GraphImpl<T> g = new GraphImpl<T>(
			edges				
		);
		return g;
	}
	
	public List getAllEdges() {
		return edges;
	}

	public List<Vertex> getAllVertices() {
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
	// TODO: refactor the above and below method which currently has simply been moved from VertexUtility
	// which was created before the Graph type was created 
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