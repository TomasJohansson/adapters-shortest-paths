package adapters.shortest.paths.impl.jython_networkx;

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    @Test
    void findShortestPaths() {
        final PathFinderFactory pathFinderFactory = new PathFinderFactory();
        final PathFinder pathFinder = pathFinderFactory.create();
        final List<List<String>> shortestPaths = pathFinder.findShortestPaths("a", "d", 5);

        assertEquals(2, shortestPaths.size());

        final List<String> shortestPath1 = shortestPaths.get(0);
        final List<String> shortestPath2 = shortestPaths.get(1);
        
        assertEquals("a", shortestPath1.get(0));
        assertEquals("b", shortestPath1.get(1));
        assertEquals("d", shortestPath1.get(2));

        assertEquals("a", shortestPath2.get(0));
        assertEquals("c", shortestPath2.get(1));
        assertEquals("d", shortestPath2.get(2));        
    }

//    @BeforeEach
//    void setUp() {
//    }
//    @AfterEach
//    void tearDown() {
//    }
}
