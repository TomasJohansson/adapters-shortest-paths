package com.programmerare.shortestpaths.core.validation;

/**
 * Currently only two possbile values (but this might of course become extended with mroe fine-grained options about what to validate).
 * The reason for using this enum (instead of boolean) is to provide semantic when reading code with the invocation 
 * instead of just sending some value of "true" or "false"   
 * @author Tomas Johansson
 */
public enum GraphEdgesValidationDesired {
	NO , YES ;
}
