package com.programmerare.shortestpaths.core.impl;

import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.impl.generics.GraphGenericsImpl;

public final class GraphImpl extends GraphGenericsImpl<Edge, Vertex , Weight> implements Graph {

	protected GraphImpl(final List<Edge> edges) {
		super(edges);
	}

	public static Graph createGraph(final List<Edge> edges) {
		return new GraphImpl(edges);
	}
}