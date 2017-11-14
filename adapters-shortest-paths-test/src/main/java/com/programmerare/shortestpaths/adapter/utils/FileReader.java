package com.programmerare.shortestpaths.adapter.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Resources;

public class FileReader {

	/**
	 * Reads line from a file which is assumed to use charset UTF-8
	 * @param filePath e.g. ""test_graphs/tiny_graph_01.txt"
	 * @return
	 */
	public List<String> readLines(final String filePath)  {
		final Charset charsetForInputFile = Charset.forName("UTF-8");
		return readLines(filePath, charsetForInputFile);
	}
	
	public List<String> readLines(final String filePath, Charset charsetForInputFile)  {
		try {
			final URL resource = Resources.getResource(filePath);
			final List<String> linesInFile = Resources.readLines(resource, charsetForInputFile);
			return linesInFile;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
}