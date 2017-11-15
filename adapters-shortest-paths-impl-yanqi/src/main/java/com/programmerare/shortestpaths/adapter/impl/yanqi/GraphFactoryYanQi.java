package com.programmerare.shortestpaths.adapter.impl.yanqi;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.edu.asu.emit.algorithm.graph.EdgeYanQi;
import com.programmerare.edu.asu.emit.algorithm.graph.GraphPossibleToCreateProgrammatically;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.impl.EdgeMapper;
import com.programmerare.shortestpaths.core.impl.GraphFactoryBase;
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphFactoryYanQi<T extends Edge> extends GraphFactoryBase<T> implements GraphFactory<T> {

	protected Graph<T> createGraphHook(final List<T> edges, final EdgeMapper<T> edgeMapper) {
		final MapperForIntegerIdsAndGeneralStringIds idMapper = MapperForIntegerIdsAndGeneralStringIds.createIdMapper(0);
		
		final List<EdgeYanQi> vertices = new ArrayList<EdgeYanQi>();

		for (final T edge : edges) {
			final int integerIdForStartVertex = idMapper.createOrRetrieveIntegerId(edge.getStartVertex().getVertexId());
			final int integerIdForEndVertex = idMapper.createOrRetrieveIntegerId(edge.getEndVertex().getVertexId());
			vertices.add(new EdgeYanQi(integerIdForStartVertex, integerIdForEndVertex, edge.getEdgeWeight().getWeightValue()));
		}
		
		// "Adaptee" https://en.wikipedia.org/wiki/Adapter_pattern
		final edu.asu.emit.algorithm.graph.Graph graphAdaptee = new GraphPossibleToCreateProgrammatically(
			idMapper.getNumberOfVertices(),
			vertices
		);
		
		return new GraphYanQi<T>(graphAdaptee, edgeMapper, idMapper);
	}


	private final static String SPACE = " ";
}