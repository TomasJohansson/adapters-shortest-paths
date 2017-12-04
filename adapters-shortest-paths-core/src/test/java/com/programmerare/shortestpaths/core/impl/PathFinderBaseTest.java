package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.parsers.EdgeParser;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphValidationException;

public class PathFinderBaseTest {

	private Graph<Edge<Vertex,Weight>,Vertex,Weight> graph;
	private List<Path<Edge<Vertex,Weight>,Vertex,Weight>> pathWithAllEdgesBeingPartOfTheGraph;
	private List<Path<Edge<Vertex,Weight>,Vertex,Weight>> pathWithAllEdgesNOTbeingPartOfTheGraph;
	
	private final static String NEWLINE = " \r\n";
	
	@Test
	public void testCreateWeightInstance() {
		PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight> pathFinderConcrete = new PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight>(graph, GraphEdgesValidationDesired.YES);
		// TDOO: refactor duplicated creations as above
		
		Weight weightForTest = new WeightForTest(123.12);
		Weight weightInstance = pathFinderConcrete.createWeightInstance(12.456, weightForTest.getClass());
		assertNotNull(weightInstance);
		assertEquals(weightForTest.getClass(), weightInstance.getClass());
		assertEquals(12.456, weightInstance.getWeightValue(), 0.0001);
	}

	@Before
	public void setUp() throws Exception {
		final EdgeParser edgeParser = EdgeParser.createEdgeParser();		
		final List<Edge<Vertex,Weight>> edges = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"B C 6" + NEWLINE +
				"C D 7" + NEWLINE +
				"D E 8" + NEWLINE);
		graph = createGraph(edges);	
		
		final List<Edge<Vertex,Weight>> edgeForPath1 = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"B C 7" + NEWLINE);
		Path<Edge<Vertex,Weight>, Vertex, Weight> path1 = createPath(createWeight(1234), edgeForPath1);
		
		final List<Edge<Vertex,Weight>> edgeForPath2 = edgeParser.fromMultiLinedStringToListOfEdges(
				"B C 5" + NEWLINE +  
				"C D 7" + NEWLINE);
		Path<Edge<Vertex,Weight>, Vertex, Weight>  path2 = createPath(createWeight(1234), edgeForPath2);

		final List<Edge<Vertex,Weight>> edgeForPath3 = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"E F 7" + NEWLINE); // NOT part of the graph
		Path<Edge<Vertex,Weight>, Vertex, Weight>  path3 = createPath(createWeight(1234), edgeForPath3);
		
		pathWithAllEdgesBeingPartOfTheGraph = Arrays.asList(path1, path2);
		pathWithAllEdgesNOTbeingPartOfTheGraph = Arrays.asList(path2, path3);
	}

	
	@Test
	public void validateThatAllEdgesInAllPathsArePartOfTheGraph_should_NOT_throw_exception() {
		//Path<Edge<Vertex,Weight>, Vertex, Weight> 
		PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight> pathFinderConcrete = new PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight>(graph, GraphEdgesValidationDesired.YES);
		pathFinderConcrete.validateThatAllEdgesInAllPathsArePartOfTheGraph(this.pathWithAllEdgesBeingPartOfTheGraph);
	}
	
	@Test(expected = GraphValidationException.class)
	public void validateThatAllEdgesInAllPathsArePartOfTheGraph_SHOULD_throw_exception() {
		PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight> pathFinderConcrete = new PathFinderConcrete<Edge<Vertex,Weight>,Vertex,Weight>(graph, GraphEdgesValidationDesired.YES);
		pathFinderConcrete.validateThatAllEdgesInAllPathsArePartOfTheGraph(this.pathWithAllEdgesNOTbeingPartOfTheGraph);
	}

	// TODO: refactor duplication ... the same etst class as below is duplicated in another test class file
	public final class PathFinderConcrete<E extends Edge<V, W> , V extends Vertex , W extends Weight> extends PathFinderBase<E, V, W> {
		protected PathFinderConcrete(Graph<E, V, W> graph, GraphEdgesValidationDesired graphEdgesValidationDesired) {
			super(graph, graphEdgesValidationDesired);
		}

		@Override
		protected List<Path<E,V,W>> findShortestPathHook(V startVertex, V endVertex, int maxNumberOfPaths) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public final static class WeightForTest implements Weight {
		private final double value;
		private  WeightForTest(final double value) {
			this.value = value;
		}
		public double getWeightValue() {
			return value;
		}
		public String renderToString() {
			return null;
		}
	}
}