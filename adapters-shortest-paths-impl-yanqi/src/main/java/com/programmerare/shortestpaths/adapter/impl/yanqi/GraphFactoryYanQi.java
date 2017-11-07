package com.programmerare.shortestpaths.adapter.impl.yanqi;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.utils.EdgeMapper;
import com.programmerare.shortestpaths.utils.MapperForIntegerIdsAndGeneralStringIds;

// Note that the below class does not really belong to that package but I modified the code in a fork below:
// https://github.com/TomasJohansson/k-shortest-paths-java-version/commits/programmatic-graph-creation-without-using-inputfile
// https://github.com/TomasJohansson/k-shortest-paths-java-version/commit/7130ed623d6e7436cdb8294c0e142a8b4b29a18d
import edu.asu.emit.algorithm.graph.GraphPossibleToCreateProgrammatically;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphFactoryYanQi implements GraphFactory {

	public Graph createGraph(final List<Edge> edges) {
		final EdgeMapper edgeMapper = EdgeMapper.createEdgeMapper(edges);
		final MapperForIntegerIdsAndGeneralStringIds idMapper = MapperForIntegerIdsAndGeneralStringIds.createIdMapper(0);
		
		final List<String> lines = new ArrayList<String>(); 
		for (final Edge edge : edges) {
			lines.add(createStringLine(idMapper, edge));
		}
		
		// "Adaptee" https://en.wikipedia.org/wiki/Adapter_pattern
		final edu.asu.emit.algorithm.graph.Graph graphAdaptee = new GraphPossibleToCreateProgrammatically(
			idMapper.getNumberOfVertices(),
			lines
		);
		
		return new GraphYanQi(graphAdaptee, edgeMapper, idMapper);
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
	private String createStringLine(final MapperForIntegerIdsAndGeneralStringIds idMapper, final Edge edge) {
		int integerIdForStartVertex = idMapper.createOrRetrieveIntegerId(edge.getStartVertex().getVertexId());
		int integerIdForEndVertex = idMapper.createOrRetrieveIntegerId(edge.getEndVertex().getVertexId());
		return integerIdForStartVertex + SPACE + integerIdForEndVertex + SPACE + edge.getEdgeWeight().getWeightValue();
	}
}