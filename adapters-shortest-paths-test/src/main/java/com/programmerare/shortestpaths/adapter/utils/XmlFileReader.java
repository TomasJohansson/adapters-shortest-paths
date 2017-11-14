package com.programmerare.shortestpaths.adapter.utils;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public final class XmlFileReader {

	private final DocumentBuilder documentBuilder;
	private final XPath xPath;

	public XmlFileReader() throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final XPathFactory xPathfactory = XPathFactory.newInstance();
		documentBuilder = factory.newDocumentBuilder();
		xPath = xPathfactory.newXPath();
	}

	public NodeList getNodeListMatchingXPathExpression(final Node node, final String xPathExpressionAsString) {
		try {
			final XPathExpression xPathExpression = xPath.compile(xPathExpressionAsString);
			final Object nodesFoundByXPathExpression = xPathExpression.evaluate(node, XPathConstants.NODESET);
			return (NodeList) nodesFoundByXPathExpression;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param pathToResourceFile example: if you want to retrieve the file "testing the content of the file "/src/test/resources/directory_for_xmlfilereader_test/xmlFileReaderTest.xml"
	 * then the parameter value should be "directory_for_xmlfilereader_test/xmlFileReaderTest.xml"
	 * @return
	 */
	public Document getResourceFileAsXmlDocument(final String pathToResourceFile) {
		final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pathToResourceFile);
		final InputSource inputSource = new InputSource(inputStream);
		return getInputSourceAsDocument(inputSource);
	}

	
	private Document getInputSourceAsDocument(final InputSource xmlInputSource) {
		try {
			return documentBuilder.parse(xmlInputSource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
//	private InputSource getXmlStringAsInputSource(final String xmlString) {
//		final StringReader reader = new StringReader(xmlString);
//		return new InputSource(reader);
//	}
	
//	private NodeList getNodeListMatchingXPathExpression(String xml, String xPathExpression) {
//		final Document document = getInputSourceAsDocument(getXmlStringAsInputSource(xml));
//		return getNodeListMatchingXPathExpression(document, xPathExpression);
//	}	
}
