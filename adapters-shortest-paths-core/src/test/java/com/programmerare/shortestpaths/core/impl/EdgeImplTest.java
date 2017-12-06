package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeGenericsImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

/**
 * @author Tomas Johansson
 */
public class EdgeImplTest {

	private Vertex vertexA, vertexB;
	private Weight weight;
	private double weightValue;
	private EdgeGenerics edgeX, edgeY;
	
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