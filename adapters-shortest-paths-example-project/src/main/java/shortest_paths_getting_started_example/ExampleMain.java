package shortest_paths_getting_started_example;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

/**
 * This is the only class in a minimal example showing how to use the adapter library for 
 * calculating the shortest paths in a small graph by using different implementations,
 * There is only one dependency in the file pom.xml 
 * (and currently also usage of the "jitpack" repository since there are yet no actual releases to maven central)  
 *
 * Below is the resulting output from running the below main method:
Implementation com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
Implementation com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderFactoryBsmock
13.0 ( 5.0[A--->B]  + 8.0[B--->D] )
15.0 ( 6.0[A--->C]  + 9.0[C--->D] )
21.0 ( 5.0[A--->B]  + 7.0[B--->C]  + 9.0[C--->D] )
-------------------------------------------------------------
 * 
 * @author Tomas Johansson
 */
public class ExampleMain {

	// definiera "convenience" interfaces som nedan för att slipa använda generics ... definiera dem i ett annat paket...
	public interface Edge2 extends Edge<Vertex, Weight> {
	}
	
	public static void main(String[] args) {
		Vertex a = createVertex("A");
		Vertex b = createVertex("B");
		Vertex c = createVertex("C");
		Vertex d = createVertex("D");

//		Edge2 createEdge2 = createEdge(a, b, createWeight(5));
//		Edge<Vertex, Weight> aaa = createEdge2; 
		
		List<Edge<Vertex,Weight>> edges = new ArrayList<Edge<Vertex,Weight>>();
		edges.add(createEdge(a, b, createWeight(5)));
		edges.add(createEdge(a, c, createWeight(6)));
		edges.add(createEdge(b, c, createWeight(7)));
		edges.add(createEdge(b, d, createWeight(8)));
		edges.add(createEdge(c, d, createWeight(9)));

		// the parameter GraphEdgesValidationDesired.NO will be used so therefore do the validation once externally here first
		GraphEdgesValidator.validateEdgesForGraphCreation(edges);
		
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryJgrapht<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryYanQi<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryBsmock<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight>());
	}

	// ---------------------------------------------------------------------------------------
	// TODO: these methods below have been copied to "/adapters-shortest-paths-test/src/test/java/com/programmerare/shortestpaths/adapter/utils/GraphShortestPathAssertionHelper.java"
	// and should be refactored into a reusable utiltity method (probably in core project)	
	private static void displayShortestPathBetweenEdges(Vertex startVertex, Vertex endVertex, List<Edge<Vertex,Weight>> edgesInput, PathFinderFactory<PathFinder<Edge<Vertex, Weight> , Vertex , Weight> , Edge<Vertex, Weight> , Vertex , Weight> pathFinderFactory) {
		System.out.println("Implementation " + pathFinderFactory.getClass().getName());
		PathFinder<Edge<Vertex, Weight> , Vertex , Weight> pathFinder = pathFinderFactory.createPathFinder(edgesInput, GraphEdgesValidationDesired.NO); // do the validation one time instead of doing it for each pathFinderFactory
		List<Path<Edge<Vertex, Weight> , Vertex , Weight>> shortestPaths = pathFinder.findShortestPaths(startVertex, endVertex, 10); // 10 is max but in this case there are only 3 possible paths to return
		for (Path<Edge<Vertex, Weight> , Vertex , Weight> path : shortestPaths) {
			System.out.println(getPathAsPrettyPrintedStringForConsoleOutput(path));
		}
  		System.out.println("-------------------------------------------------------------");
	}
	
	private static String getPathAsPrettyPrintedStringForConsoleOutput(Path<Edge<Vertex, Weight> , Vertex , Weight> path) {
		StringBuffer sb = new StringBuffer();
		sb.append(path.getTotalWeightForPath().getWeightValue() + " ( ");
		List<Edge<Vertex,Weight>> edges = path.getEdgesForPath();
		for (int i = 0; i < edges.size(); i++) {
			if(i > 0) {
				sb.append(" + ");		
			}
			sb.append(getEdgeAsPrettyPrintedStringForConsoleOutput(edges.get(i)));
		}
		sb.append(")");
		return sb.toString();
	}
	private static String getEdgeAsPrettyPrintedStringForConsoleOutput(Edge<Vertex, Weight> edge) {
		return edge.getEdgeWeight().getWeightValue()  + "[" + edge.getStartVertex().getVertexId() + "--->" + edge.getEndVertex().getVertexId() + "] ";		
	}
}