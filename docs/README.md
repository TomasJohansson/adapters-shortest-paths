## Adapters for Java implementations of Graph algorithms for routing the shortest paths.

The purpose of this project is to provide Adapters for Java implementations of Graph algorithms for routing the shortest path**s**.<br>(the bold '**s**' above is intentional i.e. **not only** the single shortest path but the shortest path**s**)

### Example of how to use this library:

Consider a graph with four vertices (A,B,C,D) and five edges with weights.<br>(A to B (5) , A to C (6) , B to C (7)  , B to D (8) , C to D (9) ).<br>![alt text](images/shortest_paths_getting_started_example.gif "Logo Title Text 1")<br>
There are three possible paths from A to C , with the total weight within parenthesis : 
* A to B to D (total cost: 13 = 5 + 8)
* A to C to D (total cost: 15 = 6 + 9)
* A to B to C to D (total cost: 21 = 5 + 7 + 9)

For example, the vertices might represent cities, and the edges might represent roads with distances as weights.

The Java code below can be used for finding the shortest paths (sorted with the shortest first) from A to D :

```java
import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import java.util.Arrays;
import java.util.List;
import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.GraphFactory;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.Weight;
import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;

...

Vertex a = createVertex("A");
Vertex b = createVertex("B");
Vertex c = createVertex("C");
Vertex d = createVertex("D");

List<Edge> edges = Arrays.asList(
	createEdge(a, b, createWeight(5)),
	createEdge(a, c, createWeight(6)),
	createEdge(b, c, createWeight(7)),
	createEdge(b, d, createWeight(8)),
	createEdge(c, d, createWeight(9))
);

GraphFactory<Edge> graphFactory = new GraphFactoryJgrapht<Edge>());
// or: graphFactory = new GraphFactoryBsmock<Edge>());
// or: graphFactory = new GraphFactoryYanQi<Edge>());
// (currently there are three implementations)

Graph<Edge> graph = graphFactory.createGraph(edges);
List<Path<Edge>> shortestPaths = graph.findShortestPaths(a, d, 10); // last parameter is max number to return but in this case there are only 3 possible paths
for (Path<Edge> path : shortestPaths) {
	Weight totalWeightForPath = path.getTotalWeightForPath();
	System.out.println(totalWeightForPath);
	List<Edge> edges = path.getEdgesForPath();
	for (Edge edge : edges) {
		Vertex startVertex = edge.getStartVertex();
		Vertex endVertex = edge.getEndVertex();
		Weight edgeWeight = edge.getEdgeWeight();					
		System.out.println(startVertex);
		System.out.println(endVertex);
		System.out.println(edgeWeight);
	}			
}
```
Assuming you are using Maven, to be able to use the above code, you can use the following configuration in your "pom.xml" file :
```xml
<repositories>
	...
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
	
<dependencies>
	...
	<dependency>
		<groupId>com.github.TomasJohansson</groupId>
		<artifactId>adapters-shortest-paths</artifactId>
		<version>5308d2aec13d6147c864703e1e2284a970e8c42e</version> <!--https://github.com/TomasJohansson/adapters-shortest-paths/commits/master  -->
	</dependency>      
</dependencies>
```
Note that "jitpack" is used since currently there is no release of this library in the maven central repository.
