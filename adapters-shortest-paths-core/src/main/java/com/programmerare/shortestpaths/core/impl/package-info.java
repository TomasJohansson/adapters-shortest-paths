/**
 * Generally reusable implementations for some of the interfaces in the core api.
 * Note that there are no such general implementations for Graph and GraphFactory,
 * but those should instead be implemented in implementing Adapter libraries.
 * (however, there is a base class for GraphFactory, but most of the implementation need to provided in another library)
 * There is also an EdgeMapper class in this package, which does not quite belong here
 * (according to the semantic of the package name, but want it here because of package level access from GraphFactoryBase) 
 */
/**
 * @author Tomas Johansson
 *
 */
package com.programmerare.shortestpaths.core.impl;