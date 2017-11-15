package com.programmerare.shortestpaths.graph.utils;

import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.utils.TimeMeasurer;

/**
 * Used for validation of paths against each other.
 * TODO: the class should be restructured probably into to two classes with appropriate names.
 * (or at least refactored with better methods)
 * One class/method should invoke different "findShortestPaths" for different implementations 
 * and another would compare a different returned "List<Path<Edge>>" with each other.
 * Please also note that one method currently receives a parameter "List<Path<Edge>> expectedListOfPaths"
 * which is created from xml as an expected output path.
 *     
 *  
 * @author Tomas Johansson
 */
public class GraphShortestPathAssertionHelper {
	
	private boolean isExecutingThroughTheMainMethod;

	public GraphShortestPathAssertionHelper(boolean isExecutingThroughTheMainMethod) {
		this.isExecutingThroughTheMainMethod = isExecutingThroughTheMainMethod;
	}

	/**
	 * Overloaded method using null when we do not have an expected list of paths (retrieved from xml)
	 * but only want to compare results from implementations with each other
	 * See also comment at class level.
	 */
	public void testResultsWithImplementationsAgainstEachOther(
			final List<Edge> edgesForGraph, 
			final Vertex startVertex,
			final Vertex endVertex, 
			final int numberOfPathsToFind, 
			final List<GraphFactory<Edge>> graphFactoriesForImplementationsToTest
		) {
		testResultsWithImplementationsAgainstEachOther(
			edgesForGraph, 
			startVertex,
			endVertex, 
			numberOfPathsToFind, 
			graphFactoriesForImplementationsToTest,
			null,
			false
		);		
	}

	/**
	 * Overloaded method. Note that the last parameter can be null if we only want to compare 
	 * results from different implementations.
	 * The second last parameter is used when we also have an expected path retrieved for example from an xml file.
	 * The last parameter can be used temporarirly for "debugging" purposes when we want to display the results to the console
	 * See comment at class level.
	 */	
	public void testResultsWithImplementationsAgainstEachOther(
		final List<Edge> edgesForGraph, 
		final Vertex startVertex,
		final Vertex endVertex, 
		final int numberOfPathsToFind, 
		final List<GraphFactory<Edge>> graphFactoriesForImplementationsToTest,
		final List<Path<Edge>> expectedListOfPaths,
		final boolean shouldPrettyPrintListOfPathsToTheConsoleOutput
	) {
		output("Number of edges in the graph to be tested : " + edgesForGraph.size());

		final Map<String, List<Path<Edge>>> shortestPathsPerImplementation = new HashMap<String, List<Path<Edge>>>();
		
		for (int i = 0; i < graphFactoriesForImplementationsToTest.size(); i++) {
			final GraphFactory<Edge> graphFactory = graphFactoriesForImplementationsToTest.get(i);

			final TimeMeasurer tm = TimeMeasurer.start();			
			final Graph<Edge> graph = graphFactory.createGraph(edgesForGraph);
			final List<Path<Edge>> shortestPaths = graph.findShortestPaths(startVertex, endVertex, numberOfPathsToFind);
			output("seconds : " + tm.getSeconds() + " for implementation " + graph.getClass().getName());
			if(shouldPrettyPrintListOfPathsToTheConsoleOutput) {
				displayListOfShortestPath(shortestPaths);
			}
			
			for (Path<Edge> path : shortestPaths) {
				// output("shortest path weight " + path.getTotalWeightForPath().getWeightValue());
			}
			
			shortestPathsPerImplementation.put(graph.getClass().getName(), shortestPaths);
			
			final Path<Edge> shortestPath = shortestPaths.get(0);
			final List<Edge> edgesForShortestPaths = shortestPath.getEdgesForPath();
			for (int j = 0; j < edgesForShortestPaths.size(); j++) {
				// output("edge " + j + " : " + edgesForShortestPaths.get(j));
			}
		}
		
		final List<String> nameOfImplementations = new ArrayList<String>(shortestPathsPerImplementation.keySet());
		for (int i = 0; i < nameOfImplementations.size(); i++) {
			final String nameOfImplementation_1 = nameOfImplementations.get(i);
			final List<Path<Edge>> pathsFoundByImplementation_1 = shortestPathsPerImplementation.get(nameOfImplementation_1);
			if(expectedListOfPaths != null) {
				final String failureMessage = nameOfImplementation_1 + " failed when comparing with expected result according to xml file"; 
				assertEquals("Mismatching number of paths, " + failureMessage, expectedListOfPaths.size(), pathsFoundByImplementation_1.size());
				for (int m = 0; m < pathsFoundByImplementation_1.size(); m++) {
					assertEqualPaths(failureMessage, expectedListOfPaths.get(m), pathsFoundByImplementation_1.get(m));
				}					
			}
			
			for (int j = i+1; j < nameOfImplementations.size(); j++) {
				final String nameOfImplementation_2 = nameOfImplementations.get(j);
				final List<Path<Edge>> pathsFoundByImplementation_2 = shortestPathsPerImplementation.get(nameOfImplementation_2);
				assertEquals(pathsFoundByImplementation_1.size(), pathsFoundByImplementation_2.size());
				for (int k = 0; k < pathsFoundByImplementation_2.size(); k++) {
					assertEqualPaths("fail for i,j,k " + i + " , " + j + " , " + k , pathsFoundByImplementation_1.get(k), pathsFoundByImplementation_2.get(k));
				}
				output("-----------------");
				output("Now the results from these two implementationss have been compaerd with each other: ");
				output(nameOfImplementation_1 + " vs " + nameOfImplementation_2);
			}
		}
	}

