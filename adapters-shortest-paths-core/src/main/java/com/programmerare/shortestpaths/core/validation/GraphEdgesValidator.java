package com.programmerare.shortestpaths.core.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.StringRenderable;
import com.programmerare.shortestpaths.utils.EdgeUtility;

public final class GraphEdgesValidator<T extends Edge> {

	private final static String nameOfClassForRemovingDuplicateEdges;
	static {
		// refactoring friendly reference to the class name
		nameOfClassForRemovingDuplicateEdges = EdgeUtility.class.getName();
	}
	
	private GraphEdgesValidator() {	}
	
	public static <T extends Edge> GraphEdgesValidator<T> createGraphEdgesValidator() {
		return new GraphEdgesValidator<T>();
	}	

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
			throw new GraphValidationException(exceptionMessagePrefix + " " + exceptionMessageSuffix);
		}
	}

	private void validateNonBlankId(final String id, final StringRenderable edgeOrVertex) {
		throwExceptionIfConditionTrue(id == null || id.trim().equals(""), "id value must not be empty", edgeOrVertex);
	}
	
	void validateUniqueEdgeId(final T edge, final Map<String, Boolean> mapForValidatingUniqueEdgeId) {
		throwExceptionIfConditionTrue(mapForValidatingUniqueEdgeId.containsKey(edge.getEdgeId()), "Edge id must be unique wich it was not. To remove duplicated edges, you can use a method in the class " + nameOfClassForRemovingDuplicateEdges, edge);
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

	/**
	 * An example of usage for this method is that both parameters (expected list of paths, and a list of edges) 
	 * may be defined in an xml file, but they might be defined incorrectly, and then it is desirable
	 * to fail early to easier realize that the problem is how the test is defined rather than the behaviour 
	 * of the code under test.
	 * Precondition: The list of all the edges should be valid, i.e. it is NOT tested again in this method that
	 * 	the vertices and weights are not null.    
	 * @param paths
	 * @param allEdgesForGraph
	 */
	public void validateAllPathsOnlyContainEdgesDefinedInGraph(
		final List<Path<T>> paths, 
		final List<T> allEdgesForGraph
	) {
		final Map<String, T> mapWithAllEdgesInGraph = createMapWithAllEdgesInGraph(allEdgesForGraph);
		
		for (final Path<T> path : paths) {
			final List<T> edgesForPath = path.getEdgesForPath();
			for (T edgeInPath : edgesForPath) {
				validateNonNullObjects(edgeInPath);
				validateNonBlankIds(edgeInPath);
				final String key = createMapKeyUsedInMapWithEdges(edgeInPath);
				throwExceptionIfConditionTrue(!mapWithAllEdgesInGraph.containsKey(key), "The edge in path is not part of the graph", edgeInPath);
			}
		}
	}

	/**
	 * @param edgesForGraph
	 * @return a map with edges as values, and the key is a string created with a private helper method in this same class   
	 */
	private Map<String, T> createMapWithAllEdgesInGraph(final List<T> edgesForGraph) {
		final Map<String, T> mapWithAllEdgesInGraph = new HashMap<String, T>();
		for (final T edgeInGraph : edgesForGraph) {
			// the method used below should never cause a NullPointerException if the above documented precondition is fulfilled
			final String key = createMapKeyUsedInMapWithEdges(edgeInGraph);
			mapWithAllEdgesInGraph.put(key, edgeInGraph);
		}
		return mapWithAllEdgesInGraph;
	}

	/**
	 * Precondition: the input edge and all its aggregated parts must be non-null, i.e. this method should 
	 * never throw an NullPointerException if the precondition is respected  
	 * @param edge
	 * @return
	 */
	private String createMapKeyUsedInMapWithEdges(final T edge) {
		final String key = edge.getEdgeId() + "_" + edge.getStartVertex().getVertexId() + "_" +  edge.getEndVertex().getVertexId();
		return key;
	}

	/**
	 * Static convenience method.
	 * @param edges
	 */
	public static <T extends Edge> void validateEdgesForGraphCreation(final List<T> edges) {
		final GraphEdgesValidator<T> graphEdgesValidator = createGraphEdgesValidator();
		graphEdgesValidator.validateEdgesAsAcceptableInputForGraphConstruction(edges);
	}
	
}