package com.programmerare.shortestpaths.adapter.jgrapht.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.adapter.jgrapht.JGraphtAlgorithm;
import org.jgrapht.GraphPath;

import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

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
 * @see <a href="https://en.wikipedia.org/wiki/Adapter_pattern">Adapter pattern</a>
 */
public class PathFinderJgraphtGenerics
	< 
		P extends PathGenerics<E, V, W>,  
		E extends EdgeGenerics<V, W>, 
		V extends Vertex, 
		W extends Weight
	>
	extends PathFinderBase<P,  E, V, W> 
	implements PathFinderGenerics<P, E, V, W> 
{
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graphAdaptee;

	private final JGraphtAlgorithm jGraphtAlgorithm;
	
	protected PathFinderJgraphtGenerics(
		final GraphGenerics<E, V, W> graph 
	) {
		this(
			graph, 
			null,
			JGraphtAlgorithm.getDefault()	
		);
	}
	protected PathFinderJgraphtGenerics(
		final GraphGenerics<E, V, W> graph, 
		final PathFactory<P, E, V, W> pathFactory,
		final JGraphtAlgorithm jGraphtAlgorithm
	) {
		super(graph, pathFactory);
		this.jGraphtAlgorithm = jGraphtAlgorithm;
		graphAdaptee = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		populateGraphAdapteeWithVerticesAndWeights();
	}
	
	private void populateGraphAdapteeWithVerticesAndWeights() {
		final List<V> vertices = this.getGraph().getVertices();
		final List<E> edges = this.getGraph().getEdges();
		for (final E edge : edges) {
			Graphs.addEdgeWithVertices(
				this.graphAdaptee,
				edge.getStartVertex().getVertexId(),
				edge.getEndVertex().getVertexId(),
				edge.getEdgeWeight().getWeightValue()
			);
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

		final KShortestPathAlgorithm<String, DefaultWeightedEdge> ksp = JGraphtAlgorithmFactory.CreateKShortestPathAlgorithm(this.jGraphtAlgorithm, graphAdaptee);
		final List<GraphPath<String, DefaultWeightedEdge>> listOfPaths = ksp.getPaths(sourceVertexId, targetVertexId, maxNumberOfPaths);
	    
	    for (final GraphPath<String, DefaultWeightedEdge> graphPath : listOfPaths) {
	    	final List<E> edges = new ArrayList<E>();
	    	final List<DefaultWeightedEdge> edgeList = graphPath.getEdgeList();
	    	for (final DefaultWeightedEdge weightedEdge : edgeList) {
	    		final E edge = getOriginalEdgeInstance(weightedEdge);
	    		edges.add(edge);
			}
	    	final W totalWeight = super.createInstanceWithTotalWeight(graphPath.getWeight(), edges);
	    	paths.add(super.createPath(totalWeight, edges));
		}
		//System.out.println("ksp implementation class: " + ksp.getClass().getName());
		//	ksp implementation class: org.jgrapht.alg.shortestpath.YenKShortestPath
		//	ksp implementation class: org.jgrapht.alg.shortestpath.KShortestSimplePaths
		return Collections.unmodifiableList(paths);
	}

	private E getOriginalEdgeInstance(final DefaultWeightedEdge weightedEdge) {
		final String edgeSource = this.graphAdaptee.getEdgeSource(weightedEdge);
		final String edgeTarget = this.graphAdaptee.getEdgeTarget(weightedEdge);		
		return super.getOriginalEdgeInstance(edgeSource, edgeTarget);
	}
}
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleTest.java
//https://github.com/jgrapht/jgrapht/blob/2432532a6642e27d99c8124a094751577a4df655/jgrapht-core/src/test/java/org/jgrapht/alg/KSPExampleGraph.java	
//https://stackoverflow.com/questions/20246409/how-to-inlcude-weight-in-edge-of-graph