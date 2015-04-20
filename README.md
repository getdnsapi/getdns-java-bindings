#getdns-java-bindings
getdns Java bindings is an implementation of Java language bindings for the [getdns API](https://getdnsapi.net/spec.html), built on top of the [getdns implementation](https://github.com/getdnsapi/getdns/). getdns is a modern, asynchronous DNS API that simplifies access to advanced DNS features, including DNSSEC. The API specification was developed by Paul Hoffman. getdns is built on top of the getdns implementation developed as a joint project between [Verisign Labs](http://labs.verisigninc.com/en_US/innovation/verisign-labs/index.xhtml) and [NLnet Labs](http://nlnetlabs.nl/).

##External dependencies
This has been built and tested with JDK 1.7 and 1.8

Currently building against the getdns 0.1.7 release.
getdns external dependencies include:

*   [libldns from NLnet Labs](https://www.nlnetlabs.nl/projects/ldns/) version 1.6.17 or later (ldns requires ope
nssl headers and libraries)
*   [libidn from the FSF](http://www.gnu.org/software/libidn/) version 1.29
*   [libexpat](http://expat.sourceforge.net/) for libunbound.
*   [libunbound from NLnet Labs](http://www.nlnetlabs.nl/projects/unbound/) version 1.4.22 or later
* [libevent](http://libevent.org) version 2.0.22 stable

Note that getdns **MUST** be built with the --with-libevent flag to configure.


##Pre-configuration

The file gradle.properties needs to be edited to customize **javahome** property pointing to JDK_HOME so that gradle can find jni header files under JDK_HOME/include directory.

####Build, install and verification
```
./gradlew --daemon clean assemble buildJniLib
sudo ./gradlew --daemon installJniLib
./gradlew --daemon runExample -Dexample=GetDNSGeneralSync
```
As per gradle standard libraries will be available in the folder build/libs.

##Examples
Examples can be found in the directory *src/examples/java*
#####Find usage of runExample with the below command.
```
./gradlew --daemon runExample
```
#####Below  are the commands to run few examples
######Run examples with **Gradle**
```
./gradlew --daemon runExample -Dexample=GetDNSGeneralSync

./gradlew --daemon runExample -Dexample=GetDNSWithDNSSECStatusExtension

```
######Run examples with **Java**
```
javac -cp ./build/libs/getdns-java-0.1.jar src/examples/java/com/verisign/getdns/example/sync/GetDNSGeneralSync.java -d .

LD_LIBRARY_PATH=/usr/local/lib java -cp ./build/libs/getdns-java-0.1.jar:. -Djava.library.path=build/libs com.verisign.getdns.example.sync.GetDNSGeneralSync

```
#####Documentation 
Javadoc for getdns-java binding can be found [here](http://getdns-java.github.io/)

###Known issues
On some machines some test cases might fail because of timeout issues, we are working on this.
