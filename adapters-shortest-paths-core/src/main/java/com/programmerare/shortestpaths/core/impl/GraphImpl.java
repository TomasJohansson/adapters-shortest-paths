package com.programmerare.shortestpaths.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;

public final class GraphImpl<T extends Edge> implements Graph<T> {

	private final List<T> edges;
	
	private List<Vertex> vertices; // lazy loaded when it is needed

	// the id is the key
	private Map<String, Vertex> mapWithVertices; // lazy loaded at the same time some the list is pppulated
	
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
	
	public List getEdges() {
		return edges;
	}

	public List<Vertex> getVertices() {
		if(vertices == null) { // lazy loading
			final List<Vertex> vertices = new ArrayList<Vertex>(); 
			final Map<String, Vertex> map = new HashMap<String, Vertex>();
			for (final Edge edge : edges) {
				final Vertex startVertex = edge.getStartVertex();
				final Vertex endVertex = edge.getEndVertex();

				if(!map.containsKey(startVertex.getVertexId())) {
					map.put(startVertex.getVertexId(), startVertex);
					vertices.add(startVertex);
				}
				
				if(!map.containsKey(endVertex.getVertexId())) {
					map.put(endVertex.getVertexId(), endVertex);
					vertices.add(endVertex);
				}			
			}
			this.vertices = vertices;
			this.mapWithVertices = map;
		}
		return vertices;
	}

	
	public boolean containsVertex(final Vertex vertex) {
		getVertices(); // triggers the lazy loading if needed, TODO refactor instead of using a getter for this purpose
		return mapWithVertices.containsKey(vertex.getVertexId());
	}
}