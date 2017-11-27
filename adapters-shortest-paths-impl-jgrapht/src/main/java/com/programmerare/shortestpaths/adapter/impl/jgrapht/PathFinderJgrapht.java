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
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.PathFinderBase;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class PathFinderJgrapht<T extends Edge> extends PathFinderBase<T> implements PathFinder<T> {

	private final SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee;
	
	PathFinderJgrapht(
		final Graph<T> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired			
	) {
		super(graph, graphEdgesValidationDesired);
		graphAdaptee = new SimpleDirectedWeightedGraph<String, WeightedEdge>(WeightedEdge.class);
		populateGraphAdapteeWithVerticesAndWeights();
	}

	private void populateGraphAdapteeWithVerticesAndWeights() {
		final List<Vertex> vertices = this.getGraph().getAllVertices();
		for (final Vertex vertex : vertices) {
			this.graphAdaptee.addVertex(vertex.getVertexId());	
		}
		
		final List<T> edges = this.getGraph().getAllEdges();
		for (final T edge : edges) {
			final WeightedEdge weightedEdge = this.graphAdaptee.addEdge(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId()); 
			this.graphAdaptee.setEdgeWeight(weightedEdge, edge.getEdgeWeight().getWeightValue()); 
		}		
	}

	@Override
	protected List<Path<T>> findShortestPathHook(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path<T>> paths = new ArrayList<Path<T>>();

		final String sourceVertexId = startVertex.getVertexId();
		final String targetVertexId = endVertex.getVertexId();
		
		final KShortestPaths<String, WeightedEdge> ksp = new KShortestPaths<String, WeightedEdge>(graphAdaptee, sourceVertexId, maxNumberOfPaths);
	    final List<GraphPath<String, WeightedEdge>> listOfPaths = ksp.getPaths(targetVertexId);
	    
	    for (final GraphPath<String, WeightedEdge> graphPath : listOfPaths) {
	    	final List<T> edges = new ArrayList<T>();
	    	final List<WeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final WeightedEdge weightedEdge : edgeList) {
	    		final T edge = getOriginalEdgeInstance(weightedEdge);
	    		edges.add(edge);
			}
	    	final Weight totalWeight = createWeight(graphPath.getWeight());
			paths.add(createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private T getOriginalEdgeInstance(final WeightedEdge weightedEdge) {
		return super.getOriginalEdgeInstance(weightedEdge.getSourceIdAsStringValue(), weightedEdge.getTargetIdAsStringValue());
	}
}
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleTest.java
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleGraph.java	
//https://stackoverflow.com/questions/20246409/how-to-inlcude-weight-in-edge-of-graph