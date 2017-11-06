package com.programmerare.shortestpaths.adapter.impl.bsmock;

import static com.programmerare.shortestpaths.adapter.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.adapter.impl.PathImpl.createPath;
import static com.programmerare.shortestpaths.adapter.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Graph;
import com.programmerare.shortestpaths.adapter.Path;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.utils.EdgeMapper;

import edu.ufl.cise.bsmock.graph.ksp.Yen;

/**
 * @author Tomas Johansson
 */
public final class GraphBsmock implements Graph {
	
	private final Yen yenAlgorithm;
	private final edu.ufl.cise.bsmock.graph.Graph graphAdaptee;
	private final EdgeMapper edgeMapper;
	
	public GraphBsmock(
		final edu.ufl.cise.bsmock.graph.Graph graphAdaptee, 
		final EdgeMapper edgeMapper
	) {
		this.graphAdaptee = graphAdaptee;
		this.edgeMapper = edgeMapper;
		yenAlgorithm = new Yen();
	}
	
	public List<Path> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path> paths = new ArrayList<Path>();		
		final List<edu.ufl.cise.bsmock.graph.util.Path> pathList = yenAlgorithm.ksp(
			graphAdaptee, 
			startVertex.getVertexId(), 
			endVertex.getVertexId(), 
			maxNumberOfPaths
		);
		
		for (edu.ufl.cise.bsmock.graph.util.Path path : pathList) {
			final LinkedList<edu.ufl.cise.bsmock.graph.Edge> listOfEdges = path.getEdges();
			final List<Edge> edges = new ArrayList<Edge>();
			for (edu.ufl.cise.bsmock.graph.Edge edge : listOfEdges) {
				edges.add(
					createEdge(
						createVertex(edge.getFromNode()), 
						createVertex(edge.getToNode()), 
						createWeight(edge.getWeight())
					)
				);
			}
			final List<Edge> originalObjectInstancesOfTheEdges = edgeMapper.getOriginalObjectInstancesOfTheEdges(edges);
			paths.add(createPath(createWeight(path.getTotalCost()), originalObjectInstancesOfTheEdges));
		}
		return Collections.unmodifiableList(paths);
	}

}