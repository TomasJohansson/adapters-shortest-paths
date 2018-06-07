package jgrapht_fork_kevemueller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.alg.shortestpath.YenKShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class KShortestPathAlgorithmTest {
	
	private Graph<String, DefaultWeightedEdge> graph;
	
	@Before
	public void before() {
		graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        Graphs.addEdgeWithVertices(graph, "A", "B", 5);
        Graphs.addEdgeWithVertices(graph, "A", "C", 6);
        Graphs.addEdgeWithVertices(graph, "B", "C", 7);
        Graphs.addEdgeWithVertices(graph, "B", "D", 8);
        Graphs.addEdgeWithVertices(graph, "C", "D", 9);
	}

	@Test
	public void testKShortestPaths() {
		// Succeeds
		testKShortestPathAlgorithm(
			new KShortestPaths<String, DefaultWeightedEdge>(graph, MAX_NUMBER_OF_PATHS)
		);
	}
	
	@Test
	public void testYen() {
		// Fails
		testKShortestPathAlgorithm(
			new YenKShortestPaths<String, DefaultWeightedEdge>(graph, MAX_NUMBER_OF_PATHS)
		);
		/*
		Version tested when it fails:
		The (current) latest version (git commit 9cab11ba8e89a2446c10a441486f46db96eae67f) 
		https://github.com/kevemueller/jgrapht/commits/master
		The Maven pom.xml dependency used:
		<dependency>
			<groupId>com.github.kevemueller</groupId>
			<artifactId>jgrapht</artifactId>
			<version>9cab11ba8e89a2446c10a441486f46db96eae67f</version> 
		</dependency>
		*/		
	}

	private void testKShortestPathAlgorithm(KShortestPathAlgorithm<String, DefaultWeightedEdge> pathFinder) {
		List<GraphPath<String, DefaultWeightedEdge>> paths = pathFinder.getPaths("A", "D");
		assertPath(paths.get(0), 13, "A", "B", "D");
		assertPath(paths.get(1), 15, "A", "C", "D");
		assertPath(paths.get(2), 21, "A", "B", "C", "D");
	}
	
	private void assertPath(
		GraphPath<String, DefaultWeightedEdge> actualPath, 
		double expectedTotalWeightForPath, String... expectedVertices
	) {
		assertEquals(expectedTotalWeightForPath, actualPath.getWeight(), SMALL_DELTA_VALUE);
		List<String> actualVertexList = actualPath.getVertexList();
		assertEquals(expectedVertices.length, actualVertexList.size());
		for (int i = 0; i < expectedVertices.length; i++) {
			assertEquals(expectedVertices[i], actualVertexList.get(i));
		}
	}

	private final static int MAX_NUMBER_OF_PATHS = 10;
	private final static double SMALL_DELTA_VALUE = 0.0000001;
}