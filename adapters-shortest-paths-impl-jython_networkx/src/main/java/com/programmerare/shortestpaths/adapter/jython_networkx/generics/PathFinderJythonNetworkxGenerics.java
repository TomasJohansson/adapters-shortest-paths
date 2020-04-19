package com.programmerare.shortestpaths.adapter.jython_networkx.generics;

import com.programmerare.shortestpaths.adaptee.jython_networkx.PathFinder;
import com.programmerare.shortestpaths.adaptee.jython_networkx.PathFinderFactory;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderBase;
import com.programmerare.shortestpaths.core.pathfactories.PathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see <a href="https://en.wikipedia.org/wiki/Adapter_pattern">Adapter pattern</a>
 */
public class PathFinderJythonNetworkxGenerics 
	< 
		P extends PathGenerics<E, V, W>,  
		E extends EdgeGenerics<V, W>, 
		V extends Vertex, 
		W extends Weight
	>
	extends PathFinderBase<P,  E, V, W> 
	implements PathFinderGenerics<P, E, V, W>
{
	private final PathFinder pathFinderJython;
	
	protected PathFinderJythonNetworkxGenerics(
		final GraphGenerics<E, V, W> graph 
	) {
		this(
			graph, 
			null				
		);
	}

	protected PathFinderJythonNetworkxGenerics(
		final GraphGenerics<E, V, W> graph, 
		final PathFactory<P, E, V, W> pathFactory
	) {
		super(graph, pathFactory);

		// VERY VERY SLOW at first invocation i.e. when the singleton instance is created:
		final PathFinderFactory pathFinderFactory = PathFinderFactory.getInstance();

		pathFinderJython = pathFinderFactory.createInstance();
		addEdgesToGraph();
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
		pathFinderJython.addGraphEdge(startVertex, endVertex, weight);
	}	

	@Override
	protected List<P> findShortestPathHook(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		final List<P> paths = new ArrayList<P>();
		final List<List<String>> shortestPaths = pathFinderJython.findShortestPaths(startVertex.getVertexId(), endVertex.getVertexId(), maxNumberOfPaths);
		for (final List<String> verticesInPath : shortestPaths) {
			final List<E> edges = new ArrayList<E>();
			for (int i = 0; i<verticesInPath.size()-1; i++) {
				String pathStartVertex = verticesInPath.get(i);
				String pathEndVertex = verticesInPath.get(i+1);
				final E edge = super.getOriginalEdgeInstance(pathStartVertex, pathEndVertex);
				edges.add(
					edge
				);				
			}
			final double totalWeightForPath = calculateTotalWeightForPath(edges);
			final W totalWeight = super.createInstanceWithTotalWeight(totalWeightForPath, edges);
			paths.add(super.createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private double calculateTotalWeightForPath(final List<E> edgesForPath) {
		double totalWeight = 0;
		for (E edge : edgesForPath) {
			totalWeight += edge.getEdgeWeight().getWeightValue();
		}
		return totalWeight;
	}
}
