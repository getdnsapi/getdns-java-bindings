/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_verisign_getdns_GetDNSContext */

#ifndef _Included_com_verisign_getdns_GetDNSContext
#define _Included_com_verisign_getdns_GetDNSContext
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    contextCreate
 * Signature: (Ljava/lang/Object;I)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_contextCreate(
		JNIEnv *, jobject, jobject, jint);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    contextDestroy
 * Signature: (Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_contextDestroy
(JNIEnv *, jobject, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    createEventBase
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_createEventBase(
		JNIEnv *, jclass);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    startListening
 * Signature: (Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_startListening
(JNIEnv *, jclass, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    generalSync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;ILjava/util/HashMap;)Ljava/util/HashMap;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_generalSync(
		JNIEnv *, jobject, jobject, jstring, jint, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    generalAsync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;ILjava/util/HashMap;Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_generalAsync(
		JNIEnv *, jobject, jobject, jstring, jint, jobject, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    addressSync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;Ljava/util/HashMap;)Ljava/util/HashMap;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_addressSync(
		JNIEnv *, jobject, jobject, jstring, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    serviceSync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;Ljava/util/HashMap;)Ljava/util/HashMap;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_serviceSync(
		JNIEnv *, jobject, jobject, jstring, jobject);
/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    hostnameSync
 * Signature: (Ljava/lang/Object;Ljava/util/HashMap;Ljava/util/HashMap;)Ljava/util/HashMap;
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_hostnamesync(
		JNIEnv *, jobject, jobject, jstring, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    addressASync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;Ljava/util/HashMap;Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_addressAsync(
		JNIEnv *, jobject, jobject, jstring, jobject, jobject);
/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    serviceASync
 * Signature: (Ljava/lang/Object;Ljava/lang/String;Ljava/util/HashMap;Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_serviceAsync(
		JNIEnv *, jobject, jobject, jstring, jobject, jobject);
/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    hostnameASync
 * Signature: (Ljava/lang/Object;Ljava/util/HashMap;Ljava/util/HashMap;Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_hostnameAsync(
		JNIEnv *, jobject, jobject, jstring, jobject, jobject);

JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_cancelRequest(
                JNIEnv *, jobject, jobject, jlong);
/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    applyContextOption
 * Signature: (Ljava/lang/String;Ljava/lang/Object;)Z
 */
JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_applyContextOption
(JNIEnv *, jobject, jobject, jstring, jobject);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    ConvertUnicodeToAscii
 * Signature: (Ljava/lang/String)Z
 */
JNIEXPORT jstring JNICALL Java_com_verisign_getdns_GetDNSContext_ConvertUnicodeToAscii(
		JNIEnv *, jobject, jstring);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    ConvertAsciiToUnicode
 * Signature: (Ljava/lang/String)Z
 */
JNIEXPORT jstring JNICALL Java_com_verisign_getdns_GetDNSContext_ConvertAsciiToUnicode(
		JNIEnv *, jobject, jstring);

/*
 * Class:     com_verisign_getdns_GetDNSContext
 * Method:    ConvertAsciiToUnicode
 * Signature: (Ljava/lang/String)Z
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_GetDnsRootTrustAnchor(
		JNIEnv *, jobject);


#ifdef __cplusplus
}
#endif
#endif
