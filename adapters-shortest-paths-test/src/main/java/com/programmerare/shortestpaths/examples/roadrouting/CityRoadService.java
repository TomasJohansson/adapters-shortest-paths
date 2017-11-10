package com.programmerare.shortestpaths.examples.roadrouting;

import java.util.List;

public interface CityRoadService {
	
	String  NAME_OF_START_CITY 	= "A";
	String  NAME_OF_END_CITY 	= "F";
	
	List<City> getAllCities();
	List<Road> getAllRoads();
	City getStartCity();
	City getEndCity();
}
