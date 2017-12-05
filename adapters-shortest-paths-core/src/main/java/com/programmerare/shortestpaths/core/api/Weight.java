package com.programmerare.shortestpaths.core.api;

/**
 * @author Tomas Johansson
 */
public interface Weight extends StringRenderable {
	
	double getWeightValue();

	/**
	 * Factory method not intended to be used by client code, but should of course be implemented if you create your own
	 * implementation instead of the default implementation.
	 * The purpose is that it will be used by PathFinderBase, for creating the total weight with the same instance as the weights in the edges. 
	 * Instead of forcing client code to pass a Weight factory class trough a factory creating the PathFinder,
	 * this factory has been placed here, and then any weight (e.g. the first found) can be used by assuming all edges have the same weight implementation.
	 * When the create method is used, it can be considered as a variant of the design pattern Prototype, though not cloning itself with the same value but the value is instead a parameter for the method.  
	 * @param value
	 * @return
	 */
	Weight create(double value);
}