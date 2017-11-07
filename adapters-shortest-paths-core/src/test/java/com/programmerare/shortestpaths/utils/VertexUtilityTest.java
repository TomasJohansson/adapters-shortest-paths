package com.programmerare.shortestpaths.utils;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;

/**
 * @author Tomas Johansson
 */
public class VertexUtilityTest {


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
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

		List<Vertex> vertices = VertexUtility.getAllVerticesFromVerticesButWithoutDuplicates(edges);
		
		List<String> expectedVerticesIds = Arrays.asList("A", "B", "C", "D");
		
		assertEquals(expectedVerticesIds.size(), vertices.size());
		
		// verify that all vertices in all edges is one of the four above
		for (Edge edge : edges) {
			assertThat(expectedVerticesIds, hasItem(edge.getStartVertex().getVertexId()));
			assertThat(expectedVerticesIds, hasItem(edge.getEndVertex().getVertexId()));
		}		
	}

}