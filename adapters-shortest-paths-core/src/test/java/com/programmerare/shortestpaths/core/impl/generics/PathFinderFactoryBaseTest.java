package com.programmerare.shortestpaths.core.impl.generics;

import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static com.programmerare.shortestpaths.core.impl.generics.EdgeGenericsImpl.createEdgeGenerics;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderBase;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderFactoryGenericsBase;
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

	private List<EdgeGenerics<Vertex,Weight>> edgesForAcceptableGraph;
	private List<EdgeGenerics<Vertex,Weight>> edgesForUnacceptableGraph;
	private PathFinderFactoryConcreteForTest pathFinderFactory;

	@Before
	public void setUp() throws Exception {
		pathFinderFactory = new PathFinderFactoryConcreteForTest();
		
		
		final EdgeGenerics<Vertex,Weight> edge_A_B = createEdgeGenerics(createVertex("A"), createVertex("B"), createWeight(123));
		final EdgeGenerics<Vertex,Weight> edge_B_C = createEdgeGenerics(createVertex("B"), createVertex("C"), createWeight(456));
		edgesForAcceptableGraph = Arrays.asList(edge_A_B, edge_B_C);

		// the same edge (A to B) defined once again is NOT correct
		final EdgeGenerics<Vertex,Weight> edge_A_B_again = createEdgeGenerics(createVertex("A"), createVertex("B"), createWeight(789));
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
		PathGenerics<EdgeGenerics<Vertex, Weight>, Vertex, Weight>,
		EdgeGenerics<Vertex , Weight> , 
		Vertex , 
		Weight 
		>
	{
		protected PathFinderConcreteTest(GraphGenerics<EdgeGenerics<Vertex, Weight>, Vertex, Weight> graph,
				GraphEdgesValidationDesired graphEdgesValidationDesired) {
			super(graph, graphEdgesValidationDesired);
		}

		@Override
		protected List<PathGenerics<EdgeGenerics<Vertex, Weight>, Vertex, Weight>> findShortestPathHook(Vertex startVertex,
				Vertex endVertex, int maxNumberOfPaths) {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	public final class PathFinderFactoryConcreteForTest extends PathFinderFactoryGenericsBase
	< PathFinderGenerics<PathGenerics<EdgeGenerics<Vertex,Weight>,Vertex,Weight> , EdgeGenerics<Vertex,Weight>,Vertex,Weight> , PathGenerics<EdgeGenerics<Vertex,Weight>,Vertex,Weight> , EdgeGenerics<Vertex , Weight> , Vertex , Weight>
	{

		public PathFinderGenerics<PathGenerics<EdgeGenerics<Vertex, Weight>, Vertex, Weight>, EdgeGenerics<Vertex, Weight>, Vertex, Weight> createPathFinder(
				GraphGenerics<EdgeGenerics<Vertex, Weight>, Vertex, Weight> graph,
				GraphEdgesValidationDesired graphEdgesValidationDesired) {
			return new PathFinderConcreteTest(
				graph, 
				graphEdgesValidationDesired
			);
		}
		//
		
//		public PathFinder<Edge<Vertex, Weight>, Vertex, Weight> createPathFinder(
//				Graph<Edge<Vertex, Weight>, Vertex, Weight> graph,
//				GraphEdgesValidationDesired graphEdgesValidationDesired) {
//			return new PathFinderConcreteTest(
//				graph, 
//				graphEdgesValidationDesired
//			);
//		}
	}	
}