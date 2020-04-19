package com.programmerare.shortestpaths.graph.utils;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.jython_networkx.PathFinderFactoryJythonNetworkx;
import com.programmerare.shortestpaths.adapter.mulavito.PathFinderFactoryMulavito;
import com.programmerare.shortestpaths.adapter.reneargento.PathFinderFactoryReneArgento;
import com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;

/**
 * @author Tomas Johansson
 */
public final class PathFinderFactories {
	
	/**
	 * @return a list of implementations that should be used for searching the best paths,
	 * and the implementation results will be verified with each other and the test will cause a failure 
	 * if there is any mismatch found in the results.
	 */	
	public static List<PathFinderFactory> createPathFinderFactories() {
		List<PathFinderFactory> list = new ArrayList<PathFinderFactory>();
		list.add(new PathFinderFactoryYanQi());
		list.add(new PathFinderFactoryBsmock());
		list.add(new PathFinderFactoryJgrapht());
		list.add(new PathFinderFactoryReneArgento());
		list.add(new PathFinderFactoryMulavito());
		
		try {
			list.add(new PathFinderFactoryJythonNetworkx());
		}
		catch(Exception e) {
			// The above implementation within the try block is 
			// special and requires a Jython implementation
			// with the installed pip package networkx
			// and do not want all other tests to fail just because
			// this construction fails.
			// There are other tests (at least one, in test method SmallGraphTest#testFindShortestPaths_JythonNetworkx)
			// which explicitly uses the one below 
			// which will fail, but do not want every test using 
			// "createPathFinderFactories" to fail, so therefore an empty
			// catch block here to let all other factories be returned and tested.
		}
		
		return list;		
	}
}
