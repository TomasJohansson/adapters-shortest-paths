# Prerequisites for the implementation "adapters-shortest-paths-impl-jython_networkx"

[Jython](https://www.jython.org) must be installed, and also the Python library [NetworkX](https://networkx.github.io/).   
  
Download Jython from https://www.jython.org/download  
Currently (october 2020) the latest version is 2.7.2.
 
You can download the file "Jython Installer" (jython-installer-2.7.2.jar) and then run the following command:
```shell
java -jar jython-installer-2.7.2.jar
```  
(and I have tested the above installer with Windows 10 as well as Linux Ubuntu 20.04)  
The above command starts a graphical installer, and I used "Standard" installation and selected an installation directory ($HOME/jython/jython2.7.2 , which I then used in the file ".profile" as described below).  

Then you must have defined some environment variable containing the string "jython" (and it does not matter if you use lowercase or uppercase) for example "Jython.Path".  
At my Ubuntu machine installation I used "JYTHON_HOME" and therefore added these lines in the file ".profile" (in my 'home' directory):  
export JYTHON_HOME=$HOME/jython/jython2.7.2  
export PATH=$PATH:JYTHON_HOME/bin  

After adding the above to the ".profile" file you can [reload it with Ubuntu](https://askubuntu.com/questions/59126/reload-bashs-profile-without-logging-out-and-back-in-again) by typing ". ~/.profile".   
Verify your Jython installation with this command:  
```shell
jython --version
```  
The result from the above command should be the output "Jython 2.7.2" if you downloaded that version.  

You should then be able to install the NetworkX library with this command:  
```shell
jython -m pip install networkx
```
If the installation was succesful you should now have a directory $HOME/jython/jython2.7.2/Lib/site-packages/networkx  
(assuming you installed Jython to $HOME/jython)