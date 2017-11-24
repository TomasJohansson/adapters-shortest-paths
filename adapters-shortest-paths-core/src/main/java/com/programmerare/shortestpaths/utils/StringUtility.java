package com.programmerare.shortestpaths.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringUtility {
	public static List<String> getMultilineStringAsListOfTrimmedStringsIgnoringLinesWithOnlyWhiteSpace(String multilinedStringWithLineBreaks) {
		final String lines[] = multilinedStringWithLineBreaks.split("[\r\n]+");
		final List<String> listOfLines = new ArrayList<String>(); 
		for (String line : lines) {
			if(line != null && !line.trim().equals("") ) {
				listOfLines.add(line.trim());	
			}
		}
		return listOfLines;	
	}

	public static String getDoubleAsStringWithoutZeroesAndDotIfNotRelevant(double d) {
		String s = Double.toString(d);
		s = s.replaceFirst("\\.0+$", "").replaceFirst("0+$", "");
		// TODO overkill to implement with regexp as above. instead use some formatter https://stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-unnecessary-decimal-0
		return s;
	}
}
