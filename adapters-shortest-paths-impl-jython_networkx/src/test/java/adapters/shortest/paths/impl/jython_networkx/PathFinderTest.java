package adapters.shortest.paths.impl.jython_networkx;

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    
    @Test
    void findShortestPaths() {
        final TimeMeasurer timeMeasurer = TimeMeasurer.start();
        final PathFinderFactory pathFinderFactory = new PathFinderFactory(timeMeasurer);
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After PathFinderFactory constructor");
        // the above (i.e. PathFinderFactory constructor) takes almost a minute
        
        PathFinder pathFinder = pathFinderFactory.createInstance();
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After first pathFinderFactory.createInstance()");
        pathFinder = pathFinderFactory.createInstance();
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After second pathFinderFactory.createInstance()");
        pathFinder = pathFinderFactory.createInstance();
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After third pathFinderFactory.createInstance()");

        pathFinder.addGraphEdge("A", "B", 5.0);
        pathFinder.addGraphEdge("A", "C", 6.0);
        pathFinder.addGraphEdge("B", "C", 7.0);
        pathFinder.addGraphEdge("B", "D", 8.0);
        pathFinder.addGraphEdge("C", "D", 9.0);
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After some pathFinder.addGraphEdge invocations");
        
        List<List<String>> shortestPaths = pathFinder.findShortestPaths("A", "D", 5);
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After first pathFinder.findShortestPaths");
        shortestPaths = pathFinder.findShortestPaths("A", "D", 5);
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After second pathFinder.findShortestPaths");
        shortestPaths = pathFinder.findShortestPaths("A", "D", 5);
        timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("After third pathFinder.findShortestPaths");

        assertEquals(3, shortestPaths.size());

        final List<String> shortestPath1 = shortestPaths.get(0);
        final List<String> shortestPath2 = shortestPaths.get(1);
        final List<String> shortestPath3 = shortestPaths.get(2);
        
        assertEquals("A", shortestPath1.get(0));
        assertEquals("B", shortestPath1.get(1));
        assertEquals("D", shortestPath1.get(2));

        assertEquals("A", shortestPath2.get(0));
        assertEquals("C", shortestPath2.get(1));
        assertEquals("D", shortestPath2.get(2));

        assertEquals("A", shortestPath2.get(0));
        assertEquals("C", shortestPath2.get(1));
        assertEquals("D", shortestPath2.get(2));

        assertEquals("A", shortestPath3.get(0));
        assertEquals("B", shortestPath3.get(1));
        assertEquals("C", shortestPath3.get(2));
        assertEquals("D", shortestPath3.get(3));
    }

//    @BeforeEach
//    void setUp() {
//    }
//    @AfterEach
//    void tearDown() {
//    }
}
