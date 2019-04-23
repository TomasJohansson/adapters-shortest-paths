package com.programmerare.shortestpaths.graph.utils;

import com.programmerare.shortestpaths.core.api.Edge;
import com.programmerare.shortestpaths.core.api.Path;
import com.programmerare.shortestpaths.core.api.Vertex;
import com.programmerare.shortestpaths.core.api.Weight;
import com.programmerare.shortestpaths.core.api.generics.GraphGenerics;
import com.programmerare.shortestpaths.core.parsers.EdgeParser;
import com.programmerare.shortestpaths.core.parsers.PathParser;

import java.util.ArrayList;
import java.util.List;

public class GraphAndPathRenderer {
    private final static String LINE_SEPARATOR = System.getProperty("line.separator");
    private final static List<Edge> emptyListOfEdges = new ArrayList<Edge>();
    
    private final EdgeParser<Edge, Vertex, Weight> edgeParser;
    private final PathParser<Path, Edge, Vertex, Weight> pathParserPath;
    
    public GraphAndPathRenderer() {
        edgeParser = EdgeParser.createEdgeParser(new EdgeParser.EdgeFactoryDefault());
        pathParserPath = PathParser.createPathParserGenerics(emptyListOfEdges);
    }
    
    public String getGraphAsStringWithOneEdgePerLine(GraphGenerics<Edge, Vertex, Weight> graph) {
        final List<Edge> edges = graph.getEdges();
        final StringBuilder sb = new StringBuilder();
        for (Edge edge: edges) {
            sb.append(edgeParser.fromEdgeToString(edge));
            sb.append(LINE_SEPARATOR);    
        }
        return sb.toString();
    }

    public String getPathAsString(Path path, boolean includeNewLineSeparatorAsFirstCharacter) {
        final String pathAsString = pathParserPath.fromPathToString(path);
        if(includeNewLineSeparatorAsFirstCharacter) return LINE_SEPARATOR + pathAsString; 
        else return pathAsString;
    }
    
    public String getPathsAsStringWithOnePathPerLine(List<Path> paths) {
        final StringBuilder sb = new StringBuilder();
        for (Path path: paths) {
            String pathAsString = pathParserPath.fromPathToString(path);
            sb.append(pathAsString);
            sb.append(LINE_SEPARATOR);
        }
        return sb.toString();
    }
}