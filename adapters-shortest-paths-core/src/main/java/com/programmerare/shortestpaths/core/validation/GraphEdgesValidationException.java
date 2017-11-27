package com.programmerare.shortestpaths.core.validation;

/**
 * @see GraphEdgesValidator
 *    
 * @author Tomas Johansson
*/
public class GraphEdgesValidationException extends RuntimeException {
	// TODO: rename this class (and maybe also the validation class): Remmove "Edges" from the class names and reuse it for vertices validation too. 
	public GraphEdgesValidationException(String message) {
		super(message);
	}
}
