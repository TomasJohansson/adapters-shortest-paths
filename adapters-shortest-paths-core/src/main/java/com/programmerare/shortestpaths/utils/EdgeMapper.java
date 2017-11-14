package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.impl.EdgeImpl;

/**
 * Edge is an interface which the implementations will not know of.
 * Instances are passed as parameter into a construction of an implementation specific graph
 * which will return its own kind of edges.
 * Then those edges will be converted back to the common Edge interface, but in such case 
 * it will be a new instance which may not be desirable if the instances are not 
 * the default implementations provided but this project, but they may be classes with more data methods,
 * and therefore it is desirable to map them back to the original instances, which is the purpose of this class.
 * @author Tomas Johansson
 */
public final class EdgeMapper<T extends Edge> {

	private final Map<String, T> edgeMapWithVertexIdsAsKey = new HashMap<String, T>();
	
	/**
	 * TODO: let this be a precondition: the edges must already be validated. Use GraphEdgesValidator before createEdgeMapper.  
	 * @param edges
	 * @return
	 */
	public static <T extends Edge> EdgeMapper<T> createEdgeMapper(final List<T> edges) {
		return new EdgeMapper(edges);
	}
	
	private  EdgeMapper(final List<T> edges) {
		for (T edge : edges) {
			final String idForMapping = getIdForMapping(edge);
			// TODO: removed the exception throwing when the precondition has been implemented
			if(edgeMapWithVertexIdsAsKey.containsKey(idForMapping)) {
				throw new RuntimeException("An edge is a pair of vertices and must only occur once. " + edge);
			}
			edgeMapWithVertexIdsAsKey.put(idForMapping, edge);
		}
	}

	public List<T> getOriginalObjectInstancesOfTheEdges(final List<T> edges) {
		final List<T> originalObjectInstancesOfTheEdges = new ArrayList<T>();
		for (T edge : edges) {
			originalObjectInstancesOfTheEdges.add(edgeMapWithVertexIdsAsKey.get(getIdForMapping(edge)));
		}		
		return originalObjectInstancesOfTheEdges;
	}

	public T getOriginalEdgeInstance(final String startVertexId, final String endVertexId) {
		return edgeMapWithVertexIdsAsKey.get(getIdForMapping(startVertexId, endVertexId));
	}
	
	private String getIdForMapping(final Edge edge) {
		return getIdForMapping(edge.getStartVertex(), edge.getEndVertex());
	}
	
	private String getIdForMapping(final Vertex startVertex, final Vertex endVertex) {
		return getIdForMapping(startVertex.getVertexId(), endVertex.getVertexId());
	}
	
	private String getIdForMapping(final String startVertexId, final String endVertexId) {
		return EdgeImpl.createEdgeIdValue(startVertexId, endVertexId);
	}	
}