/*
* Copyright (c) Tomas Johansson , http://www.programmerare.com
* The code is made available under the terms of the MIT License.
* https://github.com/TomasJohansson/adapters-shortest-paths/blob/master/adapters-shortest-paths-core/License.txt
*/
package com.programmerare.shortestpaths.core.impl.generics;

import static com.programmerare.shortestpaths.core.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;

import java.util.Collections;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.EdgeGenerics;
import com.programmerare.shortestpaths.core.api.generics.PathGenerics;

/**
 * @author Tomas Johansson
 */
//public class PathImpl<E extends Edge<V, W> , V extends Vertex , W extends Weight> implements Path<E, V, W> {
public class PathGenericsImpl<E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> implements PathGenerics<E, V, W> {

	private final W totalWeight;
	private final List<E> edges;

	//public static <T extends Edge> Path<T> createPath(final Weight totalWeight, final List<T> edges) {
	public static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> PathGenerics<E, V, W> createPathGenerics(
		final W totalWeight, final List<E> edges
	) {
		return createPathGenerics(totalWeight, edges, false, false);
	}

	/**
	 * 
	 * @param totalWeight
	 * @param edges
	 * @param shouldThrowExceptionIfTotalWeightIsMismatching
	 * @param shouldThrowExceptionIfAnyMismatchingVertex
	 * @return
	 */
	public static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> PathGenerics<E, V, W> createPathGenerics(
		final W totalWeight, 
		final List<E> edges, 
		final boolean shouldThrowExceptionIfTotalWeightIsMismatching, 
		final boolean shouldThrowExceptionIfAnyMismatchingVertex
	) {
		if(shouldThrowExceptionIfTotalWeightIsMismatching) {
			if(isTotalWeightNotCorrect(edges, totalWeight)) {
				throw new RuntimeException("Incorrect weight " + totalWeight + " not mathcing the sum of the edges " + edges);
			}			
		}
		if(shouldThrowExceptionIfAnyMismatchingVertex) {
			if(isAnyVertexMismatching(edges)) {
				throw new RuntimeException("Mismatching vertices detected " + edges);
			}
		}
		return new PathGenericsImpl<E, V, W>(totalWeight, edges);
	}	
	
	private static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> boolean isTotalWeightNotCorrect(List<E> edges, W totalWeight) {
		double tot = 0;
		for (E edge : edges) {
			tot += edge.getEdgeWeight().getWeightValue();
		}
		return Math.abs(totalWeight.getWeightValue() - tot) > SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
	}

	private static <E extends EdgeGenerics<V, W> , V extends Vertex , W extends Weight> boolean isAnyVertexMismatching(List<E> edges) {
		for (int i = 1; i < edges.size(); i++) {
			E edge = edges.get(i-1);
			E nextEdge = edges.get(i);
			if(!edge.getEndVertex().equals(nextEdge.getStartVertex())) {
				return true;
			}
		}
		return false;
	}

	protected PathGenericsImpl(
		final W totalWeight, 
		final List<E> edges
	) {
		this.totalWeight = totalWeight;
		this.edges = Collections.unmodifiableList(edges);
	}	
	
	/**
	 * {@inheritDoc}
	 */
	public W getTotalWeightForPath() {
		return totalWeight;
	}

	/**
	 * {@inheritDoc}
	 */	
	public List<E> getEdgesForPath() {
		return edges;
	}

	// The three methods below were generated by Eclipse
	
	@Override
	public String toString() {
		return "PathImpl [totalWeight=" + totalWeight + ", edges=" + edges + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + ((totalWeight == null) ? 0 : totalWeight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PathGenericsImpl))
			return false;
		PathGenericsImpl other = (PathGenericsImpl) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (totalWeight == null) {
			if (other.totalWeight != null)
				return false;
		} else if (!totalWeight.equals(other.totalWeight))
			return false;
		return true;
	}
	
}
