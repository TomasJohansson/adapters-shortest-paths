package adapters.shortest.paths.impl.jython_networkx;

import java.util.List;

/**
 * This Java interface is implemented by the Jython class 
 * in the Python source code file "src\main\resources\pathfinder.py"
 */
public interface PathFinder {
    List<List<String>> findShortestPaths(String startVertex, String endVertex, int maxNumberOfPaths);
    void addGraphEdge(String source, String target, double weight);
}
