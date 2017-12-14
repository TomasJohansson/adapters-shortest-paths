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
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

import chapter4.section4.DirectedEdge;
import chapter4.section4.EdgeWeightedDigraph;
import chapter4.section4.KShortestPaths;
import chapter4.section4.KShortestPaths.Path;



/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
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
	KShortestPaths kShortestPaths = new KShortestPaths();	
//	private final edu.asu.emit.algorithm.graph.Graph graphAdaptee;
	private final MapperForIntegerIdsAndGeneralStringIds idMapper;
	private EdgeWeightedDigraph edgeWeightedDigraph;
	
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

		edgeWeightedDigraph = new EdgeWeightedDigraph(graph.getVertices().size());
		final MapperForIntegerIdsAndGeneralStringIds idMapper = MapperForIntegerIdsAndGeneralStringIds.createIdMapper(0);
		createListOfVerticesWhileAlsoPopulatingIdMapper(idMapper);
		
		// "Adaptee" https://en.wikipedia.org/wiki/Adapter_pattern		
//		this.graphAdaptee = new GraphPossibleToCreateProgrammatically(
//			idMapper.getNumberOfVertices(),
//			vertices
//		);
		
		
		this.idMapper = idMapper;
	}
	
	private void createListOfVerticesWhileAlsoPopulatingIdMapper(final MapperForIntegerIdsAndGeneralStringIds idMapper) {
		
//		edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 1));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(0, 2, 7));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 1));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(2, 1, 1));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 3));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(1, 4, 2));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(2, 4, 4));
//		edgeWeightedDigraph.addEdge(new DirectedEdge(3, 4, 1));
		
		final List<E> edges = this.getGraph().getEdges();
//		final List<EdgeYanQi> vertices = new ArrayList<EdgeYanQi>();
		for (final E edge : edges) {
			final int integerIdForStartVertex = idMapper.createOrRetrieveIntegerId(edge.getStartVertex().getVertexId());
			final int integerIdForEndVertex = idMapper.createOrRetrieveIntegerId(edge.getEndVertex().getVertexId());
//			vertices.add(new EdgeYanQi(integerIdForStartVertex, integerIdForEndVertex, edge.getEdgeWeight().getWeightValue()));
			edgeWeightedDigraph.addEdge(new DirectedEdge(integerIdForStartVertex, integerIdForEndVertex, edge.getEdgeWeight().getWeightValue()));
		}
	}

	/**
	 * Note that currently there seem to be no way for the "yanqi" implementation to actually specify the number 
	 * of shortest paths to return, but it seems to find all paths.
	 * However, the implementation of this method instead takes care of reducing the result.
	 * Otherwise, if the semantic of the method is not respected it can not for example be tested 
	 * against results from other implementations since then they would return a different number of paths.      
	 */
	@Override
	protected List<P> findShortestPathHook(
		final V startVertex, 
		final V endVertex, 
		final int maxNumberOfPaths
	) {
		final List<P> paths = new ArrayList<P>();
		final int startVertexId = idMapper.createOrRetrieveIntegerId(startVertex.getVertexId());
		final int endVertexId = idMapper.createOrRetrieveIntegerId(endVertex.getVertexId());
		final KShortestPaths kShortestPaths = new KShortestPaths();
		List<Path> kShortestPaths2 = kShortestPaths.getKShortestPaths(edgeWeightedDigraph, startVertexId, endVertexId, maxNumberOfPaths);
		for (Path path : kShortestPaths2) {
			final List<E> edges = new ArrayList<E>();
			final Iterable<DirectedEdge> path2 = path.getPath();
			for (DirectedEdge directedEdge : path2) {
				final E edge = getOriginalEdgeInstance(directedEdge.from(), directedEdge.to()); 
				edges.add(
					edge
				);				
			}
			final W totalWeight = super.createInstanceWithTotalWeight(path.weight(), edges);			
			paths.add(super.createPath(totalWeight, edges));
			if(maxNumberOfPaths == paths.size()) {
				break;
			}
		}
		return Collections.unmodifiableList(paths);
	}



	private E getOriginalEdgeInstance(final int startVertexForEdge, final int endVertexForEdge) {
		final String startVertexId = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(startVertexForEdge);
		final String endVertexId = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(endVertexForEdge);		
		return super.getOriginalEdgeInstance(startVertexId, endVertexId);
	}
}