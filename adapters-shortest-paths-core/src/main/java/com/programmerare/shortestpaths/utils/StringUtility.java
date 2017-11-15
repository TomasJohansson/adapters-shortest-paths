package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringUtility {
	public static List<String> getMultilineStringAsListOfTrimmedStringsIgnoringThoseWithWhiteSpaceOnly(String s) {
		final String lines[] = s.split("[\r\n]+");
		final List<String> listOfLines = new ArrayList<String>(); 
		for (String line : lines) {
			if(line != null && !line.trim().equals("") ) {
				listOfLines.add(line.trim());	
			}
		}
		return listOfLines;	
	}
}
