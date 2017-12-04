package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public class GraphImplTest {

	private Edge<Vertex,Weight> edge1, edge2;
	
	@Before
	public void setUp() throws Exception {
		edge1 = createEdge(createVertex("A"), createVertex("B"), createWeight(123));
		edge2 = createEdge(createVertex("B"), createVertex("C"), createWeight(456));		
	}

	@Test
	public void testGetAllEdges() {
		List<Edge<Vertex,Weight>> edges = new ArrayList<Edge<Vertex,Weight>>();
		edges.add(edge1);
		edges.add(edge2);
		// refactor the above three rows (duplicated)

		Graph<Edge<Vertex,Weight>, Vertex,Weight> graph = createGraph(edges);
		
		List<Edge<Vertex, Weight>> allEdges = graph.getEdges();
		
		assertEquals(2,  allEdges.size());
		assertSame(edge1, allEdges.get(0));
		assertSame(edge2, allEdges.get(1));
	}
	
	@Test
	public void testGetVertices() {
		List<Edge<Vertex,Weight>> edges = new ArrayList<Edge<Vertex,Weight>>();
		edges.add(createEdge(createVertex("A"), createVertex("B"), createWeight(1)));
		edges.add(createEdge(createVertex("A"), createVertex("C"), createWeight(2)));
		edges.add(createEdge(createVertex("A"), createVertex("D"), createWeight(3)));
		edges.add(createEdge(createVertex("B"), createVertex("C"), createWeight(4)));
		edges.add(createEdge(createVertex("B"), createVertex("D"), createWeight(5)));
		edges.add(createEdge(createVertex("C"), createVertex("D"), createWeight(6)));
		
		Graph<Edge<Vertex,Weight>, Vertex,Weight> graph = createGraph(edges);
		
		List<Vertex> vertices = graph.getVertices();
		
		List<String> expectedVerticesIds = Arrays.asList("A", "B", "C", "D");
		
		assertEquals(expectedVerticesIds.size(), vertices.size());
		
		// verify that all vertices in all edges is one of the four above
		for (Edge<Vertex,Weight> edge : edges) {
			assertThat(expectedVerticesIds, hasItem(edge.getStartVertex().getVertexId()));
			assertThat(expectedVerticesIds, hasItem(edge.getEndVertex().getVertexId()));
		}		
	}
	
	@Test
	public void testContainsVertex() {
		List<Edge<Vertex,Weight>> edges = new ArrayList<Edge<Vertex,Weight>>();
		edges.add(edge1);
		edges.add(edge2);
		// refactor the above three rows (duplicated)
		
		Graph<Edge<Vertex,Weight>, Vertex,Weight> graph = createGraph(edges);

		assertTrue(graph.containsVertex(edge1.getStartVertex()));
		assertTrue(graph.containsVertex(edge1.getEndVertex()));
		assertTrue(graph.containsVertex(edge2.getStartVertex()));
		assertTrue(graph.containsVertex(edge2.getEndVertex()));
		
		Vertex vertex = createVertex("QWERTY");
		assertFalse(graph.containsVertex(vertex));
	}
	// TODO: refactor some code duplicated above and below i.e. put some code in setup method
	@Test
	public void testContainsEdge() {
		List<Edge<Vertex,Weight>> edges = new ArrayList<Edge<Vertex,Weight>>();
		edges.add(edge1);
		edges.add(edge2);
		// refactor the above three rows (duplicated)
		
		Graph<Edge<Vertex,Weight>, Vertex,Weight> graph = createGraph(edges);		

		assertTrue(graph.containsEdge(edge1));
		assertTrue(graph.containsEdge(edge2));
		
		Edge<Vertex,Weight> edgeNotInTheGraph = createEdge(createVertex("XYZ"), createVertex("QWERTY"), createWeight(987));
		assertFalse(graph.containsEdge(edgeNotInTheGraph));
	}	
}