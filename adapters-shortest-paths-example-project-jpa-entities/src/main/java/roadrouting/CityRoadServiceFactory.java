package roadrouting;

import roadrouting.database.CityRoadServiceDatabase;

public class CityRoadServiceFactory {

	public static CityRoadService createCityRoadService(final CityRoadServiceType cityRoadServiceType) {
		final CityRoadService cityRoadService = new CityRoadServiceHardcoded();
		
		if(cityRoadServiceType == CityRoadServiceType.NoDatabase) {
			return cityRoadService;
		}
		else {
			// the hardcoded values sent as parameter will be used for initial populatiion of the database
			return new CityRoadServiceDatabase(cityRoadService, cityRoadServiceType);	
		}
	}
}
