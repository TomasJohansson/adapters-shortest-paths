package roadrouting;

import org.junit.Before;
import org.junit.Test;

/**
 * This is not a real test class which is doing any assertions, but it is a convenient 
 * way of running the code to write "mvn test" instead of writing (finding and pasting) 
 * the following command:
 * mvn exec:java -Dexec.mainClass="roadrouting.RoadRoutingMain" -Dexec.args="1"
 * 
 * @author Tomas Johansson
 */
public class RoadRoutingMainTest {

	private RoadRoutingMain roadRoutingMain;
	
	@Before
	public void setUp() throws Exception {
		roadRoutingMain = new RoadRoutingMain();
	}

	@Test
	public void testMainWithSqliteDatabaseFile() {
		roadRoutingMain.main(CityRoadServiceType.DatabaseSqliteFile);
	}

	@Test
	public void testMainWithSqliteDatabaseInMemory() {
		roadRoutingMain.main(CityRoadServiceType.DatabaseSqliteInMemoryWithoutFile);
	}
	
	@Test
	public void testMainWithoutDatabase() {
		roadRoutingMain.main(CityRoadServiceType.NoDatabase);
	}	

}
