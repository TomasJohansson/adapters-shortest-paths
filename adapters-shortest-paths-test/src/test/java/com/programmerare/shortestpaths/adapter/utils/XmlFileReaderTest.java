package com.programmerare.shortestpaths.adapter.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFileReaderTest {

	private XmlFileReader xmlFileReader;

	@Before
	public void setUp() throws Exception {
		xmlFileReader = new XmlFileReader();
	}

	@Test
	public void testGetNodeListMatchingXPathExpression() {
		// testing the content of the file "/src/test/resources/directory_for_xmlfilereader_test/xmlFileReaderTest.xml":
//		<myRoot>
//		    <mySubElement>abc</mySubElement>
//		    <mySubElement>def</mySubElement>
//		</myRoot>
		final String filePath = "directory_for_xmlfilereader_test/xmlFileReaderTest.xml";
		final Document document = xmlFileReader.getResourceFileAsXmlDocument(filePath);
		assertNotNull(document);
		final NodeList nodeList = xmlFileReader.getNodeListMatchingXPathExpression(document, "myRoot/mySubElement");
		
		assertNotNull(nodeList);
		assertEquals(2,  nodeList.getLength());
		
		final Node item1 = nodeList.item(0);
		final Node item2 = nodeList.item(1);
		assertNotNull(item1);
		assertNotNull(item2);
		
		assertEquals("abc", item1.getTextContent());
		assertEquals("def", item2.getTextContent());
	}
}