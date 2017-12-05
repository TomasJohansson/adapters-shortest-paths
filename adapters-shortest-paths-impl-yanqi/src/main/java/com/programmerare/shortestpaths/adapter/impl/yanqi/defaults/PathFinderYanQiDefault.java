package com.programmerare.shortestpaths.adapter.impl.yanqi.defaults;

import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderYanQi;
import com.programmerare.shortestpaths.core.api.EdgeDefault;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.PathDefault;
import com.programmerare.shortestpaths.core.api.PathFinderDefault;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

public class PathFinderYanQiDefault 
	extends PathFinderYanQi<
		PathDefault,
		EdgeDefault, // Edge<Vertex, Weight> 
		Vertex , 
		Weight
	>
	implements PathFinderDefault
{
	protected PathFinderYanQiDefault(
		final Graph<EdgeDefault, Vertex, Weight> graph,
		final GraphEdgesValidationDesired graphEdgesValidationDesired
	) {
		super(graph, graphEdgesValidationDesired);
	}
}