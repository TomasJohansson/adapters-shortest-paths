package com.programmerare.shortestpaths.graph.tests;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.impl.EdgeImpl;
import com.programmerare.shortestpaths.graph.utils.GraphFactories;


/**
 * The focus of this test class is the id value for edges.
 * 
 * These can be either set explicitly, but if not, then they are set to a concatenation
 * of the ids for the two vertices of the edge.
 * Either way, the ids of edges are used for being able to return the same instances as was sent as parameters. 
 * In other words, the client code sends Edge instances as parameters to the method:
 * "Graph GraphFactory.createGraph(List<T> edges)"
 * Then the client will get back a Graph instance and will invoke the method:
 * "List<Path<T>> Graph.findShortestPaths(Vertex startVertex, Vertex endVertex, int maxNumberOfPaths);"
 * The returned list of Path instances will aggregate instances of Edge, and those instances will be retrieved 
 * from the edges sent to the createGraph method. 
 * 
 * The id values of the edge values play a significant role, and this test class try to make sure it works
 * for both scenarios, i.e. either explicitly set ids or implicitly set (with concatenation of vertices ids).
 * 
 * See also javadoc comments at the test methods.
 *
 * The tests are performed for all adapter implementations.
 * (the implementation is determined by the instance of GraphFactory) 
 * 
 * @author Tomas Johansson
 */
public class EdgeIdValuesTest {

	// Name of the vertices
	private final static String A = "A";
	private final static String B = "B";
	private final static String C = "C";
	private final static String D = "D";

	// Name of the edegs
	private final static String EDGE_A_to_B = "EDGE_A_to_B";
	private final static String EDGE_A_to_C = "EDGE_A_to_C";
	private final static String EDGE_B_to_C = "EDGE_B_to_C";
	private final static String EDGE_B_to_D = "EDGE_B_to_D";
	private final static String EDGE_C_to_D = "EDGE_C_to_D";
	
	// Weights for the edges
	private final static double WEIGHT_A_to_B = 5;
	private final static double WEIGHT_A_to_C = 6;
	private final static double WEIGHT_B_to_C = 7;
	private final static double WEIGHT_B_to_D = 8;
	private final static double WEIGHT_C_to_D = 9;
	

	private EdgeIdValueStrategy edgeIdValueStrategy;
	
	private Vertex a;
	private Vertex b;
	private Vertex c;
	private Vertex d;

	@Before
	public void setUp() throws Exception {
		a = createVertex(A);
		b = createVertex(B);
		c = createVertex(C);
		d = createVertex(D);
	}


	/**
	 * Tests that when there are no explicit Id value for Edge when it is constructed, 
	 * then the Id will be created as a concatenation of the Id's of the two vertices
	 */
	@Test
	public void testDefaultEdgeIdValuesWhenNotExplicitlyDefined() {
		List<Edge> edges = Arrays.asList(
			// Note there are not explicit names of the edges when they are created  
			createEdge(a, b, createWeight(WEIGHT_A_to_B)), // the edge Id will be a concatenation i.e. "A_B"
			createEdge(a, c, createWeight(WEIGHT_A_to_C)),
			createEdge(b, c, createWeight(WEIGHT_B_to_C)),
			createEdge(b, d, createWeight(WEIGHT_B_to_D)),
			createEdge(c, d, createWeight(WEIGHT_C_to_D))
		);
		// There are three possible paths, below sorted in the best order regarding shortest total weight:
		//    A to B to D (total cost: 13 = 5 + 8)
		//    A to C to D (total cost: 15 = 6 + 9)
		//    A to B to C to D (total cost: 21 = 5 + 7 + 9)
		edgeIdValueStrategy = new EdgeIdValuesNotExplicitlySpecified(); // when not specified, they should be a concatenation		
		verifyExpectedResults(edges);
	}
	
	/**
	 * Tests that the explicitly named edges will be used rather than just concatenation of vertex Ids.
	 * 
	 * This might look like an 'of course' for you, but it is not obvious that it should be like that 
	 * when you consider how the API works.
	 * One thing you should note is that the object INSTANCES of edges you send as a parameter to the GraphFactory 
	 * will be returned by the Graph as being aggregated parts of returned Path objects.
	 * The implementation will of course create its own objects and are not aware of your classes.
	 * To make this work, there has to be some kind of mapping.
	 * Previously, the mapping was done internally with the method Edge.getEdgeId but then it was a required part of the contract 
	 * for the Edge interface method that it had to be implemented as a concatenation of the Id's of the vertices.
	 * Now it is instead enough that the implementation of the adapters use the class EdgeMapper. 
	 * (which is changed now in the same commit as this comment was added, as a note in case that class would become renamed, you can trace it in git commit history) 
	 * The EdgeMappe takes care of the needed concatenation of the Ids for the vertices to retrieve abck the original instances. 
	 */
	@Test
	public void testCreateEdgesWithoutExplicitNames2() {
		List<Edge> edges = Arrays.asList(
			createEdge(EDGE_A_to_B, a, b, createWeight(WEIGHT_A_to_B)),
			createEdge(EDGE_A_to_C, a, c, createWeight(WEIGHT_A_to_C)),
			createEdge(EDGE_B_to_C, b, c, createWeight(WEIGHT_B_to_C)),
			createEdge(EDGE_B_to_D, b, d, createWeight(WEIGHT_B_to_D)),
			createEdge(EDGE_C_to_D, c, d, createWeight(WEIGHT_C_to_D))
		);
		edgeIdValueStrategy = new EdgeIdValueExplicitlySpecified();
		verifyExpectedResults(edges);
	}
	

