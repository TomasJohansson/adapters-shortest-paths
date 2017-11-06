package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.utils.EdgeMapper;

/**
 * @author Tomas Johansson
 */
public final class GraphJgrapht implements Graph {

	private final SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee;
	private final EdgeMapper edgeMapper;

	public GraphJgrapht(
		final  SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee, 
		final EdgeMapper edgeMapper
	) {
		this.graphAdaptee = graphAdaptee;
		this.edgeMapper = edgeMapper;
	}

	public List<Path> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path> paths = new ArrayList<Path>();

		final String sourceVertexId = startVertex.getVertexId();
		final String targetVertexId = endVertex.getVertexId();
		
		final KShortestPaths<String, WeightedEdge> ksp = new KShortestPaths(graphAdaptee, sourceVertexId, maxNumberOfPaths);
	    final List<GraphPath<String, WeightedEdge>> listOfPaths = ksp.getPaths(targetVertexId);
	    
	    for (final GraphPath<String, WeightedEdge> graphPath : listOfPaths) {
	    	final List<Edge> edges = new ArrayList<Edge>();
	    	final List<WeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final WeightedEdge weightedEdge : edgeList) {
	    		edges.add(
					createEdge(
						createVertex(weightedEdge.getSourceIdAsStringValue()), 
						createVertex(weightedEdge.getTargetIdAsStringValue()), 
						createWeight(weightedEdge.getWeightValue())	    				
					)
				);
			}
			final List<Edge> originalObjectInstancesOfTheEdges = edgeMapper.getOriginalObjectInstancesOfTheEdges(edges);
			paths.add(createPath(createWeight(graphPath.getWeight()), originalObjectInstancesOfTheEdges));
		}
		return Collections.unmodifiableList(paths);
	}
}