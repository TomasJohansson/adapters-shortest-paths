package com.programmerare.shortestpaths.examples.roadrouting;

import java.util.List;

import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;

/**
 * @author Tomas Johansson
 */
public class RoadRoutingMain {
	
	public static void main(String[] args) {
		final CityRoadService cityRoadService = new CityRoadService();
		final List<Road> roads = cityRoadService.getRoads();
		
		performRoadRouting(roads, new GraphFactoryYanQi<Road>());
		performRoadRouting(roads, new GraphFactoryBsmock<Road>());
		performRoadRouting(roads, new GraphFactoryJgrapht<Road>());
	}

	private static void performRoadRouting(final List<Road> roads, final GraphFactory<Road> graphFactory) {
		System.out.println("--------------------------------");
		System.out.println("Implementation starts for " + graphFactory.getClass().getSimpleName());
		final Road firstRoad = roads.get(0);
		final Road lastRoad = roads.get(roads.size()-1);
		
		// Note that the datatype below is List<Road>  wherre "Road" is an EXAMPLE 
		// of domain object you can create yourself.
		// Note that such an object must implement the interface "Edge" adn in particular must pay attention to 
		// how the method "getEdgeId()" must be implemented as documented in the Edge interface.
		
		final Graph<Road> graph = graphFactory.createGraph(roads); 
		
		final List<Path<Road>> paths = graph.findShortestPaths(firstRoad.getStartVertex(), lastRoad.getEndVertex(), 10);
		// Now also note that you can retrieve your own domain object (for example "Road" above) 
		// through the returned paths when iterating the path edges, i.e. instead of a list typed as "Edge"
		// you now have a list with "Road" and thus can use methods of that class, e.g. "getRoadName()" as below
		
		System.out.println("Paths size "+ paths.size());
		for (Path<Road> path : paths) {
			System.out.println(getPrettyPrintedPath(path));
		}
		System.out.println("--------------------------------");
	}
	
	private static String getPrettyPrintedPath(Path<Road> path) {
		final List<Road> roadsForPath = path.getEdgesForPath();
		StringBuilder sb = new StringBuilder();
		sb.append("Total weight: " + path.getTotalWeightForPath().getWeightValue() + " | ");
		for (int i = 0; i < roadsForPath.size(); i++) {
			Road road = roadsForPath.get(i);
			if(i > 0) {
				sb.append(" ---> ");
			}
			sb.append("[" + road.getRoadName() + "](" + road.getEdgeWeight().getWeightValue() + ")");
		}
		return sb.toString();
	}
}