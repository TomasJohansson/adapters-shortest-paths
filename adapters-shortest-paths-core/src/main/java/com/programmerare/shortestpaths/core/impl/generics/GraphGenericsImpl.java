package com.programmerare.shortestpaths.core.impl.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;

public class GraphGenericsImpl<E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> implements GraphGenerics<E, V, W> {

	private final List<E> edges;
	
	private List<V> vertices; // lazy loaded when it is needed

	// vertex id is the key
	private Map<String, V> mapWithVertices; // lazy loaded at the same time some the list is pppulated
	
	// edge id is the key
	private Map<String, E> mapWithEdges; // lazy loaded
	
	protected GraphGenericsImpl(final List<E> edges) {
		this.edges = Collections.unmodifiableList(edges);
	}

	public static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> GraphGenerics<E, V, W> createGraphGenerics(
		final List<E> edges
	) {
		final GraphGenericsImpl<E, V, W> g = new GraphGenericsImpl<E, V, W>(
			edges				
		);
		return g;
	}
	
	public List getEdges() {
		return edges;
	}

	public List<V> getVertices() {
		if(vertices == null) { // lazy loading
			final List<V> vertices = new ArrayList<V>(); 
			final Map<String, V> map = new HashMap<String, V>();
			for (final E edge : edges) {
				final V startVertex = edge.getStartVertex();
				final V  endVertex = edge.getEndVertex();

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

	
	public boolean containsVertex(final V vertex) {
		getVertices(); // triggers the lazy loading if needed, TODO refactor instead of using a getter for this purpose
		return mapWithVertices.containsKey(vertex.getVertexId());
	}

	public boolean containsEdge(final E edge) {
		if(mapWithEdges == null) {
			mapWithEdges = new HashMap<String, E>();
			for (E e : edges) {
				mapWithEdges.put(e.getEdgeId(), e);
			}			
		}
		return mapWithEdges.containsKey(edge.getEdgeId());
	}
}