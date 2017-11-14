package com.programmerare.shortestpaths.adapter;

import java.util.List;

import com.programmerare.shortestpaths.utils.EdgeMapper;

public abstract class GraphFactoryBase<T extends Edge> implements GraphFactory<T> {
	public final Graph<T> createGraph(List<T> edges) {
		final EdgeMapper<T> edgeMapper = EdgeMapper.createEdgeMapper(edges);
		// TODO: implement common code, e.g. input validation, in this common base class for all GraphFactories
		// * all three aggregated objects (weight and both vertices) must be non-null 
		// * all vertex ids (start and end vertices in each edge) must be non-null and non-blank
		// * all edges must be unique in two ways:
		// 		unique ids
		//  	unique combination of start vertex and end vertex (i.e. not multiple weights for an edge between two vertices)
		//  	(but it can be noted that often the above two are same i.e. the vertex id is a concatenation if not set explicitly) 	
		return createGraphHook(edges, edgeMapper);	
	}
	
	protected abstract Graph<T> createGraphHook(List<T> edges, EdgeMapper<T> edgeMapper);
}
