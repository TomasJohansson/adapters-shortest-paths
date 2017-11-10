package shortest_paths_getting_started_example;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;

import java.util.Arrays;
import java.util.List;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;

/**
 * This is the only class in a minimal example showing how to use the adapter library for 
 * calculating the shortest paths in a small graph by using different implementations,
 * There is only one dependency in the file pom.xml 
 * (and currently also usage of the "jitpack" repository since there are yet no actual releases to maven central)  
 *
 * Below is the resulting output from running the below main method:
Implementation com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
 * 
 * @author Tomas Johansson
 */
public class ExampleMain {

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

		displayShortestPathBetweenEdges(a, d, edges, new GraphFactoryJgrapht<Edge>());
		displayShortestPathBetweenEdges(a, d, edges, new GraphFactoryYanQi<Edge>());
		displayShortestPathBetweenEdges(a, d, edges, new GraphFactoryBsmock<Edge>());
	}

	private static void displayShortestPathBetweenEdges(Vertex startVertex, Vertex endVertex, List<Edge> edgesInput, GraphFactory<Edge> graphFactory) {
		System.out.println("Implementation " + graphFactory.getClass().getName());
		Graph<Edge> graph = graphFactory.createGraph(edgesInput);
		List<Path<Edge>> shortestPaths = graph.findShortestPaths(startVertex, endVertex, 10); // 10 is max but in this case there are only 3 possible paths to return
		for (Path<Edge> path : shortestPaths) {
			System.out.println(getPathAsPrettyPrintedStringForConsoleOutput(path));
		}
		System.out.println("-------------------------------------------------------------");
	}
	
	private static String getPathAsPrettyPrintedStringForConsoleOutput(Path<Edge> path) {
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