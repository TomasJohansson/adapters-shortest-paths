package com.programmerare.shortestpaths.graph.tests;

import static com.programmerare.shortestpaths.core.api.EdgeDefaultImpl.createEdgeDefault;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.programmerare.shortestpaths.adapter.impl.bsmock.defaults.PathFinderFactoryBsmockDefault;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults.PathFinderFactoryJgraphtDefault;
import com.programmerare.shortestpaths.adapter.impl.yanqi.defaults.PathFinderFactoryYanQiDefault;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * Tests for a small graph. It is small for the purpose that it should be easy to understand.
 * The tested graph have four vertices A,B,C,D with edges as below 
 * (from start vertex to start vertex and then the weight within parenthesis)
 * A --> B (3)
 * B --> C (5)
 * C --> D (7) 
 * B --> D (13)
 *
 * @author Tomas Johansson
 */
public class SmallGraphTest {

	@Test
	public void testFindShortestPaths_Bsmock() {
		testFindShortestPaths(
			new PathFinderFactoryBsmockDefault()
		);		
	}	
	
	@Test
	public void testFindShortestPaths_Jgrapht() {
		testFindShortestPaths(
			new PathFinderFactoryJgraphtDefault()
		);		
	}
	
	@Test
	public void testFindShortestPaths_YanQi() {
		testFindShortestPaths(
			new PathFinderFactoryYanQiDefault()
		);
	}
	
	public void testFindShortestPaths(
			PathFinderFactoryDefault pathFinderFactory
	) {
		EdgeDefault edgeAB3 = createEdgeDefault(createVertex("A"), createVertex("B"), createWeight(3));
		EdgeDefault edgeBC5 = createEdgeDefault(createVertex("B"), createVertex("C"), createWeight(5));
		EdgeDefault edgeCD7 = createEdgeDefault(createVertex("C"), createVertex("D"), createWeight(7));
		EdgeDefault edgeBD13= createEdgeDefault(createVertex("B"), createVertex("D"), createWeight(13));
		
		List<EdgeDefault> edges = new ArrayList<EdgeDefault>();
		edges.add(edgeAB3);
		edges.add(edgeBC5);
		edges.add(edgeCD7);
		edges.add(edgeBD13);
		// There are two ways from A to C in a Graph with the above edges:
		// A - B - C- D  	, with weight 15 ( 3 + 5 + 7 )
		// A - B - D  		, with weight 16 ( 3 + 13 )
	
//		pathFinderFactory.createPathFinder(graph, graphEdgesValidationDesired);
		
		PathFinderDefault pathFinder = pathFinderFactory.createPathFinder(
			edges,
			GraphEdgesValidationDesired.YES // TODO: refactor the construction of edges to able to do the validation only once instead of doing it for each factory
		);
		List<PathDefault> shortestPaths = pathFinder.findShortestPaths(createVertex("A"), createVertex("D"), 5); // max 5 but actually we should only find 2
		assertEquals(2,  shortestPaths.size());

		PathDefault path = shortestPaths.get(0); // the shortest mentioned above with total weight 15
		assertEquals(15,  path.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		List<EdgeDefault> edgesForPath = path.getEdgesForPath();
		assertEquals(3,  edgesForPath.size());
		assertEqualsAndTheSameInstance(edgeAB3,  edgesForPath.get(0));
		assertEqualsAndTheSameInstance(edgeBC5,  edgesForPath.get(1));
		assertEqualsAndTheSameInstance(edgeCD7,  edgesForPath.get(2));
//		
//
		PathDefault path2 = shortestPaths.get(1);
		assertEquals(16,  path2.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		List<EdgeDefault> edgesForPath2 = path2.getEdgesForPath();
		assertEquals(2,  edgesForPath2.size());
		assertEqualsAndTheSameInstance(edgeAB3,  edgesForPath2.get(0));
		assertEqualsAndTheSameInstance(edgeBD13,  edgesForPath2.get(1));
	}

	private void assertEqualsAndTheSameInstance(Edge edgeFromOriginalInput, Edge edgeFromResultingPath) {
		assertEquals(edgeFromOriginalInput,  edgeFromResultingPath);
		// Note that the below assertion works thanks to the class EdgeMappe
		assertSame(edgeFromOriginalInput, edgeFromResultingPath);
	}
}
