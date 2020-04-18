package adapters.shortest.paths.impl.jython_networkx;

import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.core.PyInteger;
//import org.python.core.PyList;
//import java.util.Arrays;
//import java.util.List;

public class PathFinderFactory {
    private PyObject classObject;

    public PathFinderFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from pathfinder import MyPathFinder"); // resources\pathfinder.py
        classObject = interpreter.get("MyPathFinder");
    }

    // https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/
    
    public PathFinder create() {
        PyObject accumulator = classObject.__call__();
        return (PathFinder) accumulator.__tojava__(PathFinder.class);
    }    
}
