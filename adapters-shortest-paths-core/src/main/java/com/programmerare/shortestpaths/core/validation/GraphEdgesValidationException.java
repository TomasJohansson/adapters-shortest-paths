package com.programmerare.shortestpaths.core.validation;

/**
 * @see GraphEdgesValidator
 *    
 * @author Tomas Johansson
*/
public class GraphEdgesValidationException extends RuntimeException {
	public GraphEdgesValidationException(String message) {
		super(message);
	}
}
