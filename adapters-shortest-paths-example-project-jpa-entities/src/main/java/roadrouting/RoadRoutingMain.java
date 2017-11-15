package roadrouting;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.api.Path;

/**
 * @author Tomas Johansson
 */
public class RoadRoutingMain {
	
	/**
	 * @param args "1" if you want to use the database, otherwise hardcoded values be used.
	 */
	public static void main(String[] args) {
		final boolean useDatabase = parseArguments(args);
		
		final CityRoadService cityRoadService = CityRoadServiceFactory.createCityRoadService(useDatabase);

		final List<Road> roads = cityRoadService.getAllRoads();

		final City startCity = cityRoadService.getStartCity();
		final City endCity = cityRoadService.getEndCity();

		final List<GraphFactory<Road>> graphFactories = new ArrayList<GraphFactory<Road>>();
		graphFactories.add(new GraphFactoryYanQi<Road>());
		graphFactories.add(new GraphFactoryBsmock<Road>()); 
		graphFactories.add(new GraphFactoryJgrapht<Road>());
		
		performRoadRoutingForTheImplementations(
			roads, 
			startCity, 
			endCity, 
			graphFactories
		);
	}
	
	private static void performRoadRoutingForTheImplementations(
		final List<Road> roads, 
		final City startCity, 
		final City endCity, 
		final List<GraphFactory<Road>> graphFactories
	) {
		for (GraphFactory<Road> graphFactory : graphFactories) {
			performRoadRouting(roads, startCity, endCity, graphFactory);	
		}
	}

	private static void performRoadRouting(
		final List<Road> roads, 
		final City startCity, 
		final City endCity, 
		final GraphFactory<Road> graphFactory
	) {
		System.out.println("--------------------------------");
		System.out.println("Implementation starts for " + graphFactory.getClass().getSimpleName());
		
		// Note that the datatype below is List<Road>  where "Road" is an EXAMPLE 
		// of domain object you can create yourself.
		// Note that such an object must implement the interface "Edge" and in particular must pay attention to 
		// how the method "getEdgeId()" must be implemented as documented in the Edge interface.
		final Graph<Road> graph = graphFactory.createGraph(roads); 
		
		final List<Path<Road>> paths = graph.findShortestPaths(startCity, endCity, 10);
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
	

	/**
	 * @param args
	 * @return true if args[0] == "1" otherwise false
	 */
	private static boolean parseArguments(String[] args) {
		if(args == null || args.length < 1) return false;
		return args[0].equals("1");
	}
}