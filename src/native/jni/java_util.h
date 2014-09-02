#include <stdio.h>
#include <jni.h>
#include <stdlib.h>

struct util_methods {
    jclass    listClass;
    jmethodID listInit;
    jmethodID listAdd;
    jclass    mapClass;
    jmethodID mapInit;
    jmethodID mapPut;
};

struct util_methods*
init_util_methods(JNIEnv* env);
