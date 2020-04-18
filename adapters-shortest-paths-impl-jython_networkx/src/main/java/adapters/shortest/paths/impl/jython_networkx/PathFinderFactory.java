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
    private final TimeMeasurer _timeMeasurer;

    private void time(String messageSuffix) {
        _timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart(messageSuffix);
    }
    
    public PathFinderFactory(final TimeMeasurer timeMeasurer) {
        _timeMeasurer = timeMeasurer;
time("Before JythonConfiguration.getInstance()");
        final JythonConfiguration jythonConfiguration = JythonConfiguration.getInstance();
time("After JythonConfiguration.getInstance()");
        //jythonConfiguration.setNameOfEnvironmentVariableDefiningTheJythonHomeDirectory("JYTHON");
        final String sysPathWithLibSitePackages = jythonConfiguration.getJythonHomeSubDirectoryWithLibSitePackagesWithForwardSlashes();
time("After jythonConfiguration.getJythonHomeSubDirectoryWithLibSitePackagesWithForwardSlashes()");
        // System.out.println("sysPathWithLibSitePackages " + sysPathWithLibSitePackages);
        jythonConfiguration.showWarningIfSubDirectorySeemsToBeMissing("networkx", "jython -m pip install networkx");
time("After jythonConfiguration.showWarningIfSubDirectorySeemsToBeMissing"); // 0 seconds since start
        // SLOW:
        PythonInterpreter interpreter = new PythonInterpreter(); // about 12 seconds for this constructor !
time("After new PythonInterpreter()"); // 12 seconds since start
        interpreter.exec("import sys; sys.path.append('" + sysPathWithLibSitePackages + "')");
time("After interpreter.exec import sys; sys.path.append"); // 12 seconds since start
        // VERY VERY SLOW:        
        interpreter.exec("from pathfinder import MyPathFinder"); // resources\pathfinder.py // about 45 seconds ! 
time("After interpreter.exec from pathfinder import MyPathFinder"); // 57 seconds since start
        classObject = interpreter.get("MyPathFinder");
time("After interpreter.get MyPathFinder ");
    }

    // https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/
    
    public PathFinder createInstance() {
        final PyObject pathFinder = classObject.__call__();
        return (PathFinder) pathFinder.__tojava__(PathFinder.class);
    }    
}
