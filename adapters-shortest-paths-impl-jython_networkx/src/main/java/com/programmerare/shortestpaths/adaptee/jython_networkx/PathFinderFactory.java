package com.programmerare.shortestpaths.adaptee.jython_networkx;

import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
//import org.python.core.PyString;
//import org.python.core.PyInteger;
//import org.python.core.PyList;
//import java.util.Arrays;
//import java.util.List;

public class PathFinderFactory {
    private final Logger logger = LoggerFactory.getLogger(PathFinderFactory.class);
    
    private final PyObject classObject;
    private final TimeMeasurer _timeMeasurer;

    private static volatile PathFinderFactory _pathFinderFactory = null;

    public static PathFinderFactory getInstance() {
        if(_pathFinderFactory == null) {
            // https://en.wikipedia.org/wiki/Singleton_pattern
            // https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java            
            synchronized(PathFinderFactory.class) {
                if (_pathFinderFactory == null) {
                    _pathFinderFactory = new PathFinderFactory(); // VERY VERY SLOW instantiation
                }
            }            
        }
        return _pathFinderFactory;
    }

    public TimeMeasurer getTimeMeasurer() {
        return _timeMeasurer;
    }

    /**
     * VERY VERY SLOW instantiation !
     * (but the private constructor is used to create a Singleton instance !)
     */
    private PathFinderFactory() {
        _timeMeasurer = TimeMeasurer.start();
        _timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("Starting the slow creation of the implementation based on python networkx. " + new Date());
//time("Before JythonConfiguration.getInstance()");
        final JythonConfiguration jythonConfiguration = JythonConfiguration.getInstance();
//time("After JythonConfiguration.getInstance()");
        //jythonConfiguration.setNameOfEnvironmentVariableDefiningTheJythonHomeDirectory("JYTHON");
        final String sysPathWithLibSitePackages = jythonConfiguration.getJythonHomeSubDirectoryWithLibSitePackagesWithForwardSlashes();
//time("After jythonConfiguration.getJythonHomeSubDirectoryWithLibSitePackagesWithForwardSlashes()");
        // System.out.println("sysPathWithLibSitePackages " + sysPathWithLibSitePackages);
        jythonConfiguration.showWarningIfSubDirectorySeemsToBeMissing("networkx", "jython -m pip install networkx");
//time("After jythonConfiguration.showWarningIfSubDirectorySeemsToBeMissing"); // 0 seconds since start
        // SLOW:
        PythonInterpreter interpreter = new PythonInterpreter(); // about 12 seconds for this constructor !
//time("After new PythonInterpreter()"); // 12 seconds since start
        interpreter.exec("import sys; sys.path.append('" + sysPathWithLibSitePackages + "')");
//time("After interpreter.exec import sys; sys.path.append"); // 12 seconds since start
        // VERY VERY SLOW:        
        interpreter.exec("from pathfinder import MyPathFinder"); // resources\pathfinder.py // about 45 seconds ! 
//time("After interpreter.exec from pathfinder import MyPathFinder"); // 57 seconds since start
        classObject = interpreter.get("MyPathFinder");
//time("After interpreter.get MyPathFinder ");
        _timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart("The slow creation of the implementation based on python networkx is now finished. " + new Date());
    }

    // https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/

    public PathFinder createInstance() {
        final PyObject pathFinder = classObject.__call__();
        return (PathFinder) pathFinder.__tojava__(PathFinder.class);
    }
    
    
    private void time(String messageSuffix) {
        _timeMeasurer.printMessageIncludingNumberOfSecondsSinceStart(messageSuffix);
    }    
    private void print(String s) {
        logger.info(s);
    }
}
