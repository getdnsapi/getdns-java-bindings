#include <stdio.h>
#include <jni.h>
#include <stdlib.h>
#include "getdns/getdns.h"

#define CHECK_NULL_INIT_PTR(javaObj, context) if(NULL != javaObj) context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, javaObj);
#define CHECK_NULL_AND_INIT_STR(str, nativeStr) if(NULL != str) nativeStr = (*env)->GetStringUTFChars(env, str, 0);


struct util_methods {
    jclass    listClass;
    jmethodID listInit;
    jmethodID listAdd;
    jclass    mapClass;
    jmethodID mapInit;
    jmethodID mapPut;
    jmethodID mapGetEntrySet;
    jclass    setClass;
    jmethodID entrySetGetIterator;
    jclass    iteratorClass;
    jmethodID hasNext;
    jmethodID next;
    jclass    entryClass;
    jmethodID entryGetKey;
    jmethodID entryGetValue;
    jclass    integerClass;
    jmethodID integerInit;
    jmethodID intValue;
    jclass    callbackClass;
    jmethodID callbackHandleResponse;
};

struct util_methods*
init_util_methods(JNIEnv* env, struct util_methods*);

int
throwExceptionOnError(JNIEnv *env, getdns_return_t ret) ;

void
throwJavaIssue(JNIEnv *env, char *message);

unsigned char* 
convertByteArrayToUnsignedCharArray(JNIEnv *env, jbyteArray array, int* len);
