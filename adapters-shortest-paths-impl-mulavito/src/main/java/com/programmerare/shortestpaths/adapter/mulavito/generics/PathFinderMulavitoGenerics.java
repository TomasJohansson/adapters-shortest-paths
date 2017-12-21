package com.programmerare.shortestpaths.adapter.mulavito.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderBase;
import com.programmerare.shortestpaths.core.pathfactories.PathFactory;

import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import mulavito.algorithms.shortestpath.ksp.Yen;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public class PathFinderMulavitoGenerics 
	< 
		P extends PathGenerics<E, V, W>,  
		E extends EdgeGenerics<V, W>, 
		V extends Vertex, 
		W extends Weight
	>
	extends PathFinderBase<P,  E, V, W> 
	implements PathFinderGenerics<P, E, V, W>
{
	private final Yen<String, EdgeMulavito> yenKShortestPaths;
	private final Graph<String, EdgeMulavito> graphMulavito;
	private final Transformer<EdgeMulavito, Number> weightTransformer;
	
	protected PathFinderMulavitoGenerics(
		final GraphGenerics<E, V, W> graph 
	) {
		this(
			graph, 
			null				
		);
	}
	protected PathFinderMulavitoGenerics(
		final GraphGenerics<E, V, W> graph, 
		final PathFactory<P, E, V, W> pathFactory
	) {
		super(graph, pathFactory);

		weightTransformer = new Transformer<EdgeMulavito, Number>() {
			// @Override
			public Number transform(EdgeMulavito edge) {
				return edge.getWeight();
			}
		};		
		graphMulavito = new DirectedOrderedSparseMultigraph<String, EdgeMulavito>();
		addEdgesToGraph();
		yenKShortestPaths = new Yen<String, EdgeMulavito>(graphMulavito, weightTransformer);
	}

	/**
	 * Precondition: The two fields idMapper and edgeWeightedDigraph must have been created before invoking this method.
	 */
	private void addEdgesToGraph() {
		final List<E> edges = this.getGraph().getEdges();
		for (final E edge : edges) {
			addEdgeToGraph(edge);
		}
	}
	private void addEdgeToGraph(final E edge) {
		addEdgeToGraph(edge.getStartVertex(), edge.getEndVertex(), edge.getEdgeWeight());
	}	
	private void addEdgeToGraph(final V startVertex, final V endVertex, final W edgeWeight) {
		addEdgeToGraph(startVertex.getVertexId(), endVertex.getVertexId(), edgeWeight.getWeightValue());
		
	}
	private void addEdgeToGraph(final String startVertex, final String endVertex, final double weight) {
		graphMulavito.addEdge(new EdgeMulavito(startVertex, endVertex, weight), startVertex, endVertex, EdgeType.DIRECTED);
	}	

	@Override
	protected List<P> findShortestPathHook(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		final List<P> paths = new ArrayList<P>();
		final List<List<EdgeMulavito>> shortestPaths = yenKShortestPaths.getShortestPaths(startVertex.getVertexId(), endVertex.getVertexId(), maxNumberOfPaths);
		for (final List<EdgeMulavito> edgesInPath : shortestPaths) {
			final double totalWeightForPath = calculateTotalWeightForPath(edgesInPath);
			final List<E> edges = new ArrayList<E>();
			for (final EdgeMulavito edgeMulavito : edgesInPath) {
				final E edge = super.getOriginalEdgeInstance(edgeMulavito.getStartVertex(), edgeMulavito.getEndVertex()); 
				edges.add(
					edge
				);				
			}
			final W totalWeight = super.createInstanceWithTotalWeight(totalWeightForPath, edges);			
			paths.add(super.createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private double calculateTotalWeightForPath(final List<EdgeMulavito> edgesForPath) {
		double totalWeight = 0;
		for (EdgeMulavito edge : edgesForPath) {
			totalWeight += edge.getWeight();
		}
		return totalWeight;
	}
	
	// --------------------------------------------------------------------------
	private final static class EdgeMulavito {
		private final String startVertex;
		private final String endVertex;
		private final double weight;

		public EdgeMulavito(final String startVertex, final String endVertex, final double weight) {
			this.startVertex = startVertex;
			this.endVertex = endVertex;
			this.weight = weight;
		}

		public double getWeight() {
			return weight;
		}

		public String getStartVertex() {
			return startVertex;
		}

		public String getEndVertex() {
			return endVertex;
		}
	}
	// --------------------------------------------------------------------------	
}