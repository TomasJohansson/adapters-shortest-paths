# License Notice
Notice that the "core" library with the API and general code is released with MIT License.
However, the libraries which are implementing adapters are licensed in the same way as the adapted libraries.
Currently there are four such adapter libraries, and if you intend to use one or more of them you must check their licenses:
* https://github.com/TomasJohansson/adapters-shortest-paths/tree/master/adapters-shortest-paths-impl-jgrapht
* https://github.com/TomasJohansson/adapters-shortest-paths/tree/master/adapters-shortest-paths-impl-bsmock
* https://github.com/TomasJohansson/adapters-shortest-paths/tree/master/adapters-shortest-paths-impl-yanqi
* https://github.com/TomasJohansson/adapters-shortest-paths/tree/master/adapters-shortest-paths-impl-reneargento


## Adapters for Java implementations of Graph algorithms for routing the shortest paths.

The purpose of this project is to provide Adapters for Java implementations of Graph algorithms for routing the shortest path**s**.<br>(the bold '**s**' above is intentional i.e. **not only** the single shortest path but the shortest path**s**)

**Currently there are four implemented Adapters**, i.e. four different implementations can be used.
Since the Client code is using the same Target interface (see the [Adapter Design Pattern](https://en.wikipedia.org/wiki/Adapter_pattern)) it is possible to **reuse the same test code for the different implementations**.
Therefore you can assert their results against each other, which could help finding bugs. If one implementation would produce a different result than the others, then it is likely a bug that should be reported and hopefully fixed. However, note that the tested graph need to be constructed in such a way that there must not be more than one path (among the first shortest paths you use test assertions for) with the same total weight. If multiple paths have the same total weight then it is not obvious which should be sorted first, and then it would not be surprising if different implementations produce different results.

When you run such tests with the same test data for different implementations then you can also easily **compare the performance for the different implementations**.       

### Example of how to use this shortest paths adapter library:

The Java code example below uses the following graph with four vertices (A,B,C,D) and five edges with weights.<br>(A to B (5) , A to C (6) , B to C (7)  , B to D (8) , C to D (9) ).<br>![alt text](images/shortest_paths_getting_started_example.gif "Logo Title Text 1")<br>
There are four possible paths from A to D , with the total weight within parenthesis : 
* A to B to D (total cost: 13 = 5 + 8)
* A to C to D (total cost: 15 = 6 + 9)
* A to B to C to D (total cost: 21 = 5 + 7 + 9)

For example, the vertices might represent cities, and the edges might represent roads with distances as weights.

The Java code below can be used for finding the shortest paths (sorted with the shortest first) from A to D :

```java
import java.util.Arrays;
import java.util.List;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Graph;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.PathFinder;
import com.programmerare.shortestpaths.core.api.PathFinderFactory;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;
import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import com.programmerare.shortestpaths.adapter.jgrapht.PathFinderFactoryJgrapht;

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
	
	Graph graph = createGraph(edges, GraphEdgesValidationDesired.YES); 

	PathFinderFactory pathFinderFactory = new PathFinderFactoryJgrapht();
	// or: pathFinderFactory = new PathFinderFactoryBsmock();
	// or: pathFinderFactory = new PathFinderFactoryYanQi();
	// or: pathFinderFactory = new PathFinderFactoryReneArgento();
	// (currently there are four implementations)

	PathFinder pathFinder = pathFinderFactory.createPathFinder(graph);
	List<Path> shortestPaths = pathFinder.findShortestPaths(a, d, 10); // last parameter is max number to return but in this case there are only 3 possible paths
	for (Path path : shortestPaths) {
		Weight totalWeightForPath = path.getTotalWeightForPath();
		System.out.println(totalWeightForPath);
		List<Edge> pathEdges = path.getEdgesForPath();
		for (Edge edge : pathEdges) {
			Vertex startVertex = edge.getStartVertex();
			Vertex endVertex = edge.getEndVertex();
			Weight edgeWeight = edge.getEdgeWeight();					
			System.out.println(startVertex);
			System.out.println(endVertex);
			System.out.println(edgeWeight);
		}			
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
		<version>deb1dc5b437bc2e12a5a49b77731a8da5ef8c89b</version> <!--https://github.com/TomasJohansson/adapters-shortest-paths/commits/master  -->
	</dependency>      
</dependencies>
```
Note that "jitpack" is used since currently there is no release of this library in the maven central repository.

### Java version
Java **6** is currently used for compiling the the core library itself, including the Adapter implementations.

However, two of the Adaptee libraries are compiled for Java **8**.
( [JGraphT](https://github.com/jgrapht/jgrapht/blob/master/pom.xml) and [the fork of "reneargento/algorithms-sedgewick-wayne"](https://github.com/TomasJohansson/algorithms-sedgewick-wayne) )  

The other two Adaptee libraries are actually compiled for Java **5**.
( [the fork of "bsmock/k-shortest-paths"](https://github.com/TomasJohansson/k-shortest-paths) and [the fork of "yan-qi/k-shortest-paths-java-version"](https://github.com/TomasJohansson/k-shortest-paths-java-version) )  
  
This means that if you are using Java 8, then you should be able to use all Adapters, but if you use Java 6 or Java 7 then you are restricted to using only the latter two of the above Adapters. 
     

### Some comments about the four adaptee libraries currently being used

There are currently Adapter implementations for the following four libraries:
* <https://github.com/jgrapht/jgrapht>
* <https://github.com/yan-qi/k-shortest-paths-java-version>
* <https://github.com/bsmock/k-shortest-paths>
* <https://github.com/reneargento/algorithms-sedgewick-wayne>

Regarding the versions/"releases" of the above libraries:

* Regarding jgrapht, the [version 1.1.0](https://github.com/jgrapht/jgrapht/releases/tag/jgrapht-1.1.0) is currently used.         
* Regarding the ["yan-ki"](https://github.com/yan-qi/k-shortest-paths-java-version) implementation, there seems to be no official releases. Also, I could not find a way of reusing the library without modification since it seems to [require input from a file](https://github.com/yan-qi/k-shortest-paths-java-version/issues/4) which would mean I could not have used it as intended, e.g. programmatically creating a big graph for comparison against other implementations. This was one of the reasons why I instead use a [forked version](https://github.com/TomasJohansson/k-shortest-paths-java-version/commits/programmatic-graph-creation-without-using-inputfile). Another reason for creating and using a fork was the limitation that the input vertices needs to be integer in a sequence, while the other libraries support general strings. I fixed this with a mapper class in which maps back and forth from more general input strings.          
* Regarding the ["bsmock"](https://github.com/bsmock/k-shortest-paths) implementation, it was not even a maven project. Therefore I [forked](https://github.com/TomasJohansson/k-shortest-paths/commits/adding-maven-structure-and-junit-test) it and created a maven project of it. I have created a [pull request with my changes](https://github.com/bsmock/k-shortest-paths/pull/2).
* Regarding "reneargento", it was neither (just like above "bsmock") a maven project, which was the reason for forking it. It also included a jar file, but the fork is instead using maven and jitpack for defining the dependency in the pom file. Please [read about the license for that dependency](https://github.com/TomasJohansson/algorithms-sedgewick-wayne).
   
	