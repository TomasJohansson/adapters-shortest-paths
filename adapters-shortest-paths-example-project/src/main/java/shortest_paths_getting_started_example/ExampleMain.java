package shortest_paths_getting_started_example;

import static com.programmerare.shortestpaths.core.impl.EdgeDefaultImpl.createEdgeDefault;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.defaults.PathFinderFactoryBsmockDefault;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.defaults.PathFinderFactoryJgraphtDefault;
import com.programmerare.shortestpaths.adapter.impl.yanqi.defaults.PathFinderFactoryYanQiDefault;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.PathFinderFactoryDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
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

	public static void main(String[] args) {
	
		Vertex a = createVertex("A");
		Vertex b = createVertex("B");
		Vertex c = createVertex("C");
		Vertex d = createVertex("D");

		List<EdgeDefault> edges = new ArrayList<EdgeDefault>();
		edges.add(createEdgeDefault(a, b, createWeight(5)));
		edges.add(createEdgeDefault(a, c, createWeight(6)));
		edges.add(createEdgeDefault(b, c, createWeight(7)));
		edges.add(createEdgeDefault(b, d, createWeight(8)));
		edges.add(createEdgeDefault(c, d, createWeight(9)));

		// the parameter GraphEdgesValidationDesired.NO will be used so therefore do the validation once externally here first
//		GraphEdgesValidator.validateEdgesForGraphCreation(edges);
		GraphEdgesValidator.<PathDefault, EdgeDefault, Vertex, Weight>validateEdgesForGraphCreation(edges);
		
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryJgraphtDefault());
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryYanQiDefault());
		displayShortestPathBetweenEdges(a, d, edges, new PathFinderFactoryBsmockDefault());
	}

	// ---------------------------------------------------------------------------------------
	// TODO: these methods below have been copied to "/adapters-shortest-paths-test/src/test/java/com/programmerare/shortestpaths/adapter/utils/GraphShortestPathAssertionHelper.java"
	// and should be refactored into a reusable utiltity method (probably in core project)	
	private static void displayShortestPathBetweenEdges(Vertex startVertex, Vertex endVertex, List<EdgeDefault> edgesInput, PathFinderFactoryDefault  pathFinderFactory) {
		System.out.println("Implementation " + pathFinderFactory.getClass().getName());
		final PathFinderDefault pathFinder = pathFinderFactory.createPathFinder(edgesInput, GraphEdgesValidationDesired.NO); // do the validation one time instead of doing it for each pathFinderFactory
		final List<PathDefault> shortestPaths = pathFinder.findShortestPaths(startVertex, endVertex, 10); // 10 is max but in this case there are only 3 possible paths to return
		for (PathDefault path : shortestPaths) {
			System.out.println(getPathAsPrettyPrintedStringForConsoleOutput(path));
		}
  		System.out.println("-------------------------------------------------------------");
	}
	
	private static String getPathAsPrettyPrintedStringForConsoleOutput(final PathDefault path) {
		StringBuffer sb = new StringBuffer();
		sb.append(path.getTotalWeightForPath().getWeightValue() + " ( ");
		List<EdgeDefault> edges = path.getEdgesForPath();
		for (int i = 0; i < edges.size(); i++) {
			if(i > 0) {
				sb.append(" + ");		
			}
			sb.append(getEdgeAsPrettyPrintedStringForConsoleOutput(edges.get(i)));
		}
		sb.append(")");
		return sb.toString();
	}
	private static String getEdgeAsPrettyPrintedStringForConsoleOutput(EdgeGenerics<Vertex, Weight> edge) {
		return edge.getEdgeWeight().getWeightValue()  + "[" + edge.getStartVertex().getVertexId() + "--->" + edge.getEndVertex().getVertexId() + "] ";		
	}
}