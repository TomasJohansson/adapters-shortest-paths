package shortest_paths_getting_started_example;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.programmerare.shortestpaths.adapter.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.mulavito.PathFinderFactoryMulavito;
import com.programmerare.shortestpaths.adapter.reneargento.PathFinderFactoryReneArgento;
import com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.adapter.jython_networkx.PathFinderFactoryJythonNetworkx; 
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * This is the only class in this minimal example project showing how to use the adapter library for 
 * calculating the shortest paths in a small graph by using different implementations.
 *
 * Below is the resulting output from running the below main method:
Implementation com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.bsmock.PathFinderFactoryBsmock
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.jgrapht.PathFinderFactoryJgrapht
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.reneargento.PathFinderFactoryReneArgento
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.mulavito.PathFinderFactoryMulavito
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
 * 
 * @author Tomas Johansson
 */
public class ExampleMain {
	/**
	 * 
	 * You can use the following commands to run the main method with Maven:
	 * (after having navigated to the directory with 'cd adapters-shortest-paths-example-project')
	 * 		mvn compile 
	 * 		mvn exec:java -Dexec.mainClass="shortest_paths_getting_started_example.ExampleMain"
	 * @param args not used
	 */
	public static void main(String[] args) {
	
		Vertex a = createVertex("A");
		Vertex b = createVertex("B");
		Vertex c = createVertex("C");
		Vertex d = createVertex("D");

		List<Edge> edges = Arrays.asList(
			createEdge(a, b, createWeight(5)),
			createEdge(a, c, createWeight(6)),
			createEdge(b, c, createWeight(7)),
			createEdge(b, d, createWeight(8)),
			createEdge(c, d, createWeight(9))
		);
		
		Graph graph = createGraph(edges, GraphEdgesValidationDesired.YES);
		
		List<PathFinderFactory> pathFinderFactories = new ArrayList<PathFinderFactory>();
		pathFinderFactories.add(new PathFinderFactoryYanQi());
		pathFinderFactories.add(new PathFinderFactoryBsmock());
		pathFinderFactories.add(new PathFinderFactoryJgrapht());
		pathFinderFactories.add(new PathFinderFactoryReneArgento());
		pathFinderFactories.add(new PathFinderFactoryMulavito());

		for (PathFinderFactory pathFinderFactory : pathFinderFactories) {
			displayShortestPathBetweenEdges(a, d, graph, pathFinderFactory);
		}
		try {
			// The below implementation uses Jython and the Python pip package networkx
			// and will fail if not both of these are installed. 
			displayShortestPathBetweenEdges(a, d, graph, new PathFinderFactoryJythonNetworkx());
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	// ---------------------------------------------------------------------------------------
	// TODO: these methods below have been copied to "/adapters-shortest-paths-test/src/test/java/com/programmerare/shortestpaths/adapter/utils/GraphShortestPathAssertionHelper.java"
	// and should be refactored into a reusable utility method (probably in core project)	
	private static void displayShortestPathBetweenEdges(Vertex startVertex, Vertex endVertex, Graph graph, PathFinderFactory  pathFinderFactory) {
		final List<Edge> edges = graph.getEdges();
		System.out.println("Implementation " + pathFinderFactory.getClass().getName());
		final PathFinder pathFinder = pathFinderFactory.createPathFinder(edges, GraphEdgesValidationDesired.NO); // do the validation one time instead of doing it for each pathFinderFactory
		final List<Path> shortestPaths = pathFinder.findShortestPaths(startVertex, endVertex, 10); // 10 is max but in this case there are only 3 possible paths to return
		for (Path path : shortestPaths) {
			System.out.println(getPathAsPrettyPrintedStringForConsoleOutput(path));
		}
		System.out.println("-------------------------------------------------------------");
	}
	
	private static String getPathAsPrettyPrintedStringForConsoleOutput(final Path path) {
		StringBuffer sb = new StringBuffer();
		sb.append(path.getTotalWeightForPath().getWeightValue() + " ( ");
		List<Edge> edges = path.getEdgesForPath();
		for (int i = 0; i < edges.size(); i++) {
			if(i > 0) {
				sb.append(" + ");		
			}
			sb.append(getEdgeAsPrettyPrintedStringForConsoleOutput(edges.get(i)));
		}
		sb.append(")");
		return sb.toString();
	}
	private static String getEdgeAsPrettyPrintedStringForConsoleOutput(Edge edge) {
		return edge.getEdgeWeight().getWeightValue()  + "[" + edge.getStartVertex().getVertexId() + "--->" + edge.getEndVertex().getVertexId() + "] ";		
	}
}
