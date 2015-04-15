getdns-java-bindings
======================
getdns java bindings. This is an early version with limited testing and validation. This has been tested on CentOS 6.3 64-bit.


External dependencies
=====================
This has been built and tested with Java 1.7

Currently building against the getdns 0.1.5 release.
getdns external dependencies include:
* [libldns from NLnet Labs](https://www.nlnetlabs.nl/projects/ldns/) version 1.6.17 or later (ldns requires ope
nssl headers and libraries)
* [libidn from the FSF](http://www.gnu.org/software/libidn/) version 1.28
* [libexpat](http://expat.sourceforge.net/) for libunbound.
* [libunbound from NLnet Labs](http://www.nlnetlabs.nl/projects/unbound/) version 1.4.22 or later
* [libevent](http://libevent.org) version 2.0.21 stable

Note that getdns **MUST** be built with the --with-libevent flag to configure.


Pre-configuration
======================
The file gradle.properties needs to be edited to customize javahome property

Building and test
======================
./gradlew clean assemble buildJniLib test

As per gradle standard libraries will be available in the folder build/libs.


Examples
======================
Examples can be found in the directory src/examples/java
Find usage of runExample with the below command
./gradlew --daemon runExample

Below  are the commands to run few examples

* ./gradlew --daemon runExample -Dexample=GetDNSIPSecure  -Dargs="['verisigninc.com']"
* ./gradlew --daemon runExample -Dexample=GetDNSGeneral  -Dargs="['icicibank.com','A']"

Known issues
======================
On some machines some test cases might fail because of timeout issues, we are working on this.
