package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;
import com.programmerare.shortestpaths.utils.TimeMeasurer;


/**
 * This test class can generate a big graph, for example 1000 nodes (and then some more edges)
 * and how many of the best paths that should be find, and it searches for paths 
 * with all implementations (currently three) and verifies them against each other,
 * i.e. if all different independent implementations in the same result, you should be able to 
 * feel pretty sure that the result is correct.
 * 
 * It can be run either as a junit test or through the main method.
 * The difference is that when running as "main method program" then there 
 * will be output printed to the console window to display the time it takes for the different implementations.
 * 
 * @author Tomas Johansson
 */
public class ImplementationComparisonTest {

	private boolean isExecutingThroughTheMainMethod = false;
	
	public static void main(String[] args) throws IOException {
		ImplementationComparisonTest implementationComparisonTest = new ImplementationComparisonTest();
		implementationComparisonTest.isExecutingThroughTheMainMethod = true;

		final int numberOfVertices = 500;
		final int numberOfPathsToFind = 50;
		implementationComparisonTest.testProgrammaticallyGeneratedGraph(
			numberOfVertices,
			numberOfPathsToFind				
		);
		// resulting oputput with 500 and 50 on my machine:
		// seconds : 0 for implementation com.programmerare.shortestpaths.adapter.impl.yanqi.GraphYanQi
		// seconds : 1 for implementation com.programmerare.shortestpaths.adapter.impl.bsmock.GraphBsmock
		// seconds : 50 for implementation com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphJgrapht		
	}

	private void output(Object o) {
		if(isExecutingThroughTheMainMethod) {
			System.out.println(o);
		}
	}

	@Test
	public void testProgrammaticallyGeneratedGraph() throws IOException {	
		final int numberOfVertices = 200;
		final int numberOfPathsToFind = 20;
		testProgrammaticallyGeneratedGraph(
			numberOfVertices,
			numberOfPathsToFind				
		);
	}
	
	private void testProgrammaticallyGeneratedGraph(
		final int numberOfVertices, 
		final int numberOfPathsToFind
	) throws IOException {
		
		final List<Edge> edgesForBigGraph = createEdgesForBigGraph(numberOfVertices);
		
		final Vertex startVertex = edgesForBigGraph.get(0).getStartVertex();
		final Vertex endVertex = edgesForBigGraph.get(edgesForBigGraph.size()-1).getEndVertex();
		
		output("Number of edges in the graph to be tested : " + edgesForBigGraph.size());

		final Map<String, List<Path<Edge>>> shortestPathsPerImplementation = new HashMap<String, List<Path<Edge>>>();
		
		final List<GraphFactory<Edge>> graphFactories = GraphFactories.createGraphFactories();
		
		for (int i = 0; i < graphFactories.size(); i++) {
			final GraphFactory<Edge> graphFactory = graphFactories.get(i);

			final TimeMeasurer tm = TimeMeasurer.start();			
			final Graph<Edge> graph = graphFactory.createGraph(edgesForBigGraph);
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
	
	private List<Edge> createEdgesForBigGraph(final int numberOfVertices) {
		final List<Vertex> vertices = createVertices(numberOfVertices);
		assertEquals(numberOfVertices, vertices.size());
		
		double decreasingWeightValue = 10 * numberOfVertices;
		
		final List<Edge> edges = new ArrayList<Edge>();
		
		for(int i=0; i<numberOfVertices-1; i++) {
			edges.add(createEdge(vertices.get(i), vertices.get(i+1), createWeight(--decreasingWeightValue)));
		}
		assertEquals(numberOfVertices-1, edges.size());
				
		for(int i=0; i<numberOfVertices-10; i+=10) {
			edges.add(createEdge(vertices.get(i), vertices.get(i+10), createWeight(--decreasingWeightValue)));
		}
		
		for(int i=0; i<numberOfVertices-100; i+=100) {
			decreasingWeightValue--;
			edges.add(createEdge(vertices.get(i), vertices.get(i+100), createWeight(--decreasingWeightValue)));
		}		

		// now construct a really short path with the smallest value as each weight and only a few edges from start vertex to end vertex
		final int numberOfVerticesToSkip = numberOfVertices / 4;
		// totally five vertices including start and end i.e. three between 
		final Vertex secondVertex = vertices.get(numberOfVerticesToSkip);
		final Vertex thirdVertex =  vertices.get(numberOfVerticesToSkip * 2);
		final Vertex fourthVertex =  vertices.get(numberOfVerticesToSkip * 3);
		
		final Vertex startVertex = vertices.get(0);
		final Vertex endVertex = vertices.get(numberOfVertices-1);
		
		edges.add(createEdge(startVertex, secondVertex, createWeight(--decreasingWeightValue)));
		edges.add(createEdge(secondVertex, thirdVertex, createWeight(--decreasingWeightValue)));
		edges.add(createEdge(thirdVertex, fourthVertex, createWeight(--decreasingWeightValue)));
		edges.add(createEdge(fourthVertex, endVertex, createWeight(--decreasingWeightValue)));
		
		assertTrue(decreasingWeightValue > 0); 
		
		return edges;
	}

	private List<Vertex> createVertices(int numberOfVerticesToCreate) {
		final List<Vertex> vertices = new ArrayList<Vertex>();
		for(int i=1; i<=numberOfVerticesToCreate; i++) {
			vertices.add(createVertex(i));
		}
		return vertices;
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