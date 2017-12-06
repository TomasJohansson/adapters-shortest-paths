package com.programmerare.shortestpaths.core.api;

import java.util.List;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

/**
 * Used for creating an instance of PathFinder.
 * The instantiated PathFinder will be an Adapter implementation of the PathFinder, i.e. will use a third-part library for finding the shortest path. 
 * 		
 * 		TODO: Maybe implement something like this for instantiating: XPathFactory xPathfactory = XPathFactory.newInstance();
 * 				Maybe it should use different strategies for choosing instance.
 * 				If multiple implementations are available, then determine in some preferred order, and maybe be able to define it 
 * 				in a config file or system property... 
 * 
 * @author Tomas Johansson
  */
public interface PathFinderFactory<F extends PathFinder<P,E,V,W> , P extends Path<E, V, W> ,  E extends Edge<V, W> , V extends Vertex , W extends Weight> {  

	/**
	 * @param graph the Graph which the implementation should forward to the PathFinder implementations, which must keep a reference to it,
	 * 	since the PathFinder find method will not receive the Graph as parameter.   
	 * @param graphEdgesValidationDesired a parameter to specify whether validation of the Graph should be done or not.
	 * 	It is an enum which is used instead of a boolean, since using a boolean provides no semantic in a method invocation.
	 * 	Client code should choose to do validation, but of course not needed if multiple instances are created
	 * (e.g. for more then one Adapter implementations).
	 * In such situations, either perform the Graph validation externally, or only do it for the first instance,
	 * since it would be unnecessary to validate the Graph multiple times.      
	 * It is convenient to choose validation, but if the same Graph is used for creating many PathFinder instances, then it would be better 
	 * for performance reasons to only use it for the first created instance (or only use external validation) and then not for the others.      
	 * @return an Adapter implementation of the PathFinder, which will use some "Adaptee" (third-part library) for finding shortest paths.
	 */	
	F createPathFinder(
		Graph<E, V, W> graph, 
		GraphEdgesValidationDesired graphEdgesValidationDesired
	);

	/**
	 * Convenience method with a list of Edge instances parameter instead of a Graph parameter.
	 * 	It should be implemented as creating a Graph with the list of edges, and then invoke the overloaded method with Graph as parameter.   
 	 * @param edges list of Edge instances to be used for creating a Graph
	 * @param graphEdgesValidationDesired see the documentation for the overloaded method
	 * @return see the documentation for the overloaded method
	 */	
	F createPathFinder(
		List<E> edges, 
		GraphEdgesValidationDesired graphEdgesValidationDesired
	);
}