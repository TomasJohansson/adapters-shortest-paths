package shortest_paths_getting_started_example;

import org.junit.jupiter.api.Test;

/**
 * This is not a real test class which is doing any assertions, but it is a convenient 
 * way of running the code to write "mvn test" instead of writing (finding and pasting) 
 * the following command:
 * mvn exec:java -Dexec.mainClass="shortest_paths_getting_started_example.ExampleMain"
 * 
 * @author Tomas Johansson
 */
public class ExampleMainTest {

	@Test
	public void testMain() {
		ExampleMain exampleMain = new ExampleMain();
		exampleMain.main(null);
	}
}