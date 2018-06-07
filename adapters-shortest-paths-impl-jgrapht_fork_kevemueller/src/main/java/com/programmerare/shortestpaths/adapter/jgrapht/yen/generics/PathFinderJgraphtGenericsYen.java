package com.programmerare.shortestpaths.adapter.jgrapht.yen.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.YenKShortestPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
//
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderBase;
import com.programmerare.shortestpaths.core.pathfactories.PathFactory;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public class PathFinderJgraphtGenericsYen
	< 
		P extends PathGenerics<E, V, W>,  
		E extends EdgeGenerics<V, W>, 
		V extends Vertex, 
		W extends Weight
	>
	extends PathFinderBase<P,  E, V, W> 
	implements PathFinderGenerics<P, E, V, W> 
{
	private final SimpleDirectedWeightedGraph<String, WeightedEdge> graphAdaptee;
	
	protected PathFinderJgraphtGenericsYen(
		final GraphGenerics<E, V, W> graph 
	) {
		this(
			graph, 
			null				
		);
	}
	protected PathFinderJgraphtGenericsYen(
		final GraphGenerics<E, V, W> graph, 
		final PathFactory<P, E, V, W> pathFactory
	) {
		super(graph, pathFactory);
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
		
		final YenKShortestPaths<String, WeightedEdge> yenKShortestPaths 
			= new YenKShortestPaths<String, WeightedEdge>(graphAdaptee, 10);
		
		final List<GraphPath<String, WeightedEdge>> yenPaths = yenKShortestPaths.getPaths(sourceVertexId, targetVertexId);
		for (final GraphPath<String, WeightedEdge> graphPath : yenPaths) {
	    	final List<E> edges = new ArrayList<E>();
	    	final List<WeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final WeightedEdge weightedEdge : edgeList) {
	    		final E edge = getOriginalEdgeInstance(weightedEdge);
	    		edges.add(edge);
			}
	    	final W totalWeight = super.createInstanceWithTotalWeight(graphPath.getWeight(), edges);
	    	paths.add(super.createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private E getOriginalEdgeInstance(final WeightedEdge weightedEdge) {
		return super.getOriginalEdgeInstance(weightedEdge.getSourceIdAsStringValue(), weightedEdge.getTargetIdAsStringValue());
	}
}