package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.adapter.Edge;

/**
 * Edge is an interface which the implementations will not know of.
 * Instances are passed as parameter into a construction of an implementation specific graph
 * which will return its own kind of edges.
 * Then those edges will be converted back to the common Edge interface, but in such case 
 * it will be a new instance which may not be desirable if the instances are not 
 * the default implementations provided but this project, but they may be classes with more data methods,
 * and therefore it is desirable to map them back to the original instances, which is the purpose of this class.
 * It works because the interface {@link Edge#getEdgeId()} has specified what should be returned which is used in this class.
 * @author Tomas Johansson
 */
public final class EdgeMapper {

	private final Map<String, Edge> edgeMapWithVertexIdsAsKey = new HashMap<String, Edge>();
	
	public static EdgeMapper createEdgeMapper(final List<Edge> edges) {
		return new EdgeMapper(edges);
	}
	
	private EdgeMapper(final List<Edge> edges) {
		for (Edge edge : edges) {
			if(edgeMapWithVertexIdsAsKey.containsKey(edge.getEdgeId())) {
				throw new RuntimeException("An edge is a pair of vertices and must only occur once. " + edge);
			}
			edgeMapWithVertexIdsAsKey.put(edge.getEdgeId(), edge);
		}
	}

	public List<Edge> getOriginalObjectInstancesOfTheEdges(final List<Edge> edges) {
		final List<Edge> originalObjectInstancesOfTheEdges = new ArrayList<Edge>();
		for (Edge edge : edges) {
			originalObjectInstancesOfTheEdges.add(edgeMapWithVertexIdsAsKey.get(edge.getEdgeId()));
		}		
		return originalObjectInstancesOfTheEdges;
	}

}
