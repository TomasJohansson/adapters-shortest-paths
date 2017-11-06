package com.programmerare.shortestpaths.adapter.impl.jgrapht;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * The purpose of this subclass is to enable access to the protected methods in the base classes DefaultWeightedEdge and DefaultEdge  
 * @author Tomas Johansson
 */
public final class WeightedEdge extends DefaultWeightedEdge {

	public String getSourceIdAsStringValue() {
		return super.getSource().toString();
	}
	
	public String getTargetIdAsStringValue() {
		return super.getTarget().toString();
	}	

	public double getWeightValue() {
		return super.getWeight();
	}
}