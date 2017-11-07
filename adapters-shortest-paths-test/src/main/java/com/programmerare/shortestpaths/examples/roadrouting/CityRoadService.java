package com.programmerare.shortestpaths.examples.roadrouting;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Tomas Johansson
 */
public final class CityRoadService {

	private List<City> cities;
	private List<Road> roads;
	
	public CityRoadService() {
		populateFieldsWithCitiesAndRoads();
	}

	private void populateFieldsWithCitiesAndRoads() {
		cities = getCitiesFromDatabase();
		roads = getRoadsFromDatabase();
		if(cities == null || roads == null) {
			City cityA = createCity("A");
			City cityB = createCity("B");
			City cityC = createCity("C");
			City cityD = createCity("D");
			City cityE = createCity("E");
			City cityF = createCity("F");
			cities = Arrays.asList(cityA, cityB, cityC, cityD, cityE, cityF);
			

			Road roadAB = createRoad(cityA, cityB, 100, RoadQuality.GOOD, "A to B");
			Road roadAC = createRoad(cityA, cityC, 200, RoadQuality.BAD, "A to C");
			Road roadAD = createRoad(cityA, cityD, 300, RoadQuality.GOOD, "A to D");
			Road roadBC = createRoad(cityB, cityC, 150, RoadQuality.BAD, "B to C");
			Road roadBD = createRoad(cityB, cityD, 250, RoadQuality.GOOD, "B to D");
			Road roadBE = createRoad(cityB, cityE, 350, RoadQuality.BAD, "B to E");
			Road roadCD = createRoad(cityC, cityD, 220, RoadQuality.GOOD, "C to D");
			Road roadCE = createRoad(cityC, cityE, 340, RoadQuality.BAD, "C to E");
			Road roadDE = createRoad(cityD, cityE, 130, RoadQuality.GOOD, "D to E");
			Road roadDF = createRoad(cityD, cityF, 140, RoadQuality.GOOD, "D to F");			
			Road roadEF = createRoad(cityE, cityF, 150, RoadQuality.GOOD, "E to F");
			roads = Arrays.asList(roadAB, roadAC, roadAD, roadBC, roadBD, roadBE, roadCD, roadCE, roadDE, roadDF, roadEF);
			
			
			persistToDatabase(cities, roads);
		}
	}

	private City createCity(String cityName) {
		return new City(UUID.randomUUID(), cityName);
	}

	private Road createRoad(final City cityFrom, final City cityTo, final int roadLength, final RoadQuality roadQuality, String roadName) {
		return new Road(cityFrom, cityTo, roadLength, roadQuality, roadName);
	}

	private List<City> getCities() {
		return cities;
	}

	public List<Road> getRoads() {
		return roads;
	}

	
	
	private List<Road> getRoadsFromDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<City> getCitiesFromDatabase() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void persistToDatabase(List<City> cities2, List<Road> roads2) {
		// TODO Auto-generated method stub
	}
}