import networkx as nx
from itertools import islice
from adapters.shortest.paths.impl.jython_networkx import PathFinder

def k_shortest_paths(G, source, target, k, weight=None):
    # https://networkx.github.io/documentation/stable/reference/algorithms/generated/networkx.algorithms.simple_paths.shortest_simple_paths.html?highlight=shortest_simple_paths#networkx.algorithms.simple_paths.shortest_simple_paths
    return list(islice(nx.shortest_simple_paths(G, source, target, weight=weight), k))
    
class MyPathFinder(PathFinder):
    def __init__(self):
        self._graph = nx.DiGraph()
            
    def addGraphEdge(self, source, target, theWeight):
        self._graph.add_edge(source, target, weight=theWeight)
        
    def findShortestPaths(self, source, target, maxNumberOfPaths):
        return k_shortest_paths(self._graph, source, target, maxNumberOfPaths, "weight")    
 
