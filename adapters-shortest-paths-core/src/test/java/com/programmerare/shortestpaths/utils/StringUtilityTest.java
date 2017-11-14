package com.programmerare.shortestpaths.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringUtilityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMultilineStringAsListOfStringsIgnoringThoseWithWhiteSpaceOnly() {
		String s = "AB\r\n" + 
				"XY\r\n" + 
				"BX\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"BA\r\n" + 
				"CD\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";
		List<String> list = StringUtility.getMultilineStringAsListOfStringsIgnoringThoseWithWhiteSpaceOnly(s);
		assertNotNull(list);
		assertEquals(5, list.size());
		assertEquals("AB", list.get(0));
		assertEquals("XY", list.get(1));
		assertEquals("BX", list.get(2));
		assertEquals("BA", list.get(3));
		assertEquals("CD", list.get(4));
		
		
	}

}