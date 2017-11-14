package com.programmerare.shortestpaths.adapter;

import java.util.List;

import com.programmerare.shortestpaths.utils.EdgeMapper;

public abstract class GraphFactoryBase<T extends Edge> implements GraphFactory<T> {
	
	private final GraphEdgesValidator graphEdgesValidator;
	
	protected GraphFactoryBase() {
		graphEdgesValidator = new GraphEdgesValidator();
	}
	
	// TODO: implement a way to avoid the validation e.g. an overloaded method.
	// Reason: If multiple instances will be used, it is unnecessary to do the validation multiple times.
	// However, it is convenient if the default is to do validation internally without having to specify it.
	public final Graph<T> createGraph(List<T> edges) {
		//graphEdgesValidator.validateEdgesAsAcceptableInputForGraphConstruction(edges);
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
		return createGraphHook(edges, edgeMapper);	
	}

	protected abstract Graph<T> createGraphHook(List<T> edges, EdgeMapper<T> edgeMapper);
}