	private void output(Object o) {
		if(isExecutingThroughTheMainMethod) {
			System.out.println(o);
		}
	}

	private void assertEqualPaths(final String message, final Path<Edge> expectedPath, final Path<Edge> actualPath) {
		assertEquals(
			message, 
			expectedPath.getTotalWeightForPath().getWeightValue(), 
			actualPath.getTotalWeightForPath().getWeightValue(), 
			SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS
		);
		final List<Edge> expectedEdges = expectedPath.getEdgesForPath(); 
		final List<Edge> actualEdges = actualPath.getEdgesForPath();
		assertEquals("Mismatching number of vertices/edges in the path, " + message, expectedEdges.size(), actualEdges.size());
		for (int i = 0; i < actualEdges.size(); i++) {
			final Edge actualEdge = actualEdges.get(i);
			final Edge expectedEdge = expectedEdges.get(i);
			assertEquals(message, expectedEdge.getStartVertex(), actualEdge.getStartVertex());
			assertEquals(message, expectedEdge.getEndVertex(), actualEdge.getEndVertex());
			assertEquals(
				message, 
				expectedEdge.getEdgeWeight().getWeightValue(), 
				actualEdge.getEdgeWeight().getWeightValue(), 
				SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS
			);			
			assertEquals(message, expectedEdge, actualEdge);			
		}
	}
	
	
	
	// ---------------------------------------------------------------------------------------
	// TODO: these methods was copied from "/adapters-shortest-paths-example-project/src/main/java/shortest_paths_getting_started_example/ExampleMain.java"
	// and should be refactored into a reusable utiltity method (probably in core project)
//	private static void displayShortestPathBetweenEdges(Vertex startVertex, Vertex endVertex, List<Edge> edgesInput, GraphFactory<Edge> graphFactory) {
//		System.out.println("Implementation " + graphFactory.getClass().getName());
//		Graph<Edge> graph = graphFactory.createGraph(edgesInput);
//		List<Path<Edge>> shortestPaths = graph.findShortestPaths(startVertex, endVertex, 10); // 10 is max but in this case there are only 3 possible paths to return
//		displayListOfShortestPath(shortestPaths);
//	}
	private static void displayListOfShortestPath(List<Path<Edge>> shortestPaths) {
		System.out.println("djkhasdhaskdjashdkashdkls");
		for (Path<Edge> path : shortestPaths) {
			System.out.println(getPathAsPrettyPrintedStringForConsoleOutput(path));
		}
		System.out.println("-------------------------------------------------------------");		
	}
	
	private static String getPathAsPrettyPrintedStringForConsoleOutput(Path<Edge> path) {
		StringBuffer sb = new StringBuffer();
		sb.append(path.getTotalWeightForPath().getWeightValue() + " ( ");
		List<Edge> edges = path.getEdgesForPath();
		for (int i = 0; i < edges.size(); i++) {
			if(i > 0) {
				sb.append(" + ");		
			}
			sb.append(getEdgeAsPrettyPrintedStringForConsoleOutput(edges.get(i)));
		}
		sb.append(")");
		return sb.toString();
	}
	private static String getEdgeAsPrettyPrintedStringForConsoleOutput(Edge edge) {
		return edge.getEdgeWeight().getWeightValue()  + "[" + edge.getStartVertex().getVertexId() + "--->" + edge.getEndVertex().getVertexId() + "] ";		
	}
	// ---------------------------------------------------------------------------------------	
}