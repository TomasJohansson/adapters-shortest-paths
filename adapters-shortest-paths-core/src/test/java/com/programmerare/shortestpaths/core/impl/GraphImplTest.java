package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;

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
		Graph<Edge> graph = GraphImpl.createGraph(edges);
		
		List<Edge> allEdges = graph.getAllEdges();
		
		assertEquals(2,  allEdges.size());
		assertSame(edge1, allEdges.get(0));
		assertSame(edge2, allEdges.get(1));
	}
}