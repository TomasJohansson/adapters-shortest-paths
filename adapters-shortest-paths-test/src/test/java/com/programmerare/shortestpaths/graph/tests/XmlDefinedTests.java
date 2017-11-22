package com.programmerare.shortestpaths.graph.tests;

import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.GraphFactory;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.parsers.EdgeParser;
import com.programmerare.shortestpaths.core.parsers.PathParser;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidator;
import com.programmerare.shortestpaths.graph.utils.FileReaderForGraphEdges;
import com.programmerare.shortestpaths.graph.utils.GraphFactories;
import com.programmerare.shortestpaths.graph.utils.GraphShortestPathAssertionHelper;
import com.programmerare.shortestpaths.utils.ResourceReader;
import com.programmerare.shortestpaths.utils.XmlFileReader;

/**
 * The class can run test cases with both input data and expected output data defined in xml files.
 * See an example in the xml file ".../src/test/resources/test_graphs/small_graph_1.xml"   
 * 
 * @author Tomas Johansson
 */
public class XmlDefinedTests {
	
	private XmlFileReader xmlFileReader;
	private ResourceReader resourceReader;
	private FileReaderForGraphEdges fileReaderForGraphTestData;
	private List<GraphFactory<Edge>> graphFactories;
	private GraphShortestPathAssertionHelper graphShortestPathAssertionHelper;
	private EdgeParser edgeParser;


	@Before
	public void setUp() throws Exception {
		fileReaderForGraphTestData = FileReaderForGraphEdges.createFileReaderForGraphEdges();
		
		graphFactories = GraphFactories.createGraphFactories();
		graphShortestPathAssertionHelper = new GraphShortestPathAssertionHelper(false);
		
		xmlFileReader = new XmlFileReader();
		resourceReader = new ResourceReader();
		edgeParser = EdgeParser.createEdgeParser();
	}

	@Test
	public void test_all_xml_files_in_test_graphs_directory() throws IOException {
		// the advantage with iterating xml files is this method is that you do not have to add a new test method
		// for each new xml file with test cases, but the disadvantage is that you do not automatically see which file failed
		// but that problem is handled in the loop below with a try/catch/throw
		final List<String> fileNames = resourceReader.getNameOfFilesInResourcesFolder("test_graphs");
		for(String fileName : fileNames) {
			if(fileName.toLowerCase().endsWith(".xml")) {
				try {
					runTestCaseDefinedInXmlFile(fileName);
				}
				catch(Exception e) {
					// Without try/catch here we would fail without seeing which test file caused the failure
					// We might use the method 'Assert.fail' here but then we do not see the stack trace, so therefore throw exception here 
					throw new java.lang.AssertionError("Failure for the test defined in file " + fileName + " , " + e.getMessage(), e);
				}
			}
		}
	}

	private void runTestCaseDefinedInXmlFile(final String nameOfXmlFileWithoutDirectoryPath) throws IOException {
		runTestCaseDefinedInXmlFileWithPathIncludingDirectory("test_graphs/" + nameOfXmlFileWithoutDirectoryPath);
	}
	
