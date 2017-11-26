package roadrouting;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.impl.bsmock.PathFinderFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.PathFinderFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;

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

		final List<PathFinderFactory<Road>> pathFinderFactories = new ArrayList<PathFinderFactory<Road>>();
		pathFinderFactories.add(new PathFinderFactoryYanQi<Road>());
		pathFinderFactories.add(new PathFinderFactoryBsmock<Road>()); 
		pathFinderFactories.add(new PathFinderFactoryJgrapht<Road>());
		
		performRoadRoutingForTheImplementations(
			roads, 
			startCity, 
			endCity, 
			pathFinderFactories
		);
	}
	
	private static void performRoadRoutingForTheImplementations(
		final List<Road> roads, 
		final City startCity, 
		final City endCity, 
		final List<PathFinderFactory<Road>> pathFinderFactories
	) {
		// the parameter GraphEdgesValidationDesired.NO will be used so therefore do the validation once externally here first
		GraphEdgesValidator.validateEdgesForGraphCreation(roads);
		
		for (PathFinderFactory<Road> pathFinderFactory : pathFinderFactories) {
			performRoadRouting(roads, startCity, endCity, pathFinderFactory);	
		}
	}

	private static void performRoadRouting(
		final List<Road> roads, 
		final City startCity, 
		final City endCity, 
		final PathFinderFactory<Road> pathFinderFactory
	) {
		System.out.println("--------------------------------");
		System.out.println("Implementation starts for " + pathFinderFactory.getClass().getSimpleName());
		
		// Note that the datatype below is List<Road>  where "Road" is an EXAMPLE 
		// of domain object you can create yourself.
		// Note that such an object must implement the interface "Edge" and in particular must pay attention to 
		// how the method "getEdgeId()" must be implemented as documented in the Edge interface.
		final PathFinder<Road> pathFinder = pathFinderFactory.createPathFinder(
			roads, 
			GraphEdgesValidationDesired.NO // do the validation one time instead of doing it for each pathFinderFactory
		); 
		
		final List<Path<Road>> paths = pathFinder.findShortestPaths(startCity, endCity, 10);
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