package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.api.Path;

public abstract class GraphTestBase {

	@Test
	public void testFindShortestPaths() {
		Edge edgeAB3 = createEdge(createVertex("A"), createVertex("B"), createWeight(3));
		Edge edgeBC5 = createEdge(createVertex("B"), createVertex("C"), createWeight(5));
		Edge edgeCD7 = createEdge(createVertex("C"), createVertex("D"), createWeight(7));
		Edge edgeBD13= createEdge(createVertex("B"), createVertex("D"), createWeight(13));
		List<Edge> edges = Arrays.asList(
			edgeAB3,
			edgeBC5,
			edgeCD7,
			edgeBD13
		);
		// There are two ways from A to C in a Graph with the above edges:
		// A - B - C- D  	, with weight 15 ( 3 + 5 + 7 )
		// A - B - D  		, with weight 16 ( 3 + 13 )
		
		GraphFactory<Edge> graphFactory = createGraphFactory();//  new GraphFactoryJgrapht();
		Graph<Edge> graph = graphFactory.createGraph(edges);
		List<Path<Edge>> shortestPaths = graph.findShortestPaths(createVertex("A"), createVertex("D"), 5); // max 5 but actually we should only find 2
		assertEquals(2,  shortestPaths.size());

		Path<Edge> path1 = shortestPaths.get(0); // the shortest mentioned above with total weight 15
		assertEquals(15,  path1.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		List<Edge> edgesForPath1 = path1.getEdgesForPath();
		assertEquals(3,  edgesForPath1.size());
		assertEqualsAndTheSameInstance(edgeAB3,  edgesForPath1.get(0));
		assertEqualsAndTheSameInstance(edgeBC5,  edgesForPath1.get(1));
		assertEqualsAndTheSameInstance(edgeCD7,  edgesForPath1.get(2));
		

		Path<Edge> path2 = shortestPaths.get(1);
		assertEquals(16,  path2.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		List<Edge> edgesForPath2 = path2.getEdgesForPath();
		assertEquals(2,  edgesForPath2.size());
		assertEqualsAndTheSameInstance(edgeAB3,  edgesForPath2.get(0));
		assertEqualsAndTheSameInstance(edgeBD13,  edgesForPath2.get(1));
	}

	private void assertEqualsAndTheSameInstance(Edge edgeFromOriginalInput, Edge edgeFromResultingPath) {
		assertEquals(edgeFromOriginalInput,  edgeFromResultingPath);
		// Note that the below assertion works thanks to the class EdgeMappe
		assertSame(edgeFromOriginalInput, edgeFromResultingPath);
	}
	
	
	protected abstract GraphFactory<Edge> createGraphFactory();
}
