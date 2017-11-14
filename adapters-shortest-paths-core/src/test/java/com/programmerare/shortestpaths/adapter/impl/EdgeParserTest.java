package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.EdgeParser.createEdgeParser;
import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
public class EdgeParserTest {

	private EdgeParser edgeParser; 
	
	@Before
	public void setUp() throws Exception {
		edgeParser = createEdgeParser();
	}

	@Test
	public void testFromStringToEdge() {
		Edge edge = edgeParser.fromStringToEdge("A B 3.7");
		assertNotNull(edge);
		assertNotNull(edge.getStartVertex());
		assertNotNull(edge.getEndVertex());
		assertNotNull(edge.getEdgeWeight());
		assertEquals("A", edge.getStartVertex().getVertexId());
		assertEquals("B", edge.getEndVertex().getVertexId());
		assertEquals(3.7, edge.getEdgeWeight().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
	}

	@Test
	public void testFromEdgeToString() {
		Vertex startVertex = createVertex("A");
		Vertex endVertex = createVertex("B");
		Weight weight = createWeight(3.7);		
		Edge edge = createEdge(startVertex, endVertex, weight);
		assertEquals("A B 3.7", edgeParser.fromEdgeToString(edge));
	}
}
