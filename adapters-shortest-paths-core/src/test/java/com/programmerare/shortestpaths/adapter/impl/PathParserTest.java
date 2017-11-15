package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Path;

public class PathParserTest {

	private PathParser pathParser;

	@Before
	public void setUp() throws Exception {
		final EdgeParser edgeParser = EdgeParser.createEdgeParser();
		final List<Edge> edges = edgeParser.fromMultiLinedStringToListOfEdges("A B 5\r\n" + 
				"A C 6\r\n" + 
				"B C 7\r\n" + 
				"B D 8\r\n" + 
				"C D 9");
//	    <graphDefinition>
//	    A B 5
//	    A C 6
//	    B C 7
//	    B D 8
//	    C D 9    
//	 	</graphDefinition>		
		pathParser = new PathParser(edges);
	}

	@Test
	public void testFromStringToListOfPaths() {
//	    <outputExpected>
//			13 A B D
//			15 A C D
//			21 A B C D
//	    </outputExpected>
		
		List<Path<Edge>> lListOfPaths = pathParser.fromStringToListOfPaths("13 A B D\r\n" + 
				"15 A C D\r\n" + 
				"21 A B C D");
		assertNotNull(lListOfPaths);
		assertEquals(3,  lListOfPaths.size());
		
		Path<Edge> path1 = lListOfPaths.get(0); // 13 A B D
		Path<Edge> path2 = lListOfPaths.get(1); // 15 A C D 
		Path<Edge> path3 = lListOfPaths.get(2); // 21 A B C D
		assertNotNull(path1);
		assertNotNull(path2);
		assertNotNull(path3);
		assertEquals(13.0, path1.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		assertEquals(15.0, path2.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		assertEquals(21.0, path3.getTotalWeightForPath().getWeightValue(), SMALL_DELTA_VALUE_FOR_WEIGHT_COMPARISONS);
		
		List<Edge> edgesForPath1 = path1.getEdgesForPath();
		List<Edge> edgesForPath2 = path2.getEdgesForPath();
		List<Edge> edgesForPath3 = path3.getEdgesForPath();
		assertNotNull(edgesForPath1);
		assertNotNull(edgesForPath2);
		assertNotNull(edgesForPath3);
		assertEquals(2, edgesForPath1.size());
		assertEquals(2, edgesForPath2.size());
		assertEquals(3, edgesForPath3.size());

		// edgesForPath1 "13 A B D" means path "A -> B" and "B -> D"
		assertEquals(pathParser.getEdgeIncludingTheWeight("A", "B"), edgesForPath1.get(0));
		assertEquals(pathParser.getEdgeIncludingTheWeight("B", "D"), edgesForPath1.get(1));

		// edgesForPath2 // 15 A C D
		assertEquals(pathParser.getEdgeIncludingTheWeight("A", "C"), edgesForPath2.get(0));
		assertEquals(pathParser.getEdgeIncludingTheWeight("C", "D"), edgesForPath2.get(1));
		
		// 21 A B C D
		assertEquals(pathParser.getEdgeIncludingTheWeight("A", "B"), edgesForPath3.get(0));
		assertEquals(pathParser.getEdgeIncludingTheWeight("B", "C"), edgesForPath3.get(1));
		assertEquals(pathParser.getEdgeIncludingTheWeight("C", "D"), edgesForPath3.get(2));
	}
}
