package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
public class EdgeImplTest {

	private Vertex vertexA, vertexB;
	private Weight weight;
	private double weightValue;
	private Edge edgeX, edgeY;
	
	@Before
	public void setUp() throws Exception {
		vertexA = createVertex("A");
		vertexB = createVertex("B");
		weightValue = 123.45;
		weight = createWeight(weightValue);
		edgeX = createEdge(vertexA, vertexB, weight);
		
		edgeY = createEdge(createVertex("A"), createVertex("B"), createWeight(weightValue));
	}


	@Test
	public void testGetStartVertex() {
		assertEquals(vertexA.getVertexId(), edgeX.getStartVertex().getVertexId());
		assertEquals(vertexA, edgeX.getStartVertex());
	}
	
	@Test
	public void testGetEndVertex() {
		assertEquals(vertexB.getVertexId(), edgeX.getEndVertex().getVertexId());
		assertEquals(vertexB, edgeX.getEndVertex());
	}	
	
	@Test
	public void testgetEdgeWeight() {
		assertEquals(weightValue, edgeX.getEdgeWeight().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		assertEquals(weight, edgeX.getEdgeWeight());		
	}
	
	@Test
	public void testEquals() {
		assertEquals(edgeX, edgeY);
		assertTrue(edgeX.equals(edgeY));
		assertTrue(edgeY.equals(edgeX));
	}
	
	@Test
	public void testHashCode() {
		assertEquals(edgeX.hashCode(), edgeY.hashCode());
	}	
}