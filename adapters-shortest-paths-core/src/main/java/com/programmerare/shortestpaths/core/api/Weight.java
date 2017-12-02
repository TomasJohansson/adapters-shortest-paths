package com.programmerare.shortestpaths.core.api;

/**
 * A Weight represents some kind of cost for an Edge, i.e. a cost for going from the start Vertex to the end Vertex of the Edge.
 * When trying to find the shortest paths between two vertices in a Graph, it is the sum of the weights that are minimized.
 * In real world scenarios, the cost can be for example distance or time (in some unit) for going between two places (vertices).
 * 
 * @author Tomas Johansson
 */
public interface Weight extends StringRenderable {

	/**
	 * @return the actual numerical value for the weight.
	 * TODO maybe: Motivate why not a double is simuply used wverywhere instead of defining a trivial interface ... ( maybe mention DDD and the book Prefactoring ...) 
	 */
	double getWeightValue();
	
}