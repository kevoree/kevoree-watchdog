kevoree-watchdog
================

Kevoree Watchdog : Software ensures the continuity of service of a Kevoree Runtime

The Kevoree Watchdog is a service that downloads (if necessary), installs, starts and monitors a Kevoree Runtime. 
This watchdog service monitors the Kevoree Runtime by regularly sending ping messages.
If no answer is received within 3000 ms, the watchdog restarts the runtime.

### Using the Watchdog in the console
The Kevoree Watchdog service has two parameters
+ **kevoree.version** (mandatory) is the version number of the runtime you want to launch (e.g.: 2.0.0, 1.9.0-SNAPSHOT).
+ **bootmodel** (optional) is a Kevoree model (.kev) or a KevScript (.kevs) that will be passed on to the launched runtime at startup.

```bash
wget http://oss.sonatype.org/content/repositories/releases/org/kevoree/watchdog/org.kevoree.watchdog/0.8/org.kevoree.watchdog-0.8.jar
java -jar org.kevoree.watchdog-0.8.jar <kevoree.version> <bootmodel>
```
[Download the Watchdog directly](https://oss.sonatype.org/content/repositories/releases/org/kevoree/watchdog/org.kevoree.watchdog/0.8/org.kevoree.watchdog-0.8.jar) 

##### Additional system properties
Kevoree watchdog also support dedicated system properties to configure standard output and error stream. The following option which must appears **BEFORE** the -jar option, and allows to redirect standard and error stream to a file

	-Dlog.out=path_to_file
	
In addition the following option, aims at redirect the error stream to a file.

	-Dlog.err=path_to_file
	
If this option is not explicitly declared, but the log.out is declared, by default Kevoree Watchdig redirect **BOTH** error and standard output to the same file declared with log.out option.

Finelly, the full watchdig startup can be done throught:

	java -Dlog.out=path_to_file -jar org.kevoree.watchdog-0.8.jar <kevoree.version> <bootmodel>


### Install Kevoree Watchdog as a Linux service (Debian and Ubuntu)

Simply download the last .deb version of Kevoree Watchdog 

```bash
wget http://oss.sonatype.org/content/repositories/releases/org/kevoree/watchdog/org.kevoree.watchdog/0.8/org.kevoree.watchdog-0.8.deb
sudo dpkg -i org.kevoree.bootstrap*.deb
```
[Download the Watchdog directly](https://oss.sonatype.org/content/repositories/releases/org/kevoree/watchdog/org.kevoree.watchdog/0.8/org.kevoree.watchdog-0.8.deb) 
The service is automatically registered to start as a service when the system starts.

### Configuring the Linux service in /etc/kevoree/config

Default content : 

	KEVOREE_VERSION=2.0.0-SNAPSHOT
	NODE_NAME=$(hostname)_$(id -un)
	PING_PORT=9999
	PING_TIMEOUT=3000
	
+ **KEVOREE_VERSION** Sets the version of the runtime to be managed (downloaded, started, monitored).
+ **NODE_NAME** Name of the Kevoree node. Initialy set to the host name plus user name.
+ **PING_PORT** Is the tcp port used ping the runtime. Set it to a free port.
+ **PING_TIMEOUT** Sets the max no-response time before rebooting the managed runtime.

##### Bootstrap model 
by default the service lookup for a bootstrap model (XMI or KevScript) in the following file 

	/etc/kevoree/bootmodel
	
If this model is empty, the runtime create one (as in standalone mode). In short place your bootstrap model and the informations relative to your node name at this place.

### Start Kevoree service 

	sudo service kevoree start
	
### Monitor the log file 

	tail -f -n 200 /var/log/kevoree/kevoree.log

### Get status (PID) of current Kevoree Runtime

	sudo service kevoree status
	
### Stop Kevoree runtime service (and the child kevoree process)

	sudo service kevoree stop

### Uninstall Kevoree bootstrap (Debian and Ubuntu)

To remove the kevoree runtime and the startup script you should use the dpkg command using the kevoree package name kevoree.bootstrap, simply as follow :

	sudo dpkg -r kevoree.watchdog 
