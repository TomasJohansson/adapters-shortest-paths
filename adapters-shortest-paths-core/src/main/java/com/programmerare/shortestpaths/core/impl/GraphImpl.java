package com.programmerare.shortestpaths.core.impl;

import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;

public class GraphImpl<T extends Edge> implements Graph<T> {

	private final List<T> edges;

	private GraphImpl(final List<T> edges) {
		this.edges = Collections.unmodifiableList(edges);
	}

	public static <T extends Edge> Graph<T> createGraph(
		final List<T> edges
	) {
		final GraphImpl<T> g = new GraphImpl<T>(
			edges				
		);
		return g;
	}
	
	public List getAllEdges() {
		return edges;
	}
}