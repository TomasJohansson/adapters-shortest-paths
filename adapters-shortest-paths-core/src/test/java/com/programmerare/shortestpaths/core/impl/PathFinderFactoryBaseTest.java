package com.programmerare.shortestpaths.core.impl;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphValidationException;

/**
 * Testing general behaviour independent of implementation.
 * Of course, since the base class is abstract, some kind of subclass need to be instantiated.
 * 
 * @author Tomas Johansson
 *
 */
public class PathFinderFactoryBaseTest {

	private List<Edge<Vertex,Weight>> edgesForAcceptableGraph;
	private List<Edge<Vertex,Weight>> edgesForUnacceptableGraph;
	private PathFinderFactoryConcreteForTest pathFinderFactory;

	@Before
	public void setUp() throws Exception {
		pathFinderFactory = new PathFinderFactoryConcreteForTest();
		
		
		final Edge<Vertex,Weight> edge_A_B = createEdge(createVertex("A"), createVertex("B"), createWeight(123));
		final Edge<Vertex,Weight> edge_B_C = createEdge(createVertex("B"), createVertex("C"), createWeight(456));
		edgesForAcceptableGraph = Arrays.asList(edge_A_B, edge_B_C);

		// the same edge (A to B) defined once again is NOT correct
		final Edge<Vertex,Weight> edge_A_B_again = createEdge(createVertex("A"), createVertex("B"), createWeight(789));
		edgesForUnacceptableGraph = Arrays.asList(edge_A_B, edge_A_B_again);
	}

	
	@Test(expected = GraphValidationException.class)
	public void testCreateGraph_SHOULD_throw_exception_for_unacceptable_graph_when_validation_REQUIRED() {
		pathFinderFactory.createPathFinder(edgesForUnacceptableGraph, GraphEdgesValidationDesired.YES);
	}
	
	@Test
	public void testCreateGraph_should_NOT_throw_exception_for_unacceptable_graph_when_validation_NOT_required() {
		pathFinderFactory.createPathFinder(edgesForUnacceptableGraph, GraphEdgesValidationDesired.NO);
	}	

	@Test
	public void testCreateGraph_should_NOT_throw_exception_for_acceptable_graph() {
		// a bit lazy to do two validation below within the same test method, 
		// but since the graph should be acceptable, no exception should be thrown 
		// regardless if validation is required
		pathFinderFactory.createPathFinder(edgesForAcceptableGraph, GraphEdgesValidationDesired.NO);
		pathFinderFactory.createPathFinder(edgesForAcceptableGraph, GraphEdgesValidationDesired.YES);
	}

	// TODO: refactor duplication ... the same etst class as below is duplicated in another test class file
	public final class PathFinderConcreteTest extends PathFinderBase 
		<  
		Edge<Vertex , Weight> , 
		Vertex , 
		Weight 
		>
	{
		protected PathFinderConcreteTest(Graph<Edge<Vertex, Weight>, Vertex, Weight> graph,
				GraphEdgesValidationDesired graphEdgesValidationDesired) {
			super(graph, graphEdgesValidationDesired);
		}

		@Override
		protected List<Path<Edge<Vertex, Weight>, Vertex, Weight>> findShortestPathHook(Vertex startVertex,
				Vertex endVertex, int maxNumberOfPaths) {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	public final class PathFinderFactoryConcreteForTest extends PathFinderFactoryBase
	< PathFinder<Edge<Vertex,Weight>,Vertex,Weight> , Edge<Vertex , Weight> , Vertex , Weight>
	{
		public PathFinder<Edge<Vertex, Weight>, Vertex, Weight> createPathFinder(
				Graph<Edge<Vertex, Weight>, Vertex, Weight> graph,
				GraphEdgesValidationDesired graphEdgesValidationDesired) {
			return new PathFinderConcreteTest(
				graph, 
				graphEdgesValidationDesired
			);

		}
	}	
}