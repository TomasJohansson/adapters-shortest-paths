package com.programmerare.shortestpaths.adapter.utils;

import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.utils.TimeMeasurer;

public class GraphShortestPathAssertionHelper {
	
	private boolean isExecutingThroughTheMainMethod;

	public GraphShortestPathAssertionHelper(boolean isExecutingThroughTheMainMethod) {
		this.isExecutingThroughTheMainMethod = isExecutingThroughTheMainMethod;
	}

	public void testResultsWithImplementationsAgainstEachOther(
		final List<Edge> edgesForGraph, 
		final Vertex startVertex,
		final Vertex endVertex, 
		final int numberOfPathsToFind, 
		final List<GraphFactory<Edge>> graphFactoriesForImplementationsToTest
	) {
		output("Number of edges in the graph to be tested : " + edgesForGraph.size());

		final Map<String, List<Path<Edge>>> shortestPathsPerImplementation = new HashMap<String, List<Path<Edge>>>();
		
		for (int i = 0; i < graphFactoriesForImplementationsToTest.size(); i++) {
			final GraphFactory<Edge> graphFactory = graphFactoriesForImplementationsToTest.get(i);

			final TimeMeasurer tm = TimeMeasurer.start();			
			final Graph<Edge> graph = graphFactory.createGraph(edgesForGraph);
			final List<Path<Edge>> shortestPaths = graph.findShortestPaths(startVertex, endVertex, numberOfPathsToFind);
			output("seconds : " + tm.getSeconds() + " for implementation " + graph.getClass().getName());
			
			assertEquals(numberOfPathsToFind, shortestPaths.size());

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
}
