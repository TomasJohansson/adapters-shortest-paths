package com.programmerare.shortestpaths.adapter;

import java.util.List;

/**
 * "Target" interface for Adapter implementations 
 * @author Tomas Johansson
 * @see https://en.wikipedia.org/wiki/Adapter_pattern
  */
public interface GraphFactory {
	Graph createGraph(List<Edge> edges);
}
