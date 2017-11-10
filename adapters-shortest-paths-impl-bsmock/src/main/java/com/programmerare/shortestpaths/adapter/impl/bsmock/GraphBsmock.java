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
import com.programmerare.shortestpaths.adapter.Weight;
import com.programmerare.shortestpaths.utils.EdgeMapper;

import edu.ufl.cise.bsmock.graph.ksp.Yen;

/**
 * @author Tomas Johansson
 */
public final class GraphBsmock<T extends Edge> implements Graph<T> {
	
	private final Yen yenAlgorithm;
	private final edu.ufl.cise.bsmock.graph.Graph graphAdaptee;
	private final EdgeMapper<T> edgeMapper;
	
	public GraphBsmock(
		final edu.ufl.cise.bsmock.graph.Graph graphAdaptee, 
		final EdgeMapper<T> edgeMapper
	) {
		this.graphAdaptee = graphAdaptee;
		this.edgeMapper = edgeMapper;
		yenAlgorithm = new Yen();
	}
	
	public List<Path<T>> findShortestPaths(
		final Vertex startVertex, 
		final Vertex endVertex, 
		final int maxNumberOfPaths
	) {
		final List<Path<T>> paths = new ArrayList<Path<T>>();		
		final List<edu.ufl.cise.bsmock.graph.util.Path> pathList = yenAlgorithm.ksp(
			graphAdaptee, 
			startVertex.getVertexId(), 
			endVertex.getVertexId(), 
			maxNumberOfPaths
		);
		for (edu.ufl.cise.bsmock.graph.util.Path path : pathList) {
			final LinkedList<edu.ufl.cise.bsmock.graph.Edge> listOfEdges = path.getEdges();
			final List<T> edges = new ArrayList<T>();
			for (edu.ufl.cise.bsmock.graph.Edge edgeAdaptee : listOfEdges) {
				final T edge = edgeMapper.getOriginalEdgeInstance(edgeAdaptee.getFromNode(), edgeAdaptee.getToNode());
				edges.add(
					edge
				);				
			}
			final Weight totalWeight = createWeight(path.getTotalCost());
			paths.add(createPath(totalWeight, edges));
		}
		return Collections.unmodifiableList(paths);
	}

}