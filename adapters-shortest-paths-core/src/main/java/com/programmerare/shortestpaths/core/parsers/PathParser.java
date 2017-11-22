package com.programmerare.shortestpaths.core.parsers;

import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.impl.EdgeImpl;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationException;
import com.programmerare.shortestpaths.utils.StringUtility;

/**
 *  TODO: write more/better documentation ...
 * 
 *  String reoresentation of the "List<Path<Edge>>" i.e. the same type returned from the following method: 
 * List<Path<Edge>> shortestPaths = graph.findShortestPaths(startVertex, endVertex, numberOfPathsToFind);
 * The intended purpose is to define strings within xml files with the expected result
 * 
 * Each line ns a string is first the total weight and then the sequence of vertices.
 * Example:  "13 A B D"
 * The simple representation (without weight informatin) is the reason why the list of edges is also needed,
 * i.e. to find the weights.  
 * 
 * @author Tomas Johansson
 *
 */
public final class PathParser {

	private final Map<String, Edge> mapWithEdgesAndVertexConcatenationAsKey;
	
	public PathParser(final List<Edge> edgesUsedForFindingTheWeightsBetweenVerticesInPath) {
		// TOOD: use input validator here when that branch has been merged into the same code base
//		this.edgesUsedForFindingTheWeightsBetweenVerticesInPath = edgesUsedForFindingTheWeightsBetweenVerticesInPath;
		
		mapWithEdgesAndVertexConcatenationAsKey = new HashMap<String, Edge>();
		for (Edge edge : edgesUsedForFindingTheWeightsBetweenVerticesInPath) {
			final String key = EdgeImpl.createEdgeIdValue(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId());
			mapWithEdgesAndVertexConcatenationAsKey.put(key, edge);
		}
	}
	
	public List<Path<Edge>> fromStringToListOfPaths(String multiLinedString) {
		final List<String> listOfLines = StringUtility.getMultilineStringAsListOfTrimmedStringsIgnoringLinesWithOnlyWhiteSpace(multiLinedString);
		return fromListOfStringsToListOfPaths(listOfLines);
	}
	
	public List<Path<Edge>> fromListOfStringsToListOfPaths(final List<String> listOfStrings) {
		final List<Path<Edge>> listOfPaths = new ArrayList<Path<Edge>>();
		for (String string : listOfStrings) {
			listOfPaths.add(fromStringToPath(string));
		}
		return listOfPaths;
	}

	/**
	 * @param pathString first the total weight and then the sequence of vertices.
	 * 		Example:  "13 A B D"
	 * @return
	 */
	private Path<Edge> fromStringToPath(final String pathString) {
		final String[] array = pathString.split("\\s+");

		// TODO check "array.length" and throw exception ...
		final double totalWeight = Double.parseDouble(array[0]);
		
		final List<Edge> edges = new ArrayList<Edge>(); 
		
		for (int i = 2; i < array.length; i++) {
			final String startVertexId = array[i-1];
			final String endVertexId = array[i];
			Edge edge = getEdgeIncludingTheWeight(startVertexId, endVertexId);
			edges.add(edge);
		}		
		return createPath(createWeight(totalWeight), edges);
	}

	public Edge getEdgeIncludingTheWeight(final String startVertexId, final String endVertexId) {
		final String key = EdgeImpl.createEdgeIdValue(startVertexId, endVertexId);
		if(!mapWithEdgesAndVertexConcatenationAsKey.containsKey(key)) {
			throw new GraphEdgesValidationException("No edge with these vertices: from " + startVertexId + " to " + endVertexId);
		}
		return mapWithEdgesAndVertexConcatenationAsKey.get(key);
	}

}
