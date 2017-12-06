package com.programmerare.shortestpaths.core.parsers;

import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.EdgeDefaultImpl;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathDefaultImpl;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.EdgeImpl;
import com.programmerare.shortestpaths.core.impl.WeightImpl;
import com.programmerare.shortestpaths.core.validation.GraphValidationException;
import com.programmerare.shortestpaths.utils.StringUtility;

/**
 *  TODO: write more/better documentation ...
 * 
 *  String reoresentation of the "List<Path<Edge>>" i.e. the same type returned from the following method: 
 * List<Path<Edge>> shortestPaths = pathFinder.findShortestPaths(startVertex, endVertex, numberOfPathsToFind);
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
public final class PathParser<P extends Path<E, V, W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> {

	//<P extends Path<E,V,W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> implements PathFinder<P, E, V, W>
	
	private final Map<String, E> mapWithEdgesAndVertexConcatenationAsKey;
	
	private PathParser(final List<E> edgesUsedForFindingTheWeightsBetweenVerticesInPath) {
		// TOOD: use input validator here when that branch has been merged into the same code base
//		this.edgesUsedForFindingTheWeightsBetweenVerticesInPath = edgesUsedForFindingTheWeightsBetweenVerticesInPath;
		
		mapWithEdgesAndVertexConcatenationAsKey = new HashMap<String, E>();
		for (E edge : edgesUsedForFindingTheWeightsBetweenVerticesInPath) {
			final String key = EdgeImpl.createEdgeIdValue(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId());
			mapWithEdgesAndVertexConcatenationAsKey.put(key, edge);
		}
	}
	
	public static <P extends Path<E, V, W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> PathParser<P, E, V, W> createPathParser(final List<E> edgesUsedForFindingTheWeightsBetweenVerticesInPath) {
		return new PathParser<P, E, V, W>(edgesUsedForFindingTheWeightsBetweenVerticesInPath);
	}
	
	public List<P> fromStringToListOfPaths(String multiLinedString) {
		final List<String> listOfLines = StringUtility.getMultilineStringAsListOfTrimmedStringsIgnoringLinesWithOnlyWhiteSpace(multiLinedString);
		return fromListOfStringsToListOfPaths(listOfLines);
	}
	
	public List<P> fromListOfStringsToListOfPaths(final List<String> listOfStrings) {
		final List<P> listOfPaths = new ArrayList<P>();
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
	P fromStringToPath(final String pathString) {
		final String[] array = pathString.split("\\s+");

		// TODO check "array.length" and throw exception ...
		final double totalWeight = Double.parseDouble(array[0]);
		
		final List<E> edges = new ArrayList<E>(); 
		
		for (int i = 2; i < array.length; i++) {
			final String startVertexId = array[i-1];
			final String endVertexId = array[i];
			E edge = getEdgeIncludingTheWeight(startVertexId, endVertexId);
			edges.add(edge);
		}
		Weight weight = WeightImpl.createWeight(totalWeight);
		return createPathWithHorribleCode((W)weight, edges);
	}
	
	public String fromPathToString(final P path) {
		final StringBuilder sb = new StringBuilder();
		final double d = path.getTotalWeightForPath().getWeightValue();
		final String s = StringUtility.getDoubleAsStringWithoutZeroesAndDotIfNotRelevant(d);
		sb.append(s);
		final List<E> edgesForPath = path.getEdgesForPath();
		for (final E edge : edgesForPath) {
			sb.append(" ");			
			sb.append(edge.getStartVertex().getVertexId());
		}
		sb.append(" ");		
		sb.append(edgesForPath.get(edgesForPath.size()-1).getEndVertex().getVertexId());
		return sb.toString();
	}

	public E getEdgeIncludingTheWeight(final String startVertexId, final String endVertexId) {
		final String key = EdgeImpl.createEdgeIdValue(startVertexId, endVertexId);
		if(!mapWithEdgesAndVertexConcatenationAsKey.containsKey(key)) {
			throw new GraphValidationException("No edge with these vertices: from " + startVertexId + " to " + endVertexId);
		}
		return mapWithEdgesAndVertexConcatenationAsKey.get(key);
	}

	// the purpose of the method name is not reduce the risk of forgetting to refactor ....
	public static <P extends Path<E, V, W> , E extends Edge<V, W> , V extends Vertex , W extends Weight> P createPathWithHorribleCode(W totalWeight, List<E> edges) {
		// TODO: fix this HORRIBLE code !!! 
		E e = edges.get(0);
		System.out.println("createThePath e class : " + e.getClass()); // EdgeDefaultImpl
		if(e.getClass().equals(EdgeDefaultImpl.class)) {
			List<EdgeDefault> aa = (List<EdgeDefault>) edges;
			PathDefault pathDefault = PathDefaultImpl.createPathDefault(totalWeight, aa);
			return (P)pathDefault;
		}
		Path<E, V, W> path = createPath(totalWeight, edges);
//		System.out.println("createThePath class : " + edges.getClass()); 
		// TODO: fix this code ...
		// PathDefault extends Path< EdgeDefault , Vertex , Weight >
//		PathDefault pathDefault = PathDefaultImpl.createPathDefault(totalWeight, edges); 
		return (P)path;
	}
	
}