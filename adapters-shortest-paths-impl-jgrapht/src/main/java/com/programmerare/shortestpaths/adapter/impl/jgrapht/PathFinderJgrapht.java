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
public class PathFinderJgrapht
<P extends Path<E, V, W> ,  E extends Edge<V, W> , V extends Vertex , W extends Weight>
extends PathFinderBase<P, E, V, W> 
implements PathFinder<P, E, V, W> 
//<T extends Edge> extends PathFinderBase<T> implements PathFinder<T> 
{

	private final SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee;
	
	protected PathFinderJgrapht(
		final Graph<E, V, W> graph, 
		final GraphEdgesValidationDesired graphEdgesValidationDesired			
	) {
		super(graph, graphEdgesValidationDesired);
		graphAdaptee = new SimpleDirectedWeightedGraph<String, WeightedEdge>(WeightedEdge.class);
		populateGraphAdapteeWithVerticesAndWeights();
	}

	private void populateGraphAdapteeWithVerticesAndWeights() {
		final List<V> vertices = this.getGraph().getVertices();
		for (final Vertex vertex : vertices) {
			this.graphAdaptee.addVertex(vertex.getVertexId());	
		}
		
		final List<E> edges = this.getGraph().getEdges();
		for (final E edge : edges) {
			final WeightedEdge weightedEdge = this.graphAdaptee.addEdge(edge.getStartVertex().getVertexId(), edge.getEndVertex().getVertexId()); 
			this.graphAdaptee.setEdgeWeight(weightedEdge, edge.getEdgeWeight().getWeightValue()); 
		}		
	}

	@Override
	protected List<P> findShortestPathHook(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		final List<P> paths = new ArrayList<P>();

		final String sourceVertexId = startVertex.getVertexId();
		final String targetVertexId = endVertex.getVertexId();
		
		final KShortestPaths<String, WeightedEdge> ksp = new KShortestPaths<String, WeightedEdge>(graphAdaptee, sourceVertexId, maxNumberOfPaths);
	    final List<GraphPath<String, WeightedEdge>> listOfPaths = ksp.getPaths(targetVertexId);
	    
	    for (final GraphPath<String, WeightedEdge> graphPath : listOfPaths) {
	    	final List<E> edges = new ArrayList<E>();
	    	final List<WeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final WeightedEdge weightedEdge : edgeList) {
	    		final E edge = getOriginalEdgeInstance(weightedEdge);
	    		edges.add(edge);
			}
			// TODO maybe: reflection is currently ALWAYS used in below method.  Maybe use a special case for direct instantiating Weight if it is WeightImpl
	    	final W totalWeight = createWeightInstance(graphPath.getWeight(), edges);
	    	paths.add(createThePath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private E getOriginalEdgeInstance(final WeightedEdge weightedEdge) {
		return super.getOriginalEdgeInstance(weightedEdge.getSourceIdAsStringValue(), weightedEdge.getTargetIdAsStringValue());
	}
}
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleTest.java
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleGraph.java	
//https://stackoverflow.com/questions/20246409/how-to-inlcude-weight-in-edge-of-graph