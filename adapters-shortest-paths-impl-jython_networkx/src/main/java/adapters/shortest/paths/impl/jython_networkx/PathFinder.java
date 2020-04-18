package adapters.shortest.paths.impl.jython_networkx;

import java.util.List;

public interface PathFinder {
    List<List<String>> findShortestPaths(String startVertex, String endVertex, int maxNumberOfPaths);
}
