package com.programmerare.shortestpaths.graph.tests;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmerare.shortestpaths.adapter.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphValidationException;
import org.junit.jupiter.api.Test;

/**
 * PathFinderBase is an abstract base class, and this test class verifies that the subclasses throw validation exceptions implemented in the base class. 
 * @author Tomas Johansson
 */
public class PathFinderBaseValidationExceptionTest {

	private Edge edgeAB, edgeBC;
	private List<Edge> edges_A_B_and_B_C;
	private Vertex vertexA, vertexB, vertexC, vertexX_notPartOfGraph;
	
	@BeforeEach
	public void setUp() throws Exception {
		vertexA = createVertex("A");
		vertexB = createVertex("B");
		vertexC = createVertex("C");
		
		vertexX_notPartOfGraph = createVertex("X");
		
		edgeAB = createEdge(vertexA, vertexB, createWeight(123));
		edgeBC = createEdge(vertexB, vertexC, createWeight(456));
		
		edges_A_B_and_B_C = new ArrayList<Edge>();
		edges_A_B_and_B_C.add(edgeAB);
		edges_A_B_and_B_C.add(edgeBC);
	}

	
	// -------------------------------------------------------------
	// Three tests (for three implementations) with start vertex not part of the graph
	@Test
	public void incorrect_startVertex_shouldThrowException_Bsmock() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryBsmock(), vertexX_notPartOfGraph, vertexC);
		});
		assertThat(exception.getMessage(), containsString("start vertex")); // start vertex is not part of the graph
	}

	@Test
	public void incorrect_startVertex_shouldThrowException_Jgrapht() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryJgrapht(), vertexX_notPartOfGraph, vertexC);
		});
		assertThat(exception.getMessage(), containsString("start vertex")); // start vertex is not part of the graph		
	}

	@Test
	public void incorrect_startVertex_shouldThrowException_YanQi() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryYanQi(), vertexX_notPartOfGraph, vertexC);
		});
		assertThat(exception.getMessage(), containsString("start vertex")); // start vertex is not part of the graph
	}
	
	
	// -------------------------------------------------------------
	// Three tests (for three implementations) with end vertex not part of the graph
	@Test
	public void incorrect_endVertex_shouldThrowException_Bsmock() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryBsmock(), vertexA, vertexX_notPartOfGraph);
		});
		assertThat(exception.getMessage(), containsString("end vertex")); // end vertex is not part of the graph		
	}

	@Test
	public void incorrect_endVertex_shouldThrowException_Jgrapht() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryJgrapht(), vertexA, vertexX_notPartOfGraph);
		});
		assertThat(exception.getMessage(), containsString("end vertex")); // end vertex is not part of the graph
	}

	@Test
	public void incorrect_endVertex_shouldThrowException_YanQi() {
		GraphValidationException exception = org.junit.jupiter.api.Assertions.assertThrows(GraphValidationException.class, () -> {
			shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryYanQi(), vertexA, vertexX_notPartOfGraph);
		});
		assertThat(exception.getMessage(), containsString("end vertex")); // end vertex is not part of the graph		
	}

	
	// -------------------------------------------------------------
	// Three tests (for three implementations) with start and vertex both part of the graph
	// The purpose of the test is simply to show that these do not throw an exception (as the other tests do) and thus no assertions are done about the found paths
	@Test
	public void correct_startAndEndVertex_should_NOT_ThrowException_Bsmock() {
		shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryBsmock(), vertexA, vertexC);
	}	
	@Test
	public void correct_startAndEndVertex_should_NOT_ThrowException_Jgrapht() {
		shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryJgrapht(), vertexA, vertexC);
	}
	@Test
	public void correct_startAndEndVertex_should_NOT_ThrowException__YanQi() {
		shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(new PathFinderFactoryYanQi(), vertexA, vertexC);
	}	
	// -------------------------------------------------------------
	private void shouldThrowExceptionIfAnyOfTheVerticesIsNotPartOfTheGraph(
		final PathFinderFactory pathFinderFactory,
		final Vertex startVertex,
		final Vertex endVertex
	) {
		PathFinder pathFinder = pathFinderFactory.createPathFinder(edges_A_B_and_B_C, GraphEdgesValidationDesired.YES);
		List<Path> shortestPaths = pathFinder.findShortestPaths(startVertex, endVertex, maxNumberOfPaths);				
	}
	
	private final static int maxNumberOfPaths = 1;
}
