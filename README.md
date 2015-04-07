#getdns-java-bindings
getdns java bindings. This is an early version with limited testing and validation. This has been tested on CentOS 6.3 64-bit.

##External dependencies
This has been built and tested with Java 1.7 and 1.8

Currently building against the getdns 0.1.6 release.
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
```
./gradlew --daemon runExample -Dexample=GetDNSGeneral  -Dargs="['getdnsapi.net','A']"

./gradlew --daemon runExample -Dexample=GetDNSWithDNSSECStatusExtension  -Dargs="['verisigninc.com','A']"

./gradlew --daemon runExample -Dexample=GetDNSWithDNSSECValidationChainExtension  -Dargs="['verisigninc.com','A']"

./gradlew --daemon runExample -Dexample=GetDNSWithReturnBothV4andV6Extension  -Dargs="['verisigninc.com','A']"

./gradlew --daemon runExample -Dexample=DaneCertVerification  -Dargs="['getdnsapi.net', 443]"

./gradlew --daemon runExample -Dexample=GetDNSIP -Dargs="['getdnsapi.net']"

./gradlew --daemon runExample  -Dexample=GetDNSGeneralForMX  -Dargs="['verisign.com']"

./gradlew --daemon runExample -Dexample=GetDNSCustomRRType -Dargs="['62d4a9f862867f38a82da911d4747490d560989536b53d2c51e0e336._sign._smimecert.gmadkat.com','TYPE65514']"


```

###Known issues
On some machines some test cases might fail because of timeout issues, we are working on this.
