package roadrouting.database;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import roadrouting.City;
import roadrouting.CityRoadService;
import roadrouting.CityRoadServiceType;
import roadrouting.Road;

/**
 * @author Tomas Johansson
 */
public final class CityRoadServiceDatabase implements CityRoadService {
    
    /**
     * Normally, the name of a constant like this (i.e. the name of the sqlite file in a JPA application)
	 * should be the same as a file name specified in the JPA persisting unit file 
	 * "src/main/resources/META-INF/persistence.xml"
     * which normally have a row like this:
     * <property name="javax.persistence.jdbc.url" value="jdbc:sqlite:roadrouting_example_database.sqlite" />
	 * However, in this example project the constant below is actually used for overriding the default url
	 * when the EntityManagerFactory is created.
	 * The default in the JPA file is to use an in-memory sqlite database like this:
	 * 
     */
    private final static String NameOfSqliteFile = "roadrouting_example_database.sqlite";
    
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
	
	private final EntityManagerFactory emf;
	private final EntityManager entityManager;

	public CityRoadServiceDatabase(
		final CityRoadService cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty,
		final CityRoadServiceType cityRoadServiceType
	) {
		final Map<String, String> persistenceProperties = createPersistenceProperties(cityRoadServiceType); 
		emf = Persistence.createEntityManagerFactory(NAME_OF_PERSISTANCE_UNIT_IN_PERSISTENCE_XML_FILE, persistenceProperties);
		entityManager = emf.createEntityManager();
		cityDataMapper = new CityDataMapper(entityManager);
		roadDataMapper = new RoadDataMapper(entityManager);
		this.cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty = cityRoadServiceProvidingDataForPopulatingDatabaseIfEmpty;
	}

	private Map<String, String> createPersistenceProperties(final CityRoadServiceType cityRoadServiceType)
	{
		final Map<String, String> persistenceProperties = new HashMap<String, String>();
		if(cityRoadServiceType == CityRoadServiceType.DatabaseSqliteFile) {
			this.deleteSqliteFileIfItAlreadyExists(); // kind of ugly to have this invocation here in 'createPersistenceProperties' but another similar if statement outside the method does not really feel great neither     
			final String connectionStringForSqliteFile = "jdbc:sqlite:" + NameOfSqliteFile;
			persistenceProperties.put("javax.persistence.jdbc.url", connectionStringForSqliteFile);
		}
		else if(cityRoadServiceType != CityRoadServiceType.DatabaseSqliteInMemoryWithoutFile) {
			throw new UnsupportedOperationException("cityRoadServiceType : " + cityRoadServiceType);
		}		
		return persistenceProperties;
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
		loadFieldsFromDatabase();

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
		
		loadFieldsFromDatabase();
	}

	private void loadFieldsFromDatabase() {
		allCities = cityDataMapper.getAll();
		allRoads = roadDataMapper.getAll();
		startCity = cityDataMapper.getByCityName(NAME_OF_START_CITY);
		endCity = cityDataMapper.getByCityName(NAME_OF_END_CITY);

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
	
	public void releaseResourcesIfAny() {
		closeEntityManagerAndEntityManagerFactoryIfStillOpen();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		closeEntityManagerAndEntityManagerFactoryIfStillOpen();
	}
	
	private void closeEntityManagerAndEntityManagerFactoryIfStillOpen() {
		if(entityManager.isOpen()) {
			entityManager.close();		
		}
		// it is important to close, because otherwise the program does not 
		// really finish even though the main method has finished running
		if(emf.isOpen()) {
			emf.close();
		}
	}

	private void deleteSqliteFileIfItAlreadyExists()
	{
		final String userDirectory = System.getProperty("user.dir");
		final File sqliteFile = new File(userDirectory, NameOfSqliteFile);
		if(sqliteFile.exists()) {
			System.out.println("File existed but will now become deleted: " + sqliteFile.getAbsolutePath());
			sqliteFile.delete();
			// now wait a second for the file to become deleted from the operating system file system 
            // to avoid potential problem with hibernate immediately trying to recreate it but then 
            // thinking it already exists 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
