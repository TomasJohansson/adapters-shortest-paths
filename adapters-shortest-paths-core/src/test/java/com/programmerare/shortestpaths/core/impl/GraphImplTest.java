package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;

public class GraphImplTest {

	private Edge edge1, edge2;
	
	@Before
	public void setUp() throws Exception {
		edge1 = createEdge(createVertex("A"), createVertex("B"), createWeight(123));
		edge2 = createEdge(createVertex("B"), createVertex("C"), createWeight(456));		
	}

	@Test
	public void testGetAllEdges() {
		List<Edge> edges = Arrays.asList(edge1, edge2);
		Graph<Edge> graph = createGraph(edges);
		
		List<Edge> allEdges = graph.getEdges();
		
		assertEquals(2,  allEdges.size());
		assertSame(edge1, allEdges.get(0));
		assertSame(edge2, allEdges.get(1));
	}
	
	@Test
	public void testGetAllVerticesFromVerticesButWithoutDuplicates() {
		List<Edge> edges = Arrays.asList(
			createEdge(createVertex("A"), createVertex("B"), createWeight(1)),
			createEdge(createVertex("A"), createVertex("C"), createWeight(2)),
			createEdge(createVertex("A"), createVertex("D"), createWeight(3)),
			createEdge(createVertex("B"), createVertex("C"), createWeight(4)),
			createEdge(createVertex("B"), createVertex("D"), createWeight(5)),
			createEdge(createVertex("C"), createVertex("D"), createWeight(6))
		);
		Graph<Edge> graph = createGraph(edges);
		List<Vertex> vertices = graph.getVertices();
		
		List<String> expectedVerticesIds = Arrays.asList("A", "B", "C", "D");
		
		assertEquals(expectedVerticesIds.size(), vertices.size());
		
		// verify that all vertices in all edges is one of the four above
		for (Edge edge : edges) {
			assertThat(expectedVerticesIds, hasItem(edge.getStartVertex().getVertexId()));
			assertThat(expectedVerticesIds, hasItem(edge.getEndVertex().getVertexId()));
		}		
	}	
}