package com.programmerare.shortestpaths.adapter.jgrapht.generics;

import com.programmerare.shortestpaths.adapter.jgrapht.JGraphtAlgorithm;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
// both of the below two classes implement the above interface
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
// The above "KShortestSimplePaths" has replaced "org.jgrapht.alg.shortestpath.KShortestPaths" 
//  which was deprecated and then removed 2018-05-17 in git commit "6db9154ea569e2cb46a42815a75086ffda1b4db4"    

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

// intentional package level access (i.e. NOT public class)
// but client code should just use the public enum JGraphtAlgorithm 
class JGraphtAlgorithmFactory {
    public static KShortestPathAlgorithm<String, DefaultWeightedEdge> CreateKShortestPathAlgorithm(
        JGraphtAlgorithm jGraphtAlgorithm, 
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graphAdaptee
    ) {
        if(jGraphtAlgorithm == JGraphtAlgorithm.KShortestPathsYen) {
            return new YenKShortestPath<>(graphAdaptee);
        }
        else if(jGraphtAlgorithm == JGraphtAlgorithm.KShortestPathsBellmanFord) {
            return new KShortestSimplePaths<String, DefaultWeightedEdge>(graphAdaptee);
        }
        throw new UnsupportedOperationException("Unsupported jGraphtAlgorithm: " + jGraphtAlgorithm);
    }
}