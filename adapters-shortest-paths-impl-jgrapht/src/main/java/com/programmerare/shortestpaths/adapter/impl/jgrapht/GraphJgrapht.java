package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.EdgeMapper;

/**
 * @author Tomas Johansson
 */
public final class GraphJgrapht<T extends Edge> implements Graph<T> {

	private final SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee;
	private final EdgeMapper<T> edgeMapper;

	public GraphJgrapht(
		final  SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee, 
		final EdgeMapper<T> edgeMapper
	) {
		this.graphAdaptee = graphAdaptee;
		this.edgeMapper = edgeMapper;
	}

	public List<Path<T>> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path<T>> paths = new ArrayList<Path<T>>();

		final String sourceVertexId = startVertex.getVertexId();
		final String targetVertexId = endVertex.getVertexId();
		
		final KShortestPaths<String, WeightedEdge> ksp = new KShortestPaths(graphAdaptee, sourceVertexId, maxNumberOfPaths);
	    final List<GraphPath<String, WeightedEdge>> listOfPaths = ksp.getPaths(targetVertexId);
	    
	    for (final GraphPath<String, WeightedEdge> graphPath : listOfPaths) {
	    	final List<T> edges = new ArrayList<T>();
	    	final List<WeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final WeightedEdge weightedEdge : edgeList) {
	    		final T edge = edgeMapper.getOriginalEdgeInstance(weightedEdge.getSourceIdAsStringValue(), weightedEdge.getTargetIdAsStringValue());
	    		edges.add(edge);
			}
	    	final Weight totalWeight = createWeight(graphPath.getWeight());
			paths.add(createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}
}