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
    // Quote from the documentation in the jgrapht source code: 
    //      "The algorithm is a variant of the Bellman-Ford algorithm ..."
    //      ( https://github.com/jgrapht/jgrapht/blob/master/jgrapht-core/src/main/java/org/jgrapht/alg/shortestpath/KShortestSimplePaths.java )
    // However, it is still considered as controversial (according to jgrapht developers) to attribute the algorithm to Bellman/Ford
    // and for that reason the suffix "Inspired" has been added for this enum value specifying this jgrapht algorithm implementation.
    // Some discussion about using the name BellmanFord can be seen in the following pull request:
    // https://github.com/jgrapht/jgrapht/pull/813
    KShortestPathsBellmanFordInspired;
    
    public static JGraphtAlgorithm getDefault() {
        return KShortestPathsYen;
    } 
}
