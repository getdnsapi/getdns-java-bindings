#getdns-java-bindings
getdns java bindings. This is an early version with limited testing and validation. This has been tested on CentOS 6.3 64-bit.

##External dependencies
This has been built and tested with Java 1.7 and 1.8

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

The file gradle.properties needs to be edited to customize **java home** property

####Building and test
```
./gradlew clean assemble buildJniLib test
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

###Known issues
On some machines some test cases might fail because of timeout issues, we are working on this.
