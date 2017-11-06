package com.programmerare.shortestpaths.adapter.impl.yanqi;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.utils.EdgeMapper;
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

import edu.asu.emit.algorithm.graph.abstraction.BaseVertex;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;


/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphYanQi implements Graph {

	private final edu.asu.emit.algorithm.graph.Graph graph;
	private final MapperForIntegerIdsAndGeneralStringIds idMapper;
	private final EdgeMapper edgeMapper;

	public GraphYanQi(final edu.asu.emit.algorithm.graph.Graph graph, final EdgeMapper edgeMapper, final MapperForIntegerIdsAndGeneralStringIds idMapper) {
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
	public List<Path> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path> paths = new ArrayList<Path>();
		final int startVertexId = idMapper.createOrRetrieveIntegerId(startVertex.getVertexId());
		final int endVertexId = idMapper.createOrRetrieveIntegerId(endVertex.getVertexId());
		final YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph, graph.getVertex(startVertexId), graph.getVertex(endVertexId));
		while(yenAlg.hasNext()) {
			
			final edu.asu.emit.algorithm.graph.Path path = yenAlg.next();
			
			final List<Edge> edges = new ArrayList<Edge>();
			final List<BaseVertex> vertexList = path.getVertexList();
			for (int i = 1; i < vertexList.size(); i++) {
				final BaseVertex startVertexForEdge = vertexList.get(i-1);
				final BaseVertex endVertexForEdge = vertexList.get(i);
				//startVertexForEdge.getWeight() // weight for a vertex ... unclear how to get the weight for the edge
				final String startVertexString = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(startVertexForEdge.getId());
				final String endVertexString = idMapper.getBackThePreviouslyStoredGeneralStringIdForInteger(endVertexForEdge.getId());
				edges.add(
					createEdge(
						createVertex(startVertexString), 
						createVertex(endVertexString), 
						createWeight(0) // the weight will be retrieved from the original instance instead below
					)
				);
			}
			final List<Edge> originalObjectInstancesOfTheEdges = edgeMapper.getOriginalObjectInstancesOfTheEdges(edges); 
			paths.add(createPath(createWeight(path.getWeight()), originalObjectInstancesOfTheEdges));
			if(maxNumberOfPaths == paths.size()) {
				break;
			}
		}
		return Collections.unmodifiableList(paths);
	}
}