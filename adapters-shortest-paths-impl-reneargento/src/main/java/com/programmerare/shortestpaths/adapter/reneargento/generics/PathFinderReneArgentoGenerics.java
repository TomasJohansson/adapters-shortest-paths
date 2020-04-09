package com.programmerare.shortestpaths.adapter.reneargento.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathFinderGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;
import com.programmerare.shortestpaths.core.impl.generics.PathFinderBase;
import com.programmerare.shortestpaths.core.pathfactories.PathFactory;
import com.programmerare.shortestpaths.core.utils.MapperForIntegerIdsAndGeneralStringIds;

import chapter4.section4.DirectedEdge;
import chapter4.section4.EdgeWeightedDigraph;
import chapter4.section4.KShortestPaths;
import chapter4.section4.KShortestPaths.Path;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see <a href="https://en.wikipedia.org/wiki/Adapter_pattern">Adapter pattern</a>
 */
public class PathFinderReneArgentoGenerics 
	< 
		P extends PathGenerics<E, V, W>,  
		E extends EdgeGenerics<V, W>, 
		V extends Vertex, 
		W extends Weight
	>
	extends PathFinderBase<P,  E, V, W> 
	implements PathFinderGenerics<P, E, V, W>
{
	private final KShortestPaths kShortestPaths;	
	private final MapperForIntegerIdsAndGeneralStringIds idMapper;
	private final EdgeWeightedDigraph edgeWeightedDigraph;
	
	protected PathFinderReneArgentoGenerics(
		final GraphGenerics<E, V, W> graph 
	) {
		this(
			graph, 
			null				
		);
	}
	protected PathFinderReneArgentoGenerics(
		final GraphGenerics<E, V, W> graph, 
		final PathFactory<P, E, V, W> pathFactory
	) {
		super(graph, pathFactory);

		kShortestPaths = new KShortestPaths();
		edgeWeightedDigraph = new EdgeWeightedDigraph(graph.getVertices().size());
		
		idMapper = MapperForIntegerIdsAndGeneralStringIds.createIdMapper(0);
		addEdgesToGraphWhileAlsoPopulatingIdMapper();
	}

	/**
	 * Precondition: The two fields idMapper and edgeWeightedDigraph must have been created before invoking this method.
	 */
	private void addEdgesToGraphWhileAlsoPopulatingIdMapper() {
		final List<E> edges = this.getGraph().getEdges();
		for (final E edge : edges) {
			final int integerIdForStartVertex = idMapper.createOrRetrieveIntegerId(edge.getStartVertex().getVertexId());
			final int integerIdForEndVertex = idMapper.createOrRetrieveIntegerId(edge.getEndVertex().getVertexId());
			edgeWeightedDigraph.addEdge(new DirectedEdge(integerIdForStartVertex, integerIdForEndVertex, edge.getEdgeWeight().getWeightValue()));
		}
	}

	@Override
	protected List<P> findShortestPathHook(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		final List<P> paths = new ArrayList<P>();
		final int startVertexId = idMapper.createOrRetrieveIntegerId(startVertex.getVertexId());
		final int endVertexId = idMapper.createOrRetrieveIntegerId(endVertex.getVertexId());
		final List<Path> shortestPaths = kShortestPaths.getKShortestPaths(edgeWeightedDigraph, startVertexId, endVertexId, maxNumberOfPaths);
		for (final Path path : shortestPaths) {
			final List<E> edges = new ArrayList<E>();
			final Iterable<DirectedEdge> edgesInPath = path.getPath();
			for (final DirectedEdge directedEdge : edgesInPath) {
				final E edge = getOriginalEdgeInstance(directedEdge.from(), directedEdge.to()); 
				edges.add(
					edge
				);				
			}
			final W totalWeight = super.createInstanceWithTotalWeight(path.weight(), edges);			
			paths.add(super.createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

	private E getOriginalEdgeInstance(final int startVertexForEdge, final int endVertexForEdge) {
		final String startVertexId = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(startVertexForEdge);
		final String endVertexId = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(endVertexForEdge);		
		return super.getOriginalEdgeInstance(startVertexId, endVertexId);
	}
}
