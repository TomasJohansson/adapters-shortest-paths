package com.programmerare.shortestpaths.graph.tests;

import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.validation.GraphEdgesValidator.createGraphEdgesValidator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo; // artifact/jarfile "hamcrest-library" is needed, i.e. this method is NOT included in "hamcrest-core"
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.programmerare.shortestpaths.adapter.impl.bsmock.GraphFactoryBsmock;
import com.programmerare.shortestpaths.adapter.impl.jgrapht.GraphFactoryJgrapht;
import com.programmerare.shortestpaths.adapter.impl.yanqi.GraphFactoryYanQi;
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
import com.programmerare.shortestpaths.utils.EdgeUtility;
import com.programmerare.shortestpaths.utils.EdgeUtility.SelectionStrategyWhenEdgesAreDuplicated;
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
	private GraphShortestPathAssertionHelper graphShortestPathAssertionHelper;
	private EdgeParser edgeParser;

	private List<GraphFactory<Edge>> graphFactoriesForAllImplementations;
	private List<GraphFactory<Edge>> graphFactories;

	private List<String> pathsToResourcesFoldersWithXmlTestFiles;

	private final static String BASE_DIRECTORY_FOR_XML_TEST_FILES = "test_graphs/";
	/**
	 * The test data in the xml files are based on test data found in the implementation library 
	 * for the implementation "bsmock", i.e. the txt files in the following directory:
	 * https://github.com/bsmock/k-shortest-paths/tree/master/edu/ufl/cise/bsmock/graph/ksp/test 
	 * https://github.com/TomasJohansson/k-shortest-paths/tree/master/edu/ufl/cise/bsmock/graph/ksp/test
	 */
	private final static String DIRECTORY_FOR_XML_TEST_FILES_FROM_BSMOCK = BASE_DIRECTORY_FOR_XML_TEST_FILES + "origin_bsmock/";
	
	// TODO: currently no files in this directory but when they are created here there shuuld be a comment here similar to the comment above
	private final static String DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI = BASE_DIRECTORY_FOR_XML_TEST_FILES + "origin_yanqi/";

	/**
	 * Used for asserting that we have tested at least a certain number of xml test files,
	 * to avoid the potential problem that we iterated empty lists of files and then believe everything worked 
	 * even though we actually tested nothing.
	 */
	private int minimumTotalNumberOfXmlTestFiles = 11; // 2 in base directory and 2 in "bsmock" (actually 3 there but one is in the exclusion list of files) and some more in yanqi directory,...  
	
	@Before
	public void setUp() throws Exception {
		fileReaderForGraphTestData = FileReaderForGraphEdges.createFileReaderForGraphEdges();
		graphShortestPathAssertionHelper = new GraphShortestPathAssertionHelper(false);
		
		xmlFileReader = new XmlFileReader();
		resourceReader = new ResourceReader();
		edgeParser = EdgeParser.createEdgeParser();

		graphFactoriesForAllImplementations = GraphFactories.createGraphFactories();
		graphFactories = new ArrayList<GraphFactory<Edge>>(); // set to empty here before each test, so add to the list if it needs to be used
	
		pathsToResourcesFoldersWithXmlTestFiles = Arrays.asList(
			DIRECTORY_FOR_XML_TEST_FILES_FROM_BSMOCK, 
			DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI,
			BASE_DIRECTORY_FOR_XML_TEST_FILES // yes the base directory itself also currently has some xml test files
		);
	}


	/**
	 * The content of the test data in this xml file is copied from the "bsmock" implementation: 
	 * /tomas-fork_bsmock_k-shortest-paths/edu/ufl/cise/bsmock/graph/ksp/test/small_road_network_01.txt
	 * It contains 28.000+ Edges, and it takes about 21 seconds with the implementation "GraphYanQi" and 421 seconds with "GraphBsmock".
	 * Therefore it is excluded from the frequent testing.
	 */
	private final static String XML_FILE_BIG_TEST__SMALL_ROAD_NETWORK_01 = "small_road_network_01.xml";
	
	/**
	 * The content of the test data in this xml file is copied from the "yanqi" implementation: 
	 * 	https://github.com/yan-qi/k-shortest-paths-java-version/blob/master/data/test_50_2
	 * It takes too long time for one of the implementations and therefore it is excluded from the frequent testing.
	 */	
	private final static String XML_FILE_BIG_TEST__50_2 = "test_50_2.xml";

	/**
	 * The content of the test data in this xml file is copied from the "yanqi" implementation: 
	 * 	https://github.com/yan-qi/k-shortest-paths-java-version/blob/master/data/test_50
	 * It takes too long time for one of the implementations and therefore it is excluded from the frequent testing.
	 */	
	private final static String XML_FILE_BIG_TEST__50 = "test_50.xml";

	/**
	 * Regarding why these files are excluded, see the comments where the constants are defined
	 */
	private final static List<String> xmlFilesToExclude = Arrays.asList(
		XML_FILE_BIG_TEST__SMALL_ROAD_NETWORK_01,
		XML_FILE_BIG_TEST__50_2,
		XML_FILE_BIG_TEST__50
	);
	
	private boolean shouldBeExcdludedInFrequentTesting(final String xmlFileName) {
		return xmlFilesToExclude.contains(xmlFileName);
	}
	
	/**
	 * Method for troubleshooting (or for big slow files), when you want to temporary want to focus at one 
	 * file, as opposed to normal regression testing when all files are iterated through another test method  
	 */
	//@Test // enable this row when you want to used the method
	public void temporaryTest() throws IOException {
		// Either use all factories as the first row below, or add one or more to the list which is empty after the setup method 
//		graphFactories = graphFactoriesForAllImplementations;
		// Use the row above OR INSTEAD some of the rows below, to specify which implementations should be used for the test
		//graphFactories.add(new GraphFactoryYanQi<Edge>());
		//graphFactories.add(new GraphFactoryBsmock<Edge>());
//		graphFactories.add(new GraphFactoryJgrapht<Edge>()); // 67 seconds, compaerd to less than 1 seconds for the other two implementations 
		
//		runTestCaseDefinedInXmlFile("tiny_graph_01.xml", graphFactories);
//		runTestCaseDefinedInXmlFile("tiny_graph_02.xml", graphFactories);
//		runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "network.xml", graphFactories);
//		runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_5.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_50.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_6_1.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_6_2.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_6.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_7.xml", graphFactories);
		//runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, "test_8.xml", graphFactories);
	}		
	
	/**
	 * Testing xml file with a big graph which takes too long time for some implementations 
	 * and therefore that file is excluded from the normal testing where all implementations are used,
	 * and instead use this method for only testing reasonable fast implementation.
	 * @throws IOException
	 */
	@Test   
	public void testXmlFile_smallRoadNetwork01() throws IOException {
		graphFactories.add(new GraphFactoryYanQi<Edge>()); // 16 seconds, reasonable acceptable for frequent regression testing 
		// graphFactories.add(new GraphFactoryBsmock<Edge>()); // 298 seconds (five minutes !) NOT acceptable for frequent regression testing 
		// graphFactories.add(new GraphFactoryJgrapht<Edge>()); // gave up waiting after 30+ minutes !
		runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_BSMOCK, XML_FILE_BIG_TEST__SMALL_ROAD_NETWORK_01, graphFactories);
	}
	
	@Test   
	public void testXmlFile_test_50_2() throws IOException {
		graphFactories.add(new GraphFactoryYanQi<Edge>());
		graphFactories.add(new GraphFactoryBsmock<Edge>());
		//graphFactories.add(new GraphFactoryJgrapht<Edge>()); // 16 seconds, compared to less than 1 seconds for the other two implementations 
		runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, XML_FILE_BIG_TEST__50_2, graphFactories);
	}
	
	@Test   
	public void testXmlFile_test_50() throws IOException {
		graphFactories.add(new GraphFactoryYanQi<Edge>());
		graphFactories.add(new GraphFactoryBsmock<Edge>());
		//graphFactories.add(new GraphFactoryJgrapht<Edge>()); // 13 seconds, compared to less than 2 seconds for the other two implementations 
		runTestCaseDefinedInXmlFile(DIRECTORY_FOR_XML_TEST_FILES_FROM_YANQI, XML_FILE_BIG_TEST__50, graphFactories);
	}	
	
	@Test
	public void test_all_xml_files_in_test_graphs_directory() throws IOException {
		// the advantage with iterating xml files is this method is that you do not have to add a new test method
		// for each new xml file with test cases, but the disadvantage is that you do not automatically see which file failed
		// but that problem is handled in the loop below with a try/catch/throw

		int counterForNumberOfXmlFilesTested = 0;
		for (final String pathToResourcesFoldersWithXmlTestFiles : pathsToResourcesFoldersWithXmlTestFiles) {
			final List<String> fileNames = resourceReader.getNameOfFilesInResourcesFolder(pathToResourcesFoldersWithXmlTestFiles);
			for(final String fileName : fileNames) {
				if(fileName.toLowerCase().endsWith(".xml") && !shouldBeExcdludedInFrequentTesting(fileName)) {
					try {
						runTestCaseDefinedInXmlFile(pathToResourcesFoldersWithXmlTestFiles, fileName, graphFactoriesForAllImplementations);
						counterForNumberOfXmlFilesTested++;
					}
					catch(Exception e) {
						// Without try/catch here we would fail without seeing which test file caused the failure
						// We might use the method 'Assert.fail' here but then we do not see the stack trace, so therefore throw exception here 
						throw new java.lang.AssertionError("Failure for the test defined in file " + fileName + " , " + e.getMessage(), e);
					}
				}
			}
		}
		assertThat(counterForNumberOfXmlFilesTested, greaterThanOrEqualTo(minimumTotalNumberOfXmlTestFiles));
	}

	private void runTestCaseDefinedInXmlFile(
		final String pathToResourcesFoldersIncludingTrailingSlash, 
		final String nameOfXmlFileWithoutDirectoryPath,
		final List<GraphFactory<Edge>> graphFactories
	) throws IOException {
		runTestCaseDefinedInXmlFileWithPathIncludingDirectory(
			pathToResourcesFoldersIncludingTrailingSlash + nameOfXmlFileWithoutDirectoryPath, 
			graphFactories
		);
	}
	
	private void runTestCaseDefinedInXmlFileWithPathIncludingDirectory(
		final String pathToResourceXmlFile, 
		final List<GraphFactory<Edge>> graphFactories
	) throws IOException {
		final Document document = xmlFileReader.getResourceFileAsXmlDocument(pathToResourceXmlFile);
		final NodeList nodeList = xmlFileReader.getNodeListMatchingXPathExpression(document, "graphTestData/graphDefinition");
		assertNotNull(nodeList);
		assertEquals(1, nodeList.getLength()); // should only be one graph definition per file
		
		Node nodeWithGraphDefinition = nodeList.item(0);
		assertNotNull(nodeWithGraphDefinition);


		final List<Edge> edgesForGraphPotentiallyIncludingDuplicatedEdges = edgeParser.fromMultiLinedStringToListOfEdges(nodeWithGraphDefinition.getTextContent());
		//System.out.println("edgesForGraphPotentiallyIncludingDuplicatedEdges " + edgesForGraphPotentiallyIncludingDuplicatedEdges.size());
		// There can be duplicates in the list of edges, whcih would cause exception at validation,
		// so therefore below instead remove duplicated with a chosen strategy 
		// and one example file with many duplicated edges is "small_road_network_01.xml"
		final List<Edge> edgesForGraph = EdgeUtility.getEdgesWithoutDuplicates(edgesForGraphPotentiallyIncludingDuplicatedEdges, SelectionStrategyWhenEdgesAreDuplicated.FIRST_IN_LIST_OF_EDGES);
		//System.out.println("edgesForGraph " + edgesForGraph.size());
		// edgesForGraphPotentiallyIncludingDuplicatedEdges 28524
		// edgesForGraph 28320
		// The above output was the result when running tests for the file "small_road_network_01.xml" 
		
		
		final PathParser pathParser = new PathParser(edgesForGraph);
		
		final NodeList nodeListWithTestCases = xmlFileReader.getNodeListMatchingXPathExpression(document, "graphTestData/testCase");
		assertNotNull(nodeListWithTestCases); // shouold be zero rather than null i.e. this is okay to test
		//assertEquals(1, nodeListWithTestCases.getLength()); // can be many (or zero, then just validat implementatins against each other)
		for (int i = 0; i < nodeListWithTestCases.getLength(); i++) {
			Node itemWithTestCase = nodeListWithTestCases.item(i);
			final NodeList nodeListWithInput = xmlFileReader.getNodeListMatchingXPathExpression(itemWithTestCase, "input");
			final String outputExpectedAsMultiLinedString  = xmlFileReader.getTextContentNodeOfFirstSubNode(itemWithTestCase, "outputExpected");
//			System.out.println("outputExpectedAsMultiLinedString " + outputExpectedAsMultiLinedString);
			final List<Path<Edge>> expectedListOfPaths  = outputExpectedAsMultiLinedString == null || outputExpectedAsMultiLinedString.trim().equals("") ? null : pathParser.fromStringToListOfPaths(outputExpectedAsMultiLinedString);
			
			final GraphEdgesValidator<Edge> edgeGraphEdgesValidator = createGraphEdgesValidator();
			edgeGraphEdgesValidator.validateEdgesAsAcceptableInputForGraphConstruction(edgesForGraph);
			if(expectedListOfPaths != null) {
				edgeGraphEdgesValidator.validateAllPathsOnlyContainEdgesDefinedInGraph(expectedListOfPaths, edgesForGraph);	
			}
			
			assertEquals(1, nodeListWithInput.getLength()); // should only be one input element
			Node nodeWithInputForTestCase  = nodeListWithInput.item(0);
			//final NodeList nodeListWithInput = xmlFileReader.getNodeListMatchingXPathExpression(itemWithTestCase, "input");
			final String startVertexId = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "startVertex");
			final String endVertexId = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "endVertex");
//			System.out.println("startVertexId " + startVertexId);
//			System.out.println("endVertexId " + endVertexId);
			final String maxNumberOfPathsAsString = xmlFileReader.getTextContentNodeOfFirstSubNode(nodeWithInputForTestCase, "maxNumberOfPaths");
			final int maxNumberOfPaths = Integer.parseInt(maxNumberOfPathsAsString);
//			System.out.println("maxNumberOfPaths " + maxNumberOfPaths);
			final boolean consoleOutputDesired = false;
			graphShortestPathAssertionHelper.setConsoleOutputDesired(consoleOutputDesired);
			graphShortestPathAssertionHelper.testResultsWithImplementationsAgainstEachOther(
				edgesForGraph, 
				createVertex(startVertexId), 
				createVertex(endVertexId), 
				maxNumberOfPaths, 
				graphFactories,
				expectedListOfPaths, // null, // expectedListOfPaths , use null when we do not want to fail because of expected output according to xml but maybe instyead want to print output with below paaraeter
				consoleOutputDesired
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
			graphFactoriesForAllImplementations
		);
	}
	
}
