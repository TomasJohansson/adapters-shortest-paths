package com.programmerare.shortestpaths.utils;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;

/**
 * @author Tomas Johansson
 */
public class EdgeMapperTest {
	private Edge edgeX1, edgeX2, edgeY1, edgeY2;
	
	@Before
	public void setUp() throws Exception {
		edgeX1 = createEdge(createVertex("A"), createVertex("B"), createWeight(7));
		edgeX2 = createEdge(createVertex("A"), createVertex("B"), createWeight(7));
		
		edgeY1 = createEdge(createVertex("B"), createVertex("C"), createWeight(8));
		edgeY2 = createEdge(createVertex("B"), createVertex("C"), createWeight(8));
		
		
	}

	@Test
	public void testGetOriginalObjectInstancesOfTheEdges() {
		List<Edge> originalEdges = Arrays.asList(edgeX1, edgeY1);
		List<Edge> equalEdgesButOtherInstances = Arrays.asList(edgeX2, edgeY2);
		
		assertEquals(originalEdges.get(0), equalEdgesButOtherInstances.get(0));
		assertEquals(originalEdges.get(1), equalEdgesButOtherInstances.get(1));
		// Note that the above were equal but they are NOT the same instances as you can see here:
		assertFalse(originalEdges.get(0) == equalEdgesButOtherInstances.get(0));
		assertFalse(originalEdges.get(1) == equalEdgesButOtherInstances.get(1));

		
		EdgeMapper edgeMapper = EdgeMapper.createEdgeMapper(originalEdges);
		List<Edge> originalObjectInstancesOfTheEdges = edgeMapper.getOriginalObjectInstancesOfTheEdges(equalEdgesButOtherInstances);
		// Note that the input parameter above vas the list which did NOT have the same instances as the original
		// list (i.e. the list passed into the constructor of EdgeMapper) but the returned list should have the same 
		// instances, and they should be mapped through the id of the edge
		assertTrue(originalEdges.get(0) == originalObjectInstancesOfTheEdges.get(0));
		assertTrue(originalEdges.get(1) == originalObjectInstancesOfTheEdges.get(1));		
	}
}
