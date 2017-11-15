package com.programmerare.shortestpaths.graph.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.parsers.EdgeParser;
import com.programmerare.shortestpaths.utils.FileReader;

/**
 * The class is used for creating Edge instances from files.
 * Each line in such a file is expected to be in the format:
 * "A B 12.34"  which means an edge from Vertex A to Vertex B with Weight 12.34 
 * 
 * @author Tomas Johansson
 */
public final class FileReaderForGraphEdges {
	
	private final FileReader fileReader;
	private final EdgeParser edgeParser;
	
	private FileReaderForGraphEdges() {
		fileReader = new FileReader();
		edgeParser = EdgeParser.createEdgeParser();
	}
	
	public static FileReaderForGraphEdges createFileReaderForGraphEdges() {
		return new FileReaderForGraphEdges();
	}


	public List<Edge> readEdgesFromFile(final String filePath) {
		return readEdgesFromFile(filePath, FileReader.CHARSET_UTF_8);		
	}

	/**
	 * @param filePath a path relative to the resources directory.
	 * Example of a file: "/src/test/resources/directory_for_filereader_test/file_for_filereader_test.txt"
	 * Then the filePath parameter should be "directory_for_filereader_test/file_for_filereader_test.txt" 
	 * @param charsetForInputFile
	 * @return
	 */
	public List<Edge> readEdgesFromFile(final String filePath, Charset charsetForInputFile) {
		final List<Edge> edges = new ArrayList<Edge>();
		
		final List<String> linesFromFileWithGraphEdges = fileReader.readLines(filePath, charsetForInputFile);
		for (String lineInFileRepresengingEdge : linesFromFileWithGraphEdges) {
			edges.add(edgeParser.fromStringToEdge(lineInFileRepresengingEdge));
		}
		return edges;
	}
}