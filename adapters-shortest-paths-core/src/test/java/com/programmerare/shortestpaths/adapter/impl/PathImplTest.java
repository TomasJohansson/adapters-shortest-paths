package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Path;

/**
 * @author Tomas Johansson
 */
public class PathImplTest {

	private Edge edgeAB3;
	private Edge edgeBC5;
	private Edge edgeCD7;
	private String firstVertex, secondVertex, thirdVertex, fourthVertex;
	private double weightFirstEdge, weightSecondEdge, weightThirdEdge, totalWeight;
	private Path path; 
			
	@Before
	public void setUp() throws Exception {
		firstVertex = "A";
		secondVertex = "B";
		thirdVertex = "C";
		fourthVertex = "D";
	
		weightFirstEdge = 3;
		weightSecondEdge = 5;
		weightThirdEdge = 7;
		totalWeight = weightFirstEdge + weightSecondEdge + weightThirdEdge;
		
		edgeAB3 = createEdge(createVertex(firstVertex), createVertex(secondVertex), createWeight(weightFirstEdge));
		edgeBC5 = createEdge(createVertex(secondVertex), createVertex(thirdVertex), createWeight(weightSecondEdge));
		edgeCD7 = createEdge(createVertex(thirdVertex), createVertex(fourthVertex), createWeight(weightThirdEdge));
		
		path = createPath(createWeight(totalWeight), Arrays.asList(edgeAB3, edgeBC5, edgeCD7));
	}

	@Test
	public void testGetTotalWeightForPath() {
		assertEquals(totalWeight, path.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
	}

	@Test
	public void testGetEdgesForPath() {
		List<? extends Edge> edgesForPath = path.getEdgesForPath();
		assertEquals(3, edgesForPath.size());
		assertEquals(edgeAB3, edgesForPath.get(0));
		assertEquals(edgeBC5, edgesForPath.get(1));
		assertEquals(edgeCD7, edgesForPath.get(2));
	}

	@Test(expected = RuntimeException.class) 
	public void testExceptionIsThrownIfVerticesIsNotMatching() { 
		createPath(
			createWeight(15d),  
			Arrays.asList(
				createEdge(createVertex("A"), createVertex("B"), createWeight(3d)),
				createEdge(createVertex("B"), createVertex("C"), createWeight(5d)),
				 // Note that "X" should be "C" below, which is the reason for expected exceotion
				createEdge(createVertex("X"), createVertex("D"), createWeight(7d))
			),
			false,
			true // tell creation method to throw exception if not all vertices are matching 				
		);
	}
	
	@Test(expected = RuntimeException.class) 
	public void testExceptionIsTotalWeightIsNotMatching() {
		createPath(
			createWeight(16), // SHOULD be 15 ( 3 + 5 + 7 ) and therefore an exception should be thrown 
			Arrays.asList(
				createEdge(createVertex("A"), createVertex("B"), createWeight(3d)),
				createEdge(createVertex("B"), createVertex("C"), createWeight(5d)),
				createEdge(createVertex("C"), createVertex("D"), createWeight(7d))
			),
			true,  // tell creation method to throw exception if sum is not matching
			false 				
		);
	}	
}