	private void runTestCaseDefinedInXmlFileWithPathIncludingDirectory(final String pathToResourceXmlFile) throws IOException {
		final Document document = xmlFileReader.getResourceFileAsXmlDocument(pathToResourceXmlFile);
		final NodeList nodeList = xmlFileReader.getNodeListMatchingXPathExpression(document, "graphTestData/graphDefinition");
		assertNotNull(nodeList);
		assertEquals(1, nodeList.getLength()); // should only be one graph definition per file
		
		Node nodeWithGraphDefinition = nodeList.item(0);
		assertNotNull(nodeWithGraphDefinition);
//		assertEquals("kkk", nodeWithGraphDefinition.getTextContent());
		final List<Edge> edgesForGraph = edgeParser.fromMultiLinedStringToListOfEdges(nodeWithGraphDefinition.getTextContent());
		//assertEquals(5,  edges.size());
		final PathParser pathParser = new PathParser(edgesForGraph);
		
		final NodeList nodeListWithTestCases = xmlFileReader.getNodeListMatchingXPathExpression(document, "graphTestData/testCase");
		assertNotNull(nodeListWithTestCases); // shouold be zero rather than null i.e. this is okay to test
		//assertEquals(1, nodeListWithTestCases.getLength()); // can be many (or zero, then just validat implementatins against each other)
		for (int i = 0; i < nodeListWithTestCases.getLength(); i++) {
			Node itemWithTestCase = nodeListWithTestCases.item(i);
			final NodeList nodeListWithInput = xmlFileReader.getNodeListMatchingXPathExpression(itemWithTestCase, "input");
			final String outputExpectedAsMultiLinedString  = xmlFileReader.getTextContentNodeOfFirstSubNode(itemWithTestCase, "outputExpected");
			System.out.println("outputExpectedAsMultiLinedString " + outputExpectedAsMultiLinedString);
			final List<Path<Edge>> expectedListOfPaths  = pathParser.fromStringToListOfPaths(outputExpectedAsMultiLinedString);
			final GraphEdgesValidator<Edge> edgeGraphEdgesValidator = new GraphEdgesValidator<Edge>();
			edgeGraphEdgesValidator.validateEdgesAsAcceptableInputForGraphConstruction(edgesForGraph);
			edgeGraphEdgesValidator.validateAllPathsOnlyContainEdgesDefinedInGraph(expectedListOfPaths, edgesForGraph);
			assertEquals(1, nodeListWithInput.getLength()); // should only be one input element
			Node nodeWithInputForTestCase  = nodeListWithInput.item(0);
			//final NodeList nodeListWithInput = xmlFileReader.getNodeListMatchingXPathExpression(itemWithTestCase, "input");
			final String startVertexId = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "startVertex");
			final String endVertexId = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "endVertex");
//			System.out.println("startVertexId " + startVertexId);
//			System.out.println("endVertexId " + endVertexId);
			final String maxNumberOfPathsAsString = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "maxNumberOfPaths");
			final int maxNumberOfPaths = Integer.parseInt(maxNumberOfPathsAsString);
			System.out.println("maxNumberOfPaths " + maxNumberOfPaths);
			graphShortestPathAssertionHelper.testResultsWithImplementationsAgainstEachOther(
				edgesForGraph, 
				createVertex(startVertexId), 
				createVertex(endVertexId), 
				maxNumberOfPaths, 
				graphFactories,
				expectedListOfPaths, // null, // expectedListOfPaths , use null when we do not want to fail because of expected output according to xml but maybe instyead want to print output with below paaraeter
				false // true // true when we want "debug output"
			);
		}
	}
	
	@Deprecated // use xml instead
	@Test
	public void test_graph_datafile__small_graph_1() throws IOException {
		final String filePath = "test_graphs/small_graph_1.txt";
//		A B 5
//		A C 6
//		B C 7
//		B D 8
//		C D 9
		final String startVertexId = "A";
		final String endVertexId = "D";
		final int maxNumberOfPathsToTryToFind = 5;
		// TODO: improve the usage of files with test data.
		// currently only the edges are specified in the test file 
		// while the tests from A to B is hardcoded as above
		
		// TODO: use xml reader instead to use an xml file instead 
		
		testPathResultsForImplementationsAgainstEachOther(filePath, startVertexId, endVertexId, maxNumberOfPathsToTryToFind);
	}

	@Deprecated // use xml instead
	@Test
	public void test_graph_datafile__small_graph_2() throws IOException {
		final String filePath = "test_graphs/small_graph_2.txt";
//		F G 13
//		F H 14
//		F I 15
//		F J 16
//		G H 17
//		G I 18
//		G J 19
//		H I 20
//		H J 21
		final String startVertexId = "F";
		final String endVertexId = "J";
		final int maxNumberOfPathsToTryToFind = 5;
		testPathResultsForImplementationsAgainstEachOther(filePath, startVertexId, endVertexId, maxNumberOfPathsToTryToFind);		
	}

	private void testPathResultsForImplementationsAgainstEachOther(
		final String filePath, 
		final String startVertexId, 
		final String endVertexId, 
		final int maxNumberOfPathsToTryToFind
	) {
		final Vertex startVertex = createVertex(startVertexId);
		final Vertex endVertex = createVertex(endVertexId);
		final List<Edge> edgesForGraph = fileReaderForGraphTestData.readEdgesFromFile(filePath);
		graphShortestPathAssertionHelper.testResultsWithImplementationsAgainstEachOther(
			edgesForGraph, 
			startVertex, 
			endVertex, 
			maxNumberOfPathsToTryToFind, 
			graphFactories
		);
	}
	
}
