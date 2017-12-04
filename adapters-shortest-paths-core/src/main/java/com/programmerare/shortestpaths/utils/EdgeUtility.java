package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;

public final class EdgeUtility {

	private final static Map<SelectionStrategyWhenEdgesAreDuplicated, SelectionStrategy> tableLookupMapForSelectionStrategies = new HashMap<SelectionStrategyWhenEdgesAreDuplicated, SelectionStrategy>();
	static {
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.FIRST_IN_LIST_OF_EDGES, new SelectionStrategyFirst());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.LAST_IN_LIST_OF_EDGES, new SelectionStrategyLast());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.SMALLEST_WEIGHT, new SelectionStrategySmallestWeight());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.LARGEST_WEIGHT, new SelectionStrategyLargestWeight());
	}
	
	public final static List<Edge<Vertex, Weight>> getEdgesWithoutDuplicates(
		final List<Edge<Vertex, Weight>> edges, 
		final SelectionStrategyWhenEdgesAreDuplicated selectionStrategyWhenEdgesAreDuplicated
	) {
		final Map<String, List<Edge<Vertex, Weight>>> map = getMap(edges);
		final List<Edge<Vertex, Weight>> reduced = getReduced(edges, map, tableLookupMapForSelectionStrategies.get(selectionStrategyWhenEdgesAreDuplicated));
		return reduced;
	}
	
	private static List<Edge<Vertex, Weight>> getReduced(
		final List<Edge<Vertex, Weight>> edges, 
		final Map<String, List<Edge<Vertex, Weight>>> map,
		final SelectionStrategy selectionStrategy
	) {
		final List<Edge<Vertex, Weight>> edgesToReturn = new ArrayList<Edge<Vertex, Weight>>(); 	
		for (final Edge<Vertex, Weight> edge : edges) {
			final String key = edge.getEdgeId();
			if(map.containsKey(key)) {
				final List<Edge<Vertex, Weight>> list = map.get(key);
				final Edge<Vertex, Weight> reduce = selectionStrategy.reduce(list);
				edgesToReturn.add(reduce);
				map.remove(key);
			}
		}
		return edgesToReturn;
	}

	private static Map<String, List<Edge<Vertex, Weight>>> getMap(final List<Edge<Vertex, Weight>> edges) {
		final Map<String, List<Edge<Vertex, Weight>>> map = new HashMap<String, List<Edge<Vertex, Weight>>>();
		for (final Edge<Vertex, Weight> edge : edges) {
			final List<Edge<Vertex, Weight>> list;
			final String key = edge.getEdgeId();
			if(map.containsKey(key)) {
				list = map.get(key);	
			}
			else {
				list = new ArrayList<Edge<Vertex, Weight>>();
				map.put(key, list);
			}
			list.add(edge);
		}		
		return map;
	}

	public static enum SelectionStrategyWhenEdgesAreDuplicated {
		FIRST_IN_LIST_OF_EDGES, 
		LAST_IN_LIST_OF_EDGES,
		SMALLEST_WEIGHT,
		LARGEST_WEIGHT;
	}
	
	public static interface SelectionStrategy {
		Edge<Vertex, Weight> reduce(List<Edge<Vertex, Weight>> edges);
	}
	public static class SelectionStrategyFirst implements SelectionStrategy {
		public Edge<Vertex, Weight> reduce(final List<Edge<Vertex, Weight>> edges) {
			return edges.get(0);
		}
	}
	public static class SelectionStrategyLast implements SelectionStrategy {
		public Edge<Vertex, Weight> reduce(final List<Edge<Vertex, Weight>> edges) {
			return edges.get(edges.size()-1);
		}
	}
	
	public static class SelectionStrategySmallestWeight implements SelectionStrategy {
		public Edge<Vertex, Weight> reduce(final List<Edge<Vertex, Weight>> edges) {
			double weightMin = Double.MAX_VALUE;
			Edge<Vertex, Weight> edgeToReturn = null;
			for (Edge<Vertex, Weight> edge : edges) {
				double w = edge.getEdgeWeight().getWeightValue();
				if(w < weightMin) {
					weightMin = w;
					edgeToReturn = edge;
				}
			}
			return edgeToReturn;
		}
	}
	// TODO: refactor above and below class to reduce duplication
	public static class SelectionStrategyLargestWeight implements SelectionStrategy {
		public Edge<Vertex, Weight> reduce(final List<Edge<Vertex, Weight>> edges) {
			double weightMax = Double.MIN_VALUE;
			Edge<Vertex, Weight> edgeToReturn = null;
			for (Edge<Vertex, Weight> edge : edges) {
				double w = edge.getEdgeWeight().getWeightValue();
				if(w > weightMax) {
					weightMax = w;
					edgeToReturn = edge;
				}
			}
			return edgeToReturn;
		}
	}	
} 