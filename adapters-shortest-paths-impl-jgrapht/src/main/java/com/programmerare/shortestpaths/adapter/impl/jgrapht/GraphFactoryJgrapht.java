package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import static com.programmerare.shortestpaths.utils.VertexUtility.getAllVerticesFromVerticesButWithoutDuplicates;

import java.util.List;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.utils.EdgeMapper;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public class GraphFactoryJgrapht implements GraphFactory {

	public Graph createGraph(List<Edge> edges) {
		final EdgeMapper edgeMapper = EdgeMapper.createEdgeMapper(edges);
		
		final SimpleDirectedWeightedGraph<String, WeightedEdge>  graphAdaptee = 
	            new SimpleDirectedWeightedGraph<String, WeightedEdge>
	            (WeightedEdge.class);
		
		final List<Vertex> vertices = getAllVerticesFromVerticesButWithoutDuplicates(edges);
		
		for (final Vertex vertex : vertices) {
			graphAdaptee.addVertex(vertex.getVertexId());	
		}
		for (final Edge edge : edges) {
			final WeightedEdge e = graphAdaptee.addEdge(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId()); 
			graphAdaptee.setEdgeWeight(e, edge.getEdgeWeight().getWeightValue()); 
		}
		
	    return new GraphJgrapht(graphAdaptee, edgeMapper);
	}
}

//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleTest.java
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleGraph.java	
//https://stackoverflow.com/questions/20246409/how-to-inlcude-weight-in-edge-of-graph