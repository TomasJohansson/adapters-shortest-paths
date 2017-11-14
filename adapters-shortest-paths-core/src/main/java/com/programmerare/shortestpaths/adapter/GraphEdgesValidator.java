package com.programmerare.shortestpaths.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GraphEdgesValidator<T extends Edge> {

	public void validateEdgesAsAcceptableInputForGraphConstruction(final List<T> edges) {

		final Map<String, Boolean> mapForValidatingUniqueEdgeId = new HashMap<String, Boolean>(); 
		final Map<String, Boolean> mapForValidatingUniqueVerticesIds = new HashMap<String, Boolean>();
		
		// * all three aggregated objects (weight and both vertices) must be non-null 
		// * all edge ids and vertex ids (start and end vertices in each edge) must be non-null and non-blank
		// * all edges must be unique in two ways:
		// 		unique ids
		//  	unique combination of start vertex and end vertex (i.e. not multiple weights for an edge between two vertices)
		//  	(but it can be noted that often the above two are same i.e. the vertex id is a concatenation if not set explicitly)
		for (T edge : edges) {
			validateNonNullObjects(edge);
			validateNonBlankIds(edge);
			
			validateUniqueEdgeId(edge, mapForValidatingUniqueEdgeId);
			validateUniqueVerticesIds(edge, mapForValidatingUniqueVerticesIds);
		}		
	}

	void validateNonNullObjects(final T edge) {
		validateNonNullObject(edge.getStartVertex());		
		validateNonNullObject(edge.getEndVertex());
		validateNonNullObject(edge.getEdgeWeight());
	}

	/**
	 * @param shouldNotBeNull should be either an instance of Vertex or a Weight, aggregated within an Edge instance 
	 */
	void validateNonNullObject(final Object shouldNotBeNull) {
		if(shouldNotBeNull == null) {
//			TODO throw new .getClass()..
		}
	}

	/**
	 * Precondition: the "outer" objects (e.g. "edge" and "edge.getStartVertex()" should already have been checked for not being null 
	 * @param edge
	 */
	void validateNonBlankIds(final T edge) {
		validateNonBlankId(edge.getEdgeId());
		validateNonBlankId(edge.getStartVertex().getVertexId());
		validateNonBlankId(edge.getEndVertex().getVertexId());
	}

	void validateNonBlankId(final String id) {
		if(id == null || id.trim().equals("")) {
//			TODO throw new .getClass()..
		}		
	}
	
	void validateUniqueEdgeId(final T edge, final Map<String, Boolean> mapForValidatingUniqueEdgeId) {
		if(mapForValidatingUniqueEdgeId.containsKey(edge.getEdgeId())) {
			// TODO throw ...
		}
		mapForValidatingUniqueEdgeId.put(edge.getEdgeId(), true);
	}

	/**
	 * Precondition: the edge should already have been checked for nulls and blank values, i.e.
	 * the method can assume that 'edge.getStartVertex().getVertexId()' will work without throwing NullPointerException
	 * @param edge
	 * @param mapForValidatingUniqueVerticesIds
	 */
	void validateUniqueVerticesIds(final T edge, final Map<String, Boolean> mapForValidatingUniqueVerticesIds) {
		final String concatenationOdVerticesIds = edge.getStartVertex().getVertexId() + "_" + edge.getEndVertex().getVertexId();
		if(mapForValidatingUniqueVerticesIds.containsKey(concatenationOdVerticesIds)) {
			// TODO throw ...
		}
		mapForValidatingUniqueVerticesIds.put(concatenationOdVerticesIds, true);		
	}	
}