import networkx as nx
from itertools import islice
from com.programmerare.shortestpaths.adaptee.jython_networkx import PathFinder

def k_shortest_paths(G, source, target, k, weight=None):
    # The implementation of this function "k_shortest_paths" is copied from the URL below:
    # https://networkx.github.io/documentation/stable/reference/algorithms/generated/networkx.algorithms.simple_paths.shortest_simple_paths.html?highlight=shortest_simple_paths#networkx.algorithms.simple_paths.shortest_simple_paths
    return list(islice(nx.shortest_simple_paths(G, source, target, weight=weight), k))

# The Python/Jython class below is implementing the Java interface "PathFinder"
# (src\main\java\com\programmerare\shortestpaths\adaptee\jython_networkx\PathFinder.java)     
class MyPathFinder(PathFinder):
    def __init__(self):
        self._graph = nx.DiGraph()
            
    def addGraphEdge(self, source, target, theWeight):
        self._graph.add_edge(source, target, weight=theWeight)
        
    def findShortestPaths(self, source, target, maxNumberOfPaths):
        return k_shortest_paths(self._graph, source, target, maxNumberOfPaths, "weight")    

