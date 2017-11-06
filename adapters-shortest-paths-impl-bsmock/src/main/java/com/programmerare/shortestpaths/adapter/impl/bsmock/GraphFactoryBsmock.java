package com.programmerare.shortestpaths.adapter.impl.bsmock;

import java.util.List;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.utils.EdgeMapper;

/**
 * "Adapter" implementation of the "Target" interface 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
 */
public final class GraphFactoryBsmock implements GraphFactory {

	public Graph createGraph(List<Edge> edges) {
		final EdgeMapper edgeMapper = EdgeMapper.createEdgeMapper(edges);

		// "Adaptee" https://en.wikipedia.org/wiki/Adapter_pattern
		final edu.ufl.cise.bsmock.graph.Graph graphAdaptee = new edu.ufl.cise.bsmock.graph.Graph();
		
		for (final Edge edge : edges) {
			graphAdaptee.addEdge(
				edge.getStartVertex().getVertexId(), 
				edge.getEndVertex().getVertexId(), 
				edge.getEdgeWeight().getWeightValue()
			);	
		}
		return new GraphBsmock(graphAdaptee, edgeMapper);
	}
}