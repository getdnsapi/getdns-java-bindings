#include <stdio.h>
#include <jni.h>
#include <stdlib.h>
#include "getdns/getdns.h"

struct util_methods {
    jclass    listClass;
    jmethodID listInit;
    jmethodID listAdd;
    jclass    mapClass;
    jmethodID mapInit;
    jmethodID mapPut;
    jclass    integerClass;
    jmethodID integerInit;
};

struct util_methods*
init_util_methods(JNIEnv* env, struct util_methods*);

void
throwException(JNIEnv *env, getdns_return_t ret) ;

void
throwJavaIssue(JNIEnv *env, char *message);
