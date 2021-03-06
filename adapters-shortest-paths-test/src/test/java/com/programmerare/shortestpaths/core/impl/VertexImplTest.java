/*
* Copyright (c) Tomas Johansson , http://www.programmerare.com
* The code is made available under the terms of the MIT License.
* https://github.com/TomasJohansson/adapters-shortest-paths/blob/master/adapters-shortest-paths-core/License.txt
*/
package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.programmerare.shortestpaths.core.api.Vertex;

/**
 * @author Tomas Johansson
 */
public class VertexImplTest {

	private Vertex vertexA;
	private Vertex vertexB;
	
	@BeforeEach
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

	@Test
	public void testRenderToString() {
		assertEquals("Vertex[ id=357 ]", vertexA.renderToString());
	}
}
