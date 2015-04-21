#getdns Sample Java Web Application (war)

Built using getdns-java-bindings

##External Dependencies

getdns-java-bindings

##Building

./gradlew clean war


##Configuring Tomcat and Deploying Application

1. Ensure that you have downloaded getdns-java-bindings and built it(please verify build using provided examples).

2. getdns-java-bindings provides JNI interface with getdns, through shared library "libgetdnsconnector.so", this is found in home folder getdns-java-bindings/build/libs folder. Copy "libgetdnsconnector.so" file to a folder where you have installed other dependency libraries(e.g. getdns, event etc are generally in linux sysem installed in /usr/local/lib).

3. Update apache-tomcat/bin/catalina.sh file to add path of folder containing "libgetdnsconnector.so" to library path (e.g. **JAVA_OPTS="$JAVA_OPTS -Djava.library.path=/usr/local/lib"**)

4. Add path for folder containg other shared library dependency (e.g. getdns, unbound etc.). For CentOS and Ubuntu linux this can be done by creating a file .conf file (e.g. getdns.conf) in " /etc/ld.so.conf.d" folder add path of shared library folder(s) one per line, save this file.

5. Deploy the war file in apache-tomcat/webapps folder.

##Built and Tested with

Ubuntu 14.04 LTS
java version "1.7.0_51"
Tomcat 7x

##Known issues
Hot deployment causes classloading error, this forces restart.
Setting java library path may cause some performance issue.