	private void verifyExpectedResults(List<Edge> edges) {
		final ExpectedPath[] expectedPaths = getExpectedPaths();

		final List<GraphFactory<Edge>> graphFactories = GraphFactories.createGraphFactories();
		for (GraphFactory<Edge> graphFactory : graphFactories) {
			verifyExpectedPaths(a, d, edges, graphFactory, expectedPaths);	
		}
	}
	
	private ExpectedPath[] getExpectedPaths() {
		ExpectedPath[] expectedPaths = new ExpectedPath[] {
				new ExpectedPath(13, new ExpectedEdge[] {
						new ExpectedEdge(getEdgeId(A, B), A, B, WEIGHT_A_to_B),
						new ExpectedEdge(getEdgeId(B, D), B, D, WEIGHT_B_to_D)
				}),
				new ExpectedPath(15, new ExpectedEdge[] {
						new ExpectedEdge(getEdgeId(A, C), A, C, WEIGHT_A_to_C),
						new ExpectedEdge(getEdgeId(C, D), C, D, WEIGHT_C_to_D)
				}),
				new ExpectedPath(21, new ExpectedEdge[] {
						new ExpectedEdge(getEdgeId(A, B), A, B, WEIGHT_A_to_B),
						new ExpectedEdge(getEdgeId(B, C), B, C, WEIGHT_B_to_C),
						new ExpectedEdge(getEdgeId(C, D), C, D, WEIGHT_C_to_D)
				})
		};
		return expectedPaths;
	}

	private String getEdgeId(String startVertexId, String endVertexId) {
		return edgeIdValueStrategy.getEdgeId(startVertexId, endVertexId);
	}

	private void verifyExpectedPaths(
		Vertex startVertex, 
		Vertex endVertex, 
		List<Edge> edges, 
		GraphFactory<Edge> graphFactory,
		ExpectedPath[] expectedShortestPaths
	) {
		Graph<Edge> graph = graphFactory.createGraph(edges);
		List<Path<Edge>> actualShortestPaths = graph.findShortestPaths(startVertex, endVertex, 10);
		
		assertEquals(expectedShortestPaths.length, actualShortestPaths.size());

		String errorContext = graphFactory.getClass().getSimpleName(); 
		for (int i = 0; i < expectedShortestPaths.length; i++) {
			errorContext += " , i: "+ i;
			ExpectedPath expectedPath = expectedShortestPaths[i];
			Path<Edge> actualPath = actualShortestPaths.get(i);
			assertEquals(errorContext, expectedPath.totalWeight, actualPath.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
			ExpectedEdge[] expectedEdgesForPath = expectedPath.expectedEdges;
			List<Edge> actualEdgesForPath = actualPath.getEdgesForPath();
			assertEquals(errorContext, expectedEdgesForPath.length, actualEdgesForPath.size());
			for (int j = 0; j < expectedEdgesForPath.length; j++) {
				errorContext += " , j=" + j;
				ExpectedEdge expectedEdge = expectedEdgesForPath[j];
				Edge actualEdge = actualEdgesForPath.get(j);
				assertNotNull(errorContext, actualEdge);
				assertNotNull(errorContext, actualEdge.getEdgeWeight());
				assertNotNull(errorContext, actualEdge.getStartVertex());
				assertNotNull(errorContext, actualEdge.getEndVertex());
				assertEquals(errorContext, expectedEdge.weight, actualEdge.getEdgeWeight().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
				assertEquals(errorContext, expectedEdge.startVertexId, actualEdge.getStartVertex().getVertexId());
				assertEquals(errorContext, expectedEdge.endVertexId, actualEdge.getEndVertex().getVertexId());
				assertEquals(errorContext, expectedEdge.edgeId, actualEdge.getEdgeId());
			}			
		}
	}


	public class ExpectedPath {
		public double totalWeight;
		public ExpectedEdge[] expectedEdges;
		public ExpectedPath(double totalWeight, ExpectedEdge[] expectedEdges) {
			this.totalWeight = totalWeight;
			this.expectedEdges = expectedEdges;
		}
	}
	public class ExpectedEdge {
		public String edgeId;
		public String startVertexId;
		public String endVertexId;
		public double weight;
		public ExpectedEdge(String edgeId, String startVertexId, String endVertexId, double weight) {
			this.edgeId = edgeId;
			this.startVertexId = startVertexId;
			this.endVertexId = endVertexId;
			this.weight = weight;
		}
	}

	
	
	
	public interface EdgeIdValueStrategy {
		String getEdgeId(String startVertexId, String endVertexId);
	}
	
	public class EdgeIdValuesNotExplicitlySpecified implements EdgeIdValueStrategy {
		public String getEdgeId(String startVertexId, String endVertexId) {
			return EdgeImpl.createEdgeIdValue(startVertexId, endVertexId);
		}
	}

	public class EdgeIdValueExplicitlySpecified implements EdgeIdValueStrategy {
		private Map<String, String> edgeNameMappedFromIdsOfVertices;
		public EdgeIdValueExplicitlySpecified() {
			edgeNameMappedFromIdsOfVertices = new HashMap<String, String>();
			edgeNameMappedFromIdsOfVertices.put(A + B, EDGE_A_to_B);
			edgeNameMappedFromIdsOfVertices.put(A + C, EDGE_A_to_C);
			edgeNameMappedFromIdsOfVertices.put(B + C, EDGE_B_to_C);
			edgeNameMappedFromIdsOfVertices.put(B + D, EDGE_B_to_D);
			edgeNameMappedFromIdsOfVertices.put(C + D, EDGE_C_to_D);			
		}
		public String getEdgeId(String startVertexId, String endVertexId) {
			return edgeNameMappedFromIdsOfVertices.get(startVertexId + endVertexId);
		}
	}	
}