package com.programmerare.shortestpaths.adapter.impl.yanqi;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.edu.asu.emit.algorithm.graph.EdgeYanQi;
import com.programmerare.edu.asu.emit.algorithm.graph.GraphPossibleToCreateProgrammatically;
import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.utils.EdgeMapper;
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphFactoryYanQi<T extends Edge> implements GraphFactory<T> {

	public Graph<T> createGraph(final List<T> edges) {
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
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
	
	/**
	 * @param idMapper see the documentation of the class {@link MapperForIntegerIdsAndGeneralStringIds}
	 * @param edge
	 * @return a line of strings where each line has three parts separated with a space.
	 * The first part is an integer id representing the 'from vertex' of the edge.
	 * The second part is an integer id representing the 'to vertex' of the edge,
	 * The third part is a double value representing the weight of the edge, 
	 * See also the example in method 'testGraphPossibleToCreateProgramatically'
	 *  at this page: https://github.com/TomasJohansson/k-shortest-paths-java-version/commit/7130ed623d6e7436cdb8294c0e142a8b4b29a18d
	 */
	private String createStringLine(final MapperForIntegerIdsAndGeneralStringIds idMapper, final T edge) {
		int integerIdForStartVertex = idMapper.createOrRetrieveIntegerId(edge.getStartVertex().getVertexId());
		int integerIdForEndVertex = idMapper.createOrRetrieveIntegerId(edge.getEndVertex().getVertexId());
		return integerIdForStartVertex + SPACE + integerIdForEndVertex + SPACE + edge.getEdgeWeight().getWeightValue();
	}
}