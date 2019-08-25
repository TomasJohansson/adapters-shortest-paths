/*
* Copyright (c) Tomas Johansson , http://www.programmerare.com
* The code is made available under the terms of the MIT License.
* https://github.com/TomasJohansson/adapters-shortest-paths/blob/master/adapters-shortest-paths-core/License.txt
*/
package com.programmerare.shortestpaths.core.impl.generics;

import com.programmerare.shortestpaths.core.api.Edge;
import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;



/**
 * @author Tomas Johansson
 */
public class EdgeMapperTest {
	private Edge edgeX1, edgeX2, edgeY1, edgeY2;
	
	@BeforeEach
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
		assertSame(originalEdges.get(0), originalObjectInstancesOfTheEdges.get(0));
		assertSame(originalEdges.get(1), originalObjectInstancesOfTheEdges.get(1)); 		
	}
	
	@Test
	public void testGetOriginalEdgeInstance() {
		List<Edge> originalEdges = Arrays.asList(edgeX1, edgeY1);
		
		EdgeMapper edgeMapper = EdgeMapper.createEdgeMapper(originalEdges);

		// the same edge instance should be retrieve when we below pass in the string ids for the vertices of the edge 
		
		assertSame(edgeX1, edgeMapper.getOriginalEdgeInstance(edgeX1.getStartVertex().getVertexId(), edgeX1.getEndVertex().getVertexId()));
		assertSame(edgeY1, edgeMapper.getOriginalEdgeInstance(edgeY1.getStartVertex().getVertexId(), edgeY1.getEndVertex().getVertexId()));
	}
}
