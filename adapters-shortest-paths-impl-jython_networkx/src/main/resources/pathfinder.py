from adapters.shortest.paths.impl.jython_networkx import PathFinder

class MyPathFinder(PathFinder):
    
    def findShortestPaths(self, startVertex, endVertex, maxNumberOfPaths):
        return [
           ["a", "b", "d"],
           ["a", "c", "d"]
        ] 
