package com.programmerare.shortestpaths.core.impl.generics;

import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static com.programmerare.shortestpaths.core.impl.generics.EdgeGenericsImpl.createEdgeGenerics;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;

/**
 * @author Tomas Johansson
 */
public class EdgeGenericsImplTest {

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
		edgeX = createEdgeGenerics(vertexA, vertexB, weight);
		
		edgeY = createEdgeGenerics(createVertex("A"), createVertex("B"), createWeight(weightValue));
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