package com.programmerare.shortestpaths.adapter.jgrapht;

/**
 * Yen is faster and should normally be used.
 * 
 * The reason for including the variant of Bellman-Ford as an alternative
 * is to use it for testing purposes i.e. to verify that the same result
 * is achieved with different implementations and algorithms.
 */
public enum JGraphtAlgorithm {
    // org.jgrapht.alg.shortestpath.YenKShortestPath
    // Quote from the documentation in the source code: 
    //      "Implementation of Yen`s algorithm for finding $k$ shortest loopless paths."
    KShortestPathsYen,
    
    // org.jgrapht.alg.shortestpath.KShortestSimplePaths
    // Quote from the documentation in the source code: 
    //      "The algorithm is a variant of the Bellman-Ford algorithm ..."
    KShortestPathsBellmanFord;
    
    public static JGraphtAlgorithm getDefault() {
        return KShortestPathsYen;
    } 
}