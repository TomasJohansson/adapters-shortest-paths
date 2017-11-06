package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Vertex;

/**
 * @author Tomas Johansson
 */
public class VertexImplTest {

	private Vertex vertexA;
	private Vertex vertexB;
	
	@Before
	public void setUp() throws Exception {
		vertexA = createVertex(357);
		vertexB = createVertex("357");		
	}
	
	@Test
	public void testGetVertexId() {
		Vertex vertexA = createVertex(357);
		Vertex vertexB = createVertex("357");
		
		assertEquals(vertexA.getVertexId(), vertexB.getVertexId());
		
		assertEquals(vertexA, vertexB);
		assertEquals(vertexA.hashCode(), vertexB.hashCode());
	}
	
	@Test
	public void testEquals() {
		assertEquals(vertexA, vertexB);

		assertTrue(vertexA.equals(vertexB));
		assertTrue(vertexB.equals(vertexA));
	}
	
	@Test
	public void testHashCode() {
		assertEquals(vertexA.hashCode(), vertexB.hashCode());
	}	

}