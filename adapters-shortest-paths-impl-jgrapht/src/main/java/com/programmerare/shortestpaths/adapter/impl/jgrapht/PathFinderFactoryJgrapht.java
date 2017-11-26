package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import static com.programmerare.shortestpaths.utils.VertexUtility.getAllVerticesFromEdgesButWithoutDuplicates;

import java.util.List;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.impl.EdgeMapper;
import com.programmerare.shortestpaths.core.impl.PathFinderFactoryBase;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public class PathFinderFactoryJgrapht<T extends Edge> extends PathFinderFactoryBase<T> implements PathFinderFactory<T> {
 
	protected PathFinder<T> createPathFinderHook(final Graph<T> graph, final EdgeMapper<T> edgeMapper) {
		final List<T> edges = graph.getAllEdges();
		final SimpleDirectedWeightedGraph<String, WeightedEdge>  graphAdaptee = 
	            new SimpleDirectedWeightedGraph<String, WeightedEdge>
	            (WeightedEdge.class);
		
		final List<Vertex> vertices = getAllVerticesFromEdgesButWithoutDuplicates(edges);
		for (final Vertex vertex : vertices) {
			graphAdaptee.addVertex(vertex.getVertexId());	
		}
		for (final T edge : edges) {
			final WeightedEdge weightedEdge = graphAdaptee.addEdge(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId()); 
			graphAdaptee.setEdgeWeight(weightedEdge, edge.getEdgeWeight().getWeightValue()); 
		}
	    return new PathFinderJgrapht<T>(graphAdaptee, edgeMapper);
	}
}

//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleTest.java
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleGraph.java	
//https://stackoverflow.com/questions/20246409/how-to-inlcude-weight-in-edge-of-graph