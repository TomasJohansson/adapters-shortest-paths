package com.programmerare.shortestpaths.examples.roadrouting.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.programmerare.shortestpaths.examples.roadrouting.City;
import com.programmerare.shortestpaths.examples.roadrouting.CityRoadService;
import com.programmerare.shortestpaths.examples.roadrouting.Road;

/**
 * @author Tomas Johansson
 */
public final class CityRoadServiceDatabase implements CityRoadService {

	private List<City> allCities;
	private List<Road> allRoads;
	private City startCity;
	private City endCity;	
	/**
	 * persistence.xml
	 * 	<persistence-unit name="jpaExampleWithRoadAndCity" transaction-type="RESOURCE_LOCAL">
	 */
	private final static String NAME_OF_PERSISTANCE_UNIT_IN_PERSISTENCE_XML_FILE = "jpaExampleWithRoadAndCity";
	
	private final CityDataMapper cityDataMapper;
	private final RoadDataMapper roadDataMapper;
	private final CityRoadService cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty;
	

	public CityRoadServiceDatabase(final CityRoadService cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty) {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory(NAME_OF_PERSISTANCE_UNIT_IN_PERSISTENCE_XML_FILE);
		final EntityManager entityManager = emf.createEntityManager();
		cityDataMapper = new CityDataMapper(entityManager);
		roadDataMapper = new RoadDataMapper(entityManager);
		
		this.cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty = cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty;
	}

	public City getStartCity() {
		setFieldsIfNeeded();
		return startCity;
	}
	public City getEndCity() {
		setFieldsIfNeeded();
		return endCity;
	}

	public List<City> getAllCities() {
		setFieldsIfNeeded();
		return allCities;
	}
	
	public List<Road> getAllRoads() {
		setFieldsIfNeeded();
		return allRoads;
	}

	private void setFieldsIfNeeded() {
		if(!isAllFieldsLoadedFromDatabase()) { // at first invocation for one of the four getters 
			
			populateDatabaseWithHardcodedCityAndRoads();
			
			throwExceptionIfCouldNotBeRetrievedFromDatabase();
		}
	}

	private boolean isAllFieldsLoadedFromDatabase = false;  // lazy loading field
	
	// TODO: refactor this method to instead using Command-Query-Separation
	private boolean isAllFieldsLoadedFromDatabase() {
		if(isAllFieldsLoadedFromDatabase) return true; // lazy loading field
		
		// try to query all needed fields from the database
		allCities = cityDataMapper.getAll();
		allRoads = roadDataMapper.getAll();
		startCity = cityDataMapper.getByCityName(NAME_OF_START_CITY);
		endCity = cityDataMapper.getByCityName(NAME_OF_END_CITY);

		// if everything above worked then that is good,
		// but if nothing works, it is also a reasonable expected/normal scenario
		// when not yet populated, but if some failed and some succeeded then we have an unknown problem
		// so then throw an exception
		int successCount = 0;
		if(allCities != null && allCities.size() > 0) successCount++;
		if(allRoads != null && allRoads.size() > 0) successCount++;
		if(startCity != null ) successCount++;
		if(endCity != null ) successCount++;

		final int numberOfQueries = 4;
		
		if(successCount == numberOfQueries) {
			return true;
		}		
		else if(successCount == 0) {
			isAllFieldsLoadedFromDatabase = true; 
			return false;
		}
		else {
			throw new RuntimeException("Only " + successCount + " of " + numberOfQueries + " database queries succeeded");
		}
	}


	private void populateDatabaseWithHardcodedCityAndRoads() {
		final List<City> citiesNotYetPersisted = this.cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty.getAllCities();
		final List<Road> roadsNotYetPersisted = this.cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty.getAllRoads();
		
		persistCitiesToDatabase(citiesNotYetPersisted);
		persistRoadsToDatabase(roadsNotYetPersisted);
	}

	private void persistCitiesToDatabase(final List<City> cities) {
		cityDataMapper.save(cities);
	}
	private void persistRoadsToDatabase(final List<Road> roads) {
		roadDataMapper.save(roads);
	}	


	private void throwExceptionIfCouldNotBeRetrievedFromDatabase(
		final String messageSuffixForException, 
		final boolean shouldNowThrowException
	) {
		if(shouldNowThrowException) {
			throw new RuntimeException("Problem with loading data from database: " + messageSuffixForException);
		}
	}
	private void throwExceptionIfCouldNotBeRetrievedFromDatabase() {
		throwExceptionIfCouldNotBeRetrievedFromDatabase("all Cities", 	allCities == null || allCities.size() == 0);
		throwExceptionIfCouldNotBeRetrievedFromDatabase("all Roads", 	allRoads == null || allRoads.size() == 0);
		throwExceptionIfCouldNotBeRetrievedFromDatabase("start city with name " + NAME_OF_START_CITY, 	startCity == null);
		throwExceptionIfCouldNotBeRetrievedFromDatabase("end city with name " + NAME_OF_END_CITY, 		endCity == null);
	}
}