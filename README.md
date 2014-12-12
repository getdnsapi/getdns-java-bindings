GetDNS-Java
======================
GetDNS java bindings. This is a very early version with very limited testing and validation.

Pre-configuration
======================
The file gradle.properties needs to be edited to customize javahome property

Building and test
======================
./gradlew clean assemble buildJniLib test

Examples
======================
Examples can be found in the directory src/examples/java
Find usage of runExample with the below command
./gradlew --daemon runExample

Below  are the commands to run few examples
./gradlew --daemon runExample -Dexample=GetDNSIPSecure  -Dargs="['icicibank.com']"
./gradlew --daemon runExample -Dexample=GetDNSGeneral  -Dargs="['icicibank.com','A']"

