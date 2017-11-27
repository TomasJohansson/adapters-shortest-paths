package com.programmerare.shortestpaths.core.validation;

/**
 * @see GraphEdgesValidator
 *    
 * @author Tomas Johansson
*/
public class GraphValidationException extends RuntimeException {
	public GraphValidationException(String message) {
		super(message);
	}
}
