#include "java_util.h"

/*struct util_methods {
    jclass    listClass;
    jmethodID listInit;
    jmethodID listAdd;
    jclass    mapClass;
    jmethodID mapInit;
    jmethodID mapPut;
};*/

struct util_methods*
init_util_methods(JNIEnv* env) {
    struct util_methods* methods = (struct util_methods*) malloc(sizeof(struct util_methods));
    methods->listClass = (*env)->FindClass(env, "java/util/ArrayList");
    if(methods->listClass == NULL)
    {
        return NULL;
    }

    methods->listInit = (*env)->GetMethodID(env, methods->listClass, "<init>", "(I)V");
    methods->listAdd = (*env)->GetMethodID(env, methods->listClass, "add",
                "(Ljava/lang/Object;)Z");
    methods->mapClass = (*env)->FindClass(env, "java/util/HashMap");
    if(methods->mapClass == NULL)
    {
        return NULL;
    }

    methods->mapInit = (*env)->GetMethodID(env, methods->mapClass, "<init>", "(I)V");
    methods->mapPut = (*env)->GetMethodID(env, methods->mapClass, "put",
                "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    return methods;
}


