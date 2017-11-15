package com.programmerare.shortestpaths.adapter.impl.yanqi;

import static com.programmerare.shortestpaths.core.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.EdgeMapper;
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

import edu.asu.emit.algorithm.graph.abstraction.BaseVertex;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;


/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphYanQi<T extends Edge> implements Graph<T> { 

	private final edu.asu.emit.algorithm.graph.Graph graph;
	private final MapperForIntegerIdsAndGeneralStringIds idMapper;
	private final EdgeMapper<T> edgeMapper;

	public GraphYanQi(
		final edu.asu.emit.algorithm.graph.Graph graph, 
		final EdgeMapper<T> edgeMapper, 
		final MapperForIntegerIdsAndGeneralStringIds idMapper
	) {
		this.graph = graph;
		this.edgeMapper = edgeMapper;
		this.idMapper = idMapper;
	}

	/**
	 * Note that currently there seem to be no way for the "yanqi" implementation to actually specify the number 
	 * of shortest paths to return, but it seems to find all paths.
	 * However, the implementation of this method instead takes care of reducing the result.
	 * Otherwise, if the semantic of the method is not respected it can not for example be tested 
	 * against results from other implementations since then they would return a different number of paths.      
	 */
	public List<Path<T>> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path<T>> paths = new ArrayList<Path<T>>();
		final int startVertexId = idMapper.createOrRetrieveIntegerId(startVertex.getVertexId());
		final int endVertexId = idMapper.createOrRetrieveIntegerId(endVertex.getVertexId());
		final YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph, graph.getVertex(startVertexId), graph.getVertex(endVertexId));
		while(yenAlg.hasNext()) {
			final edu.asu.emit.algorithm.graph.Path path = yenAlg.next();
			final List<T> edges = new ArrayList<T>();
			final List<BaseVertex> vertexList = path.getVertexList();
			for (int i = 1; i < vertexList.size(); i++) {
				final BaseVertex startVertexForEdge = vertexList.get(i-1);
				final BaseVertex endVertexForEdge = vertexList.get(i);
				final String startVertexString = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(startVertexForEdge.getId());
				final String endVertexString = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(endVertexForEdge.getId());
				final T edge = edgeMapper.getOriginalEdgeInstance(startVertexString, endVertexString);
				edges.add(
					edge
				);				
			}
			final Weight totalWeight = createWeight(path.getWeight());
			paths.add(createPath(totalWeight, edges));
			if(maxNumberOfPaths == paths.size()) {
				break;
			}
		}
		return Collections.unmodifiableList(paths);
	}
}