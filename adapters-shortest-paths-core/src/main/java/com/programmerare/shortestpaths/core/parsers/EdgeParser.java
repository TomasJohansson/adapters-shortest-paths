package com.programmerare.shortestpaths.core.parsers;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.utils.StringUtility;

/**
 * See javadoc comments at the two essential methods in this class, i.e. the methods which convert between String and Edge.
 *  
 * @author Tomas Johansson
 */
public final class EdgeParser {

	/**
	 * when splitting, an regular expression can be used e.g. "\\s++" for matching one or more white space characters
	 * while at the creation you need to be precise e.g. create a string with exactly one space.
	 * However, these two should be compatible in the sense that the string used for creating should be possible 
	 * to parse back for the splitting string (which is the case with the example with a space for creation
	 * and the above mention regular expression for splitting.
	 * The things splitted/created with these strings are the separators in a string like this:
	 * "X Y 12.34" (start vertex id + separator + end vertex id + separator + weight)  
	 */
	private final String separatorBetweenEdgesAndWeightWhenSplitting;
	private final String separatorBetweenEdgesAndWeightWhenCreating;
	
	// The values below should 1,2,3 (and the current default is that order)
	// Such an order 1,2,3 means that the edge parts are in that order e.g. "X Y 12.34" 
	// wherre X (1) is the start vertex id and Y (2) is the id for end vertex and 12.34 (3) is the weight.
	private final int orderForStartVertex;
	private final int orderForEndVertex;
	private final int orderForWeight;

	/**
	 * @param separatorBetweenEdgesAndWeightWhenSplitting
	 * @param separatorBetweenEdgesAndWeightWhenCreating
	 * @param orderForStartVertex
	 * @param orderForEndVertex
	 * @param orderForWeight
	 */
	private EdgeParser(
		final String separatorBetweenEdgesAndWeightWhenSplitting, 
		final String separatorBetweenEdgesAndWeightWhenCreating, 
		final int orderForStartVertex, 
		final int orderForEndVertex, 
		final int orderForWeight
	) {
		this.separatorBetweenEdgesAndWeightWhenSplitting = separatorBetweenEdgesAndWeightWhenSplitting;
		this.separatorBetweenEdgesAndWeightWhenCreating = separatorBetweenEdgesAndWeightWhenCreating;
		this.orderForStartVertex = orderForStartVertex;
		this.orderForEndVertex = orderForEndVertex;
		this.orderForWeight = orderForWeight;
		int sum = orderForStartVertex + orderForEndVertex + orderForWeight;
		if( 
			(sum != 6) // 1 + 2 + 3 
			||
			(  (orderForStartVertex < 1) || (orderForEndVertex < 1) || (orderForWeight < 1) )
			||
			( (orderForStartVertex > 3) || (orderForEndVertex > 3) || (orderForWeight > 3) )
		) { 
			throw new RuntimeException("The order for each part must be 1,2,3 and not the same order for any two parts. The order input parameters were: " + orderForStartVertex + " , " + orderForEndVertex + " , "  + orderForWeight);
		}
	}

	/**
	 * TODO: if/when this method is opened for public use, then write tests for validatinng correct input data.
	 * @return
	 */
	public static EdgeParser createEdgeParser() {
		return createEdgeParser("\\s+", " ", 1, 2, 3);
	}
	
	// TODO: if this method is "opened" for client code i.e. made public then write some tests with validation of input
	private static EdgeParser createEdgeParser(
		final String separatorBetweenEdgesAndWeightWhenSplitting, 
		final String separatorBetweenEdgesAndWeightWhenCreating, 
		final int orderForStartVertex, 
		final int orderForEndVertex, 
		final int orderForWeight	
	) {
		return new EdgeParser(
			separatorBetweenEdgesAndWeightWhenSplitting, 
			separatorBetweenEdgesAndWeightWhenCreating, 
			orderForStartVertex, 
			orderForEndVertex, 
			orderForWeight				
		);
	}
	
	/**
	 * Typical (intended) usage of the method:
	 * Read input line by line from a file, and each line represents an Edge, which then can be parsed with this method.
	 * @param stringRepresentationOfEdge format: "startVertexId [SPACE] endVertexId [SPACE] weight", 
	 * 	for example "X Y 12.34" for an edge from vertex X to vertex Y with 12.34 as the weight 
	 * @return
	 */
	public Edge fromStringToEdge(final String stringRepresentationOfEdge) {
		final String[] array = stringRepresentationOfEdge.split(separatorBetweenEdgesAndWeightWhenSplitting);
		// if(split.length < 3) // TODO throw
		final Vertex startVertexId = createVertex(array[orderForStartVertex-1]);
		final Vertex endVertexId = createVertex(array[orderForEndVertex-1]);
		final Weight weight = createWeight(Double.parseDouble(array[orderForWeight-1]));
		final Edge edge = createEdge(startVertexId, endVertexId, weight);
		return edge;
	}
	
	/**
	 * An example usage of this method can be to generate )e.g. randomly) lots of Edges for a Graph, to be used in testing.
	 * Then we cab convert the edges to string format with this method and write them to a file, 
	 * and then create test reading from the file and recreating the edges with a corresponding method in this class
	 * which converts in the other direction i.e. from String to Edge.
	 * The reason for doing these things is that you want use regression testing with repeatable deterministic assertions,
	 * which you will not get if you randomly generate new graphs every time.
	 * Regarding how to produce assertions for a randomly generated graph written to a file,
	 * one method is to use the assertions with different implementations, and if three or more independen implementations 
	 * produce the same result, then it is reasonable to assume that the result is correct, and those expected 
	 * assertions might also be generated to a file, rather than every time only being able to assert 
	 * that different implementations produce the same result.  
	 * @param edge
	 * @return
	 */
	public String fromEdgeToString(final Edge edge) {
		// if(edge == null) // TODO throw		
		String[] array = new String[3];
		array[orderForStartVertex-1] = edge.getStartVertex().getVertexId();
		array[orderForEndVertex-1] = edge.getEndVertex().getVertexId();
		array[orderForWeight-1] = Double.toString(edge.getEdgeWeight().getWeightValue());
		return array[0] + separatorBetweenEdgesAndWeightWhenCreating + array[1] + separatorBetweenEdgesAndWeightWhenCreating + array[2];
	}

	/**
	 * 
	 * @param multiLinedString a string including linebreaks where each line defines an edge with vertices and weight. 
	 * A string like this is intended to be surrounded by  xml tags in xml files but the content will then use this method.
	    A B 5
	    A C 6
	    B C 7
	    B D 8
	    C D 9    
	 * @return
	 */
	public List<Edge> fromMultiLinedStringToListOfEdges(final String multiLinedString) {
		final List<Edge> edges = new ArrayList<Edge>();
		final List<String> edgesAsStrings = StringUtility.getMultilineStringAsListOfTrimmedStringsIgnoringLinesWithOnlyWhiteSpace(multiLinedString);
		for (String string : edgesAsStrings) {
			edges.add(fromStringToEdge(string));
		}
		return edges;
	}
}