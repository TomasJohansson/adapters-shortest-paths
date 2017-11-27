package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.parsers.EdgeParser;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationException;

public class PathFinderBaseTest {

	private Graph<Edge> graph;
	private List<Path<Edge>> pathWithAllEdgesBeingPartOfTheGraph;
	private List<Path<Edge>> pathWithAllEdgesNOTbeingPartOfTheGraph;
	
	private final static String NEWLINE = " \r\n";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		final EdgeParser edgeParser = EdgeParser.createEdgeParser();		
		final List<Edge> edges = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"B C 6" + NEWLINE +
				"C D 7" + NEWLINE +
				"D E 8" + NEWLINE);
		graph = createGraph(edges);	
		
		final List<Edge> edgeForPath1 = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"B C 7" + NEWLINE);
		Path<Edge> path1 = createPath(createWeight(1234), edgeForPath1);
		
		final List<Edge> edgeForPath2 = edgeParser.fromMultiLinedStringToListOfEdges(
				"B C 5" + NEWLINE +  
				"C D 7" + NEWLINE);
		Path<Edge> path2 = createPath(createWeight(1234), edgeForPath2);

		final List<Edge> edgeForPath3 = edgeParser.fromMultiLinedStringToListOfEdges(
				"A B 5" + NEWLINE +  
				"E F 7" + NEWLINE); // NOT part of the graph
		Path<Edge> path3 = createPath(createWeight(1234), edgeForPath3);
		
		pathWithAllEdgesBeingPartOfTheGraph = Arrays.asList(path1, path2);
		pathWithAllEdgesNOTbeingPartOfTheGraph = Arrays.asList(path2, path3);
	}

	@Test
	public void validateThatAllEdgesInAllPathsArePartOfTheGraph_should_NOT_throw_exception() {
		PathFinderConcrete<Edge> pathFinderConcrete = new PathFinderConcrete<Edge>(graph, GraphEdgesValidationDesired.YES);
		pathFinderConcrete.validateThatAllEdgesInAllPathsArePartOfTheGraph(this.pathWithAllEdgesBeingPartOfTheGraph);
	}
	
	@Test(expected = GraphEdgesValidationException.class)
	public void validateThatAllEdgesInAllPathsArePartOfTheGraph_SHOULD_throw_exception() {
		PathFinderConcrete<Edge> pathFinderConcrete = new PathFinderConcrete<Edge>(graph, GraphEdgesValidationDesired.YES);
		pathFinderConcrete.validateThatAllEdgesInAllPathsArePartOfTheGraph(this.pathWithAllEdgesNOTbeingPartOfTheGraph);
	}
	
	public final class PathFinderConcrete<T extends Edge> extends PathFinderBase<T> implements PathFinder<T> {
		protected PathFinderConcrete(Graph<T> graph, GraphEdgesValidationDesired graphEdgesValidationDesired) {
			super(graph, graphEdgesValidationDesired);
		}
		@Override
		protected List<Path<T>> findShortestPathHook(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths) {
			return null;
		}
	}
}