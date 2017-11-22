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
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationException;

/**
 * Testing general behaviour independent of implementation.
 * Of course, since the base class is abstract, some kind of subclass need to be instantiated.
 * 
 * @author Tomas Johansson
 *
 */
public class GraphFactoryBaseTest {

	private List<Edge> edgesForAcceptableGraph;
	private List<Edge> edgesForUnacceptableGraph;
	private GraphFactoryConcreteForTest graphFactory;

	@Before
	public void setUp() throws Exception {
		graphFactory = new GraphFactoryConcreteForTest<Edge>();
		
		final Edge edge_A_B = createEdge(createVertex("A"), createVertex("B"), createWeight(123));
		final Edge edge_B_C = createEdge(createVertex("B"), createVertex("C"), createWeight(456));
		edgesForAcceptableGraph = Arrays.asList(edge_A_B, edge_B_C);

		// the same edge (A to B) defined once again is NOT correct
		final Edge edge_A_B_again = createEdge(createVertex("A"), createVertex("B"), createWeight(789));
		edgesForUnacceptableGraph = Arrays.asList(edge_A_B, edge_A_B_again);
	}

	
	@Test(expected = GraphEdgesValidationException.class)
	public void testCreateGraph_SHOULD_throw_exception_for_unacceptable_graph_when_validation_REQUIRED() {
		graphFactory.createGraph(edgesForUnacceptableGraph, GraphEdgesValidationDesired.YES);
	}
	
	@Test
	public void testCreateGraph_should_NOT_throw_exception_for_unacceptable_graph_when_validation_NOT_required() {
		graphFactory.createGraph(edgesForUnacceptableGraph, GraphEdgesValidationDesired.NO);
	}	

	@Test
	public void testCreateGraph_should_NOT_throw_exception_for_acceptable_graph() {
		// a bit lazy to do two validation below within the same test method, 
		// but since the graph should be acceptable, no exception should be thrown 
		// regardless if validation is required
		graphFactory.createGraph(edgesForAcceptableGraph, GraphEdgesValidationDesired.NO);
		graphFactory.createGraph(edgesForAcceptableGraph, GraphEdgesValidationDesired.YES);
	}

	public final class GraphFactoryConcreteForTest<T extends Edge> extends GraphFactoryBase<T> implements GraphFactory<T> {
		protected Graph<T> createGraphHook(final List<T> edges, final EdgeMapper<T> edgeMapper) {
			return null;
		}
	}	
}
