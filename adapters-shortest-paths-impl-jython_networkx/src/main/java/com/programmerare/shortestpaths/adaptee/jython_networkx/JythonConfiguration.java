package com.programmerare.shortestpaths.adaptee.jython_networkx;

import org.python.util.PythonInterpreter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public class JythonConfiguration {
    private final Logger logger = LoggerFactory.getLogger(JythonConfiguration.class);

    
    private static JythonConfiguration _jythonConfiguration = new JythonConfiguration();

    public static JythonConfiguration getInstance() {
        return _jythonConfiguration;
    }

    // uppercased versions of these below strings (potential environment variable names) will also be iterated when trying to find a jython installation directory
    private final List<String> _lowerCasedNamesWithPotentialEnvinronmentVariablesForJythonHomeDirectory = Arrays.asList(
        "jython.home", "jython_home", "jythonhome",
        "jython.path", "jython_path", "jythonpath",
        "jython"
    );
    
    private final Map<String, String> jythonEnvironmentVariables = new HashMap<String, String>();
    private String _firstFoundJythonKey = null;
    private String _jythonPathToUse = null;

    // implementation 'org.python:jython-standalone:2.7.2'
    private final String _versionOfJythonAtTheClassPath; // should become "2.7.2" if you have defined a gradle dependency as above 

    private File _existingJythonJarFile = null;
    private String _versionForExistingJythonJarFile = null;
    
    
    private JythonConfiguration() {
        _versionOfJythonAtTheClassPath = getVersionOfJythonAtTheClassPath();
//        print("_versionOfJythonAtTheClassPath " + _versionOfJythonAtTheClassPath);
        boolean result = false;

        final ArrayList<String> namesWithPotentialEnvinronmentVariablesForJythonHomeDirectory = new ArrayList<String>(_lowerCasedNamesWithPotentialEnvinronmentVariablesForJythonHomeDirectory);

        // Java 8 code:
        //namesWithPotentialEnvinronmentVariablesForJythonHomeDirectory.addAll(
            // add upper cased versions of the strings
            //_lowerCasedNamesWithPotentialEnvinronmentVariablesForJythonHomeDirectory.stream().map(s -> s.toUpperCase()).collect(Collectors.toList())
        //);
        // use the code above instead of the code below if/when Java 8 is used instead of Java 6
        // Java 6 code:
        for(String lowerCasedName: _lowerCasedNamesWithPotentialEnvinronmentVariablesForJythonHomeDirectory) {
            namesWithPotentialEnvinronmentVariablesForJythonHomeDirectory.add(lowerCasedName.toUpperCase());
        }

        for (String env : namesWithPotentialEnvinronmentVariablesForJythonHomeDirectory) {
            // print("iterated environment variable: " + env);
            result = setNameOfEnvironmentVariableDefiningTheJythonHomeDirectory(env);
            if(result) {
                break;
            }
        }
        if(!result) {
            iterateEnvironmentVariablesAndPopulateMapWithPotentialJythonVariables();
        }
    }

    private void iterateEnvironmentVariablesAndPopulateMapWithPotentialJythonVariables() {
        final Map<String, String> getenv = System.getenv();
        final Set<Map.Entry<String, String>> entries = getenv.entrySet();
//        print("number of environments variable to iterate: " + entries.size());
        final Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()) {
            final Map.Entry<String, String> next = iterator.next();
            final String key = next.getKey();
            final String keyLowerCased = key.toLowerCase();
            final String value = next.getValue();
            //print("iterated env key: " + keyLowerCased);
            //print("iterated env value: " + value);
            if(keyLowerCased.contains("jython")) {
                if(_firstFoundJythonKey == null) {
                    _firstFoundJythonKey = key;
                    print("iterated env key: " + keyLowerCased);
                    print("iterated env value: " + value);
                    setJythonPathToUse(value);
                }
                jythonEnvironmentVariables.put(key, value);
            }
        }
//        if(_jythonPathToUse == null) {
//            print("No jython path found");
//        }
//        else {
//            print("jython path to use: " + _jythonPathToUse);
//        }
    }

    private String getVersionOfJythonAtTheClassPath() {
        final Class<PythonInterpreter> pythonInterpreterClass = PythonInterpreter.class;
        final Package aPackage = pythonInterpreterClass.getPackage();
        
        final String implementationVersion = aPackage.getImplementationVersion();
        // print("implementationVersion " + implementationVersion);        
        if(implementationVersion != null && !implementationVersion.trim().equals("")) {
            return implementationVersion;
        }
        
        final String specificationVersion = aPackage.getSpecificationVersion();
        // print("specificationVersion " + specificationVersion);        
        if(specificationVersion != null && !specificationVersion.trim().equals("")) {
            return specificationVersion;
        }
        
        final String externalForm = pythonInterpreterClass.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
//        print("externalForm: " + externalForm);
        String filePathWithoutFileColonPrefix = externalForm.replace("file:", "");
        File f = new File(filePathWithoutFileColonPrefix);
//        print("file path: " + f.getAbsolutePath());
        final String jythonJarFileVersion = getJythonVersionFromJarfileManifest(f);
//        print("jythonJarFileVersion: " + jythonJarFileVersion);
        return jythonJarFileVersion;        
    }

    private String getJythonVersionFromJarfileManifest(final File jythonJarFile) {
        String versionAccordingToJarFileManifest = null;
        try {
            final JarFile jarFile = new JarFile(jythonJarFile);
            final Manifest manifest = jarFile.getManifest();
            final Map<String, Attributes> entries = manifest.getEntries();
            final Iterator<Map.Entry<String, Attributes>> iterator = entries.entrySet().iterator();
            while(iterator.hasNext()) {
                final Map.Entry<String, Attributes> next = iterator.next();
                final String key = next.getKey();
                final String keyLowerCased = key.toLowerCase();
                //if(keyLowerCased.contains("version")) {
                    //print("value " + value);
                    final Attributes attributes = next.getValue();
                    final Iterator<Map.Entry<Object, Object>> iterator1 = attributes.entrySet().iterator();
                    while(iterator1.hasNext()) {
                        final Map.Entry<Object, Object> next1 = iterator1.next();
                        final String key1 = next1.getKey().toString().toLowerCase();
                        final String value1 = next1.getValue().toString();
                        //                        print("key1 " + key1);
                        //                        print("value1 " + value1);
                        if(key1 != null && key1.equals("version") && value1 != null) {
                            //    Manifest-Version: 1.0
                            //    Ant-Version: Apache Ant 1.9.14
                            //    Created-By: 1.8.0_241-b07 (Oracle Corporation)
                            //    Main-Class: org.python.util.jython
                            //    Built-By: Jeff
                            //    Implementation-Vendor: Python Software Foundation
                            //    Implementation-Title: Jython fat jar
                            //    Implementation-Version: 2.7.2
                            //
                            //    Name: Build-Info
                            //    version: 2.7.2
                            //    oracle: true
                            //    informix: true
                            //    build-compiler: modern
                            //    jdk-target-version: 1.8
                            //    debug: true
                            //
                            //_versionForExistingJythonJarFile = value1; // e.g. "2.7.2"
                            versionAccordingToJarFileManifest = (value1 == null) ? "" : value1.trim();
                        }
                    }
//                }
//                else {
//                    print("other key , not version: " + key);
//                }
            }
        } catch (IOException e) {
            print("problem " + e.toString());
            e.printStackTrace();
        }
        if(versionAccordingToJarFileManifest != null && !versionAccordingToJarFileManifest.trim().equals("")) {
            return versionAccordingToJarFileManifest;
        }
        return "";
    }
    
    private boolean setJythonPathToUse(String jythonPathToUse) {
        final File pythonDirectory = new File(jythonPathToUse);
        if(!pythonDirectory.exists() || !pythonDirectory.isDirectory()) {
            printWarningMessage("jython home directory does not exist: " + pythonDirectory.getAbsolutePath());
            return false;
        }
        return setExistingJythonJarFile(new File(pythonDirectory,"jython.jar"));
    }

    private boolean setExistingJythonJarFile(final File jythonJarFile)  {
        if((jythonJarFile.exists() && jythonJarFile.isFile())) {
            _existingJythonJarFile = jythonJarFile;
            _jythonPathToUse = _existingJythonJarFile.getParentFile().getAbsolutePath();
            //print("setExistingJythonJarFile _jythonPathToUse " + _jythonPathToUse);
            final String versionOfJythonJarFile = getJythonVersionFromJarfileManifest(_existingJythonJarFile);
            if(!versionOfJythonJarFile.equals("")) {
                _versionForExistingJythonJarFile = versionOfJythonJarFile;
//                print("_versionForExistingJythonJarFile " + _versionForExistingJythonJarFile);
            }
            //print("jython.jar found at the path: " + existingJythonJarFile.getAbsolutePath());
//            print("version for the file jython.jar : " + versionForJarFile);
            //print("_versionOfJythonAtTheClassPath " + _versionOfJythonAtTheClassPath);
            if(_versionOfJythonAtTheClassPath != null && _versionOfJythonAtTheClassPath.equals(_versionForExistingJythonJarFile)) {
//                print("Same version of jython i.e. " + _versionForExistingJythonJarFile);
            }
            else {
                printWarningMessage("different Jython versions detected: " + _versionOfJythonAtTheClassPath + " vs " + _versionForExistingJythonJarFile);
            }
            return true;
        }
        else {
            printWarningMessage("File does not exist: " + jythonJarFile.getAbsolutePath());
            return false;
        }
    }

    public String getJythonHomeDirectory() {
        return _jythonPathToUse;
    }

    private File getJythonHomeSubDirectoryWithLibSitePackages() {
        String jythonHomeDirectory = getJythonHomeDirectory();
        if(jythonHomeDirectory == null) {
            final String errorMessage = "jython HomeDirectory is unknown";
            logger.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        final File dir = new File(getJythonHomeDirectory());
        if(!dir.exists()) {
            printWarningMessage("Jython directory does not exist: "  + dir.getAbsolutePath());
        }
        final File subDirWithSitePackages = new File(new File(dir, "Lib"), "site-packages");
        if(!subDirWithSitePackages.exists()) {
            printWarningMessage("Directory does not exist: "  + subDirWithSitePackages.getAbsolutePath());
        }        
        return subDirWithSitePackages;
    }

    public String getJythonHomeSubDirectoryWithLibSitePackagesWithForwardSlashes() {
        final File f = getJythonHomeSubDirectoryWithLibSitePackages();
        return f.getAbsolutePath().replace('\\', '/');
    }
    
    public boolean setJythonHomeDirectory(final String jythonHomeDirectory) {
        return setJythonPathToUse(jythonHomeDirectory);
    }

    public boolean setNameOfEnvironmentVariableDefiningTheJythonHomeDirectory(String nameOfEnvironmentVariableDefiningTheJythonHomeDirectory)  {
        final String theJythonHomeDirectory = System.getenv(nameOfEnvironmentVariableDefiningTheJythonHomeDirectory);
        if(theJythonHomeDirectory == null) {
            printWarningMessage("no Jython home directory was found with environment variable " + nameOfEnvironmentVariableDefiningTheJythonHomeDirectory);
            return false;
        }
        else {
            print("Jython home directory was found with environment variable " + nameOfEnvironmentVariableDefiningTheJythonHomeDirectory + " : " + theJythonHomeDirectory);
            return setJythonHomeDirectory(theJythonHomeDirectory);
        }
    }

    public void showWarningIfSubDirectorySeemsToBeMissing(
        final String namePartOfSubDirectoryToLookFor,
        final String commandToSuggestIfDirectorySeemsToBeMissing
    ) {
        final File dir = getJythonHomeSubDirectoryWithLibSitePackages();
        final File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains(namePartOfSubDirectoryToLookFor);
            }
        });
        if(files == null || files.length == 0) {
            printWarningMessage("No directory found with the name part " + namePartOfSubDirectoryToLookFor + " in directory " + dir.getAbsolutePath() + " , Maybe you should try to install with this command: " + commandToSuggestIfDirectorySeemsToBeMissing);
        }
        else {
            for (File file : files) {
                print("Found directory with '" + namePartOfSubDirectoryToLookFor + "' : " + file.getAbsolutePath());    
            }
        }
    }

    private void printWarningMessage(String warningMessage) {
        logger.warn("WARNING: " + warningMessage);
    }
    private void print(String text) {
        logger.info(text);
    }    
}
