kevoree-watchdog
================

Kevoree Watchdog : Software to manage permanent Kevoree Runtime

The Kevoree bootstrap service aims at deploying, installing, starting and monitor a kevoree runtime. This bootstrap acts as a watchdog which monitor the underlying kevoree runtime. If no message are receive during 3000 ms the kevoree child runtime is restarted.

### Using the bootstraper in the console

		wget https://oss.sonatype.org/content/repositories/snapshots/org/kevoree/bootstrap/org.kevoree.bootstrap.service/1.0-SNAPSHOT/org.kevoree.bootstrap.service-1.0-20130517.235756-4.deb
		
		java -jar org.kevoree.bootstrap.service-1.0-20130517.232717-2.jar <kevoree.version> <bootmodel>

### Install Kevoree bootstrap and startup script (Debian and Ubuntu)

Simply donwload the last .deb version of kevoree bootstraper 

	wget https://oss.sonatype.org/content/repositories/snapshots/org/kevoree/bootstrap/org.kevoree.bootstrap.service/1.0-SNAPSHOT/org.kevoree.bootstrap.service-1.0-20130517.235756-4.deb
	sudo dpkg -i sudo dpkg -i org.kevoree.bootstrap*.deb
	
### Configuration is located in /etc/kevoree/config file

Default content : 

	KEVOREE_VERSION=2.0.0-SNAPSHOT
	
Change the KEVOREE_VERSION to the right version 

### Start Kevoree service 

	sudo service kevoree start
	
### Monitor the log file 

	tail -f -n 200 /var/log/kevoree/out

### Get status (PID) of current Kevoree Runtime

	sudo service kevoree status
	
### Stop Kevoree runtime service (and the child kevoree process)

	sudo service kevoree stop

### Uninstall Kevoree bootstrap (Debian and Ubuntu)

To remove the kevoree runtime and the startup script you should use the dpkg command using the kevoree package name kevoree.bootstrap , simply as follow :

	sudo dpkg -r kevoree.bootstrap 