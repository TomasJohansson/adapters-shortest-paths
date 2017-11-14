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
		throwExceptionIfConditionTrue(edge == null, "edge == null", edge);
		throwExceptionIfConditionTrue(edge.getStartVertex() == null, "start vertex is null for edge", edge);
		throwExceptionIfConditionTrue(edge.getEndVertex() == null, "end vertex is null for edge", edge);
		throwExceptionIfConditionTrue(edge.getEdgeWeight() == null, "weight is null for edge", edge);
	}

	/**
	 * Precondition: the "outer" objects (e.g. "edge" and "edge.getStartVertex()" should already have been checked for not being null 
	 * @param edge
	 */
	void validateNonBlankIds(final T edge) {
		validateNonBlankId(edge.getEdgeId(), edge);
		validateNonBlankId(edge.getStartVertex().getVertexId(), edge.getStartVertex());
		validateNonBlankId(edge.getEndVertex().getVertexId(), edge.getEndVertex());
	}
	
	private void throwExceptionIfConditionTrue(boolean conditionFoExceptionToBeThrown, String exceptionMessagePrefix, StringRenderable edgeOrVertexOrWeight) {
		if(conditionFoExceptionToBeThrown) {
			final String exceptionMessageSuffix = edgeOrVertexOrWeight == null ? "null" : edgeOrVertexOrWeight.renderToString();
			throw new GraphEdgesValidationException(exceptionMessagePrefix + " " + exceptionMessageSuffix);
		}
	}

	private void validateNonBlankId(final String id, final StringRenderable edgeOrVertex) {
		throwExceptionIfConditionTrue(id == null || id.trim().equals(""), "id value must not be empty", edgeOrVertex);
	}
	
	void validateUniqueEdgeId(final T edge, final Map<String, Boolean> mapForValidatingUniqueEdgeId) {
		throwExceptionIfConditionTrue(mapForValidatingUniqueEdgeId.containsKey(edge.getEdgeId()), "edge id must be unique wich it was not", edge);
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
		throwExceptionIfConditionTrue(mapForValidatingUniqueVerticesIds.containsKey(concatenationOdVerticesIds), "edge id must be unique wich it was not " + concatenationOdVerticesIds, edge);
		mapForValidatingUniqueVerticesIds.put(concatenationOdVerticesIds, true);		
	}	
}