package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.programmerare.shortestpaths.core.api.Edge;

public final class EdgeUtility {

	private final static Map<SelectionStrategyWhenEdgesAreDuplicated, SelectionStrategy> tableLookupMapForSelectionStrategies = new HashMap<SelectionStrategyWhenEdgesAreDuplicated, SelectionStrategy>();
	static {
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.FIRST_IN_LIST_OF_EDGES, new SelectionStrategyFirst());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.LAST_IN_LIST_OF_EDGES, new SelectionStrategyLast());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.SMALLEST_WEIGHT, new SelectionStrategySmallestWeight());
		tableLookupMapForSelectionStrategies.put(SelectionStrategyWhenEdgesAreDuplicated.LARGEST_WEIGHT, new SelectionStrategyLargestWeight());
	}
	
	public final static List<Edge> getEdgesWithoutDuplicates(
		final List<Edge> edges, 
		final SelectionStrategyWhenEdgesAreDuplicated selectionStrategyWhenEdgesAreDuplicated
	) {
		final Map<String, List<Edge>> map = getMap(edges);
		final List<Edge> reduced = getReduced(edges, map, tableLookupMapForSelectionStrategies.get(selectionStrategyWhenEdgesAreDuplicated));
		return reduced;
	}
	
	private static List<Edge> getReduced(
		final List<Edge> edges, 
		final Map<String, List<Edge>> map,
		final SelectionStrategy selectionStrategy
	) {
		final List<Edge> edgesToReturn = new ArrayList<Edge>(); 	
		for (final Edge edge : edges) {
			final String key = edge.getEdgeId();
			if(map.containsKey(key)) {
				final List<Edge> list = map.get(key);
				final Edge reduce = selectionStrategy.reduce(list);
				edgesToReturn.add(reduce);
				map.remove(key);
			}
		}
		return edgesToReturn;
	}

	private static Map<String, List<Edge>> getMap(final List<Edge> edges) {
		final Map<String, List<Edge>> map = new HashMap<String, List<Edge>>();
		for (final Edge edge : edges) {
			final List<Edge> list;
			final String key = edge.getEdgeId();
			if(map.containsKey(key)) {
				list = map.get(key);	
			}
			else {
				list = new ArrayList<Edge>();
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
		Edge reduce(List<Edge> edges);
	}
	public static class SelectionStrategyFirst implements SelectionStrategy {
		public Edge reduce(final List<Edge> edges) {
			return edges.get(0);
		}
	}
	public static class SelectionStrategyLast implements SelectionStrategy {
		public Edge reduce(final List<Edge> edges) {
			return edges.get(edges.size()-1);
		}
	}
	
	public static class SelectionStrategySmallestWeight implements SelectionStrategy {
		public Edge reduce(final List<Edge> edges) {
			double weightMin = Double.MAX_VALUE;
			Edge edgeToReturn = null;
			for (Edge edge : edges) {
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
		public Edge reduce(final List<Edge> edges) {
			double weightMax = Double.MIN_VALUE;
			Edge edgeToReturn = null;
			for (Edge edge : edges) {
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