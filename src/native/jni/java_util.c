#include "java_util.h"

/*
 * Init all classes and methods needed to be used during converting c data into Java data and vice versa.
 */
struct util_methods*
init_util_methods(JNIEnv* env, struct util_methods* methods) {
    //struct util_methods* methods = (struct util_methods*) malloc(sizeof(struct util_methods));
    methods->listClass = (*env)->FindClass(env, "java/util/ArrayList");
    methods->mapClass = (*env)->FindClass(env, "java/util/HashMap");
    methods->integerClass = (*env)->FindClass(env, "java/lang/Integer");
    methods->callbackClass = (*env)->FindClass(env, "com/verisign/getdns/IGetDNSCallback");
    methods->setClass = (*env)->FindClass(env, "java/util/Set");
    methods->iteratorClass = (*env)->FindClass(env, "java/util/Iterator");
    methods->entryClass = (*env)->FindClass(env, "java/util/Map$Entry");
    if(methods->listClass == NULL || methods->mapClass == NULL || methods->integerClass == NULL || methods->callbackClass == NULL || NULL == methods->setClass ||
       NULL == methods->iteratorClass || NULL == methods->entryClass)
    {
        throwJavaIssue(env, "Problem with Java environment 1");
        return NULL;
    }

    methods->listInit = (*env)->GetMethodID(env, methods->listClass, "<init>", "(I)V");
    methods->listAdd = (*env)->GetMethodID(env, methods->listClass, "add",
                "(Ljava/lang/Object;)Z");

    methods->mapInit = (*env)->GetMethodID(env, methods->mapClass, "<init>", "(I)V");
    methods->mapPut = (*env)->GetMethodID(env, methods->mapClass, "put",
                "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

    methods->mapGetEntrySet = (*env)->GetMethodID(env, methods->mapClass, "entrySet", "()Ljava/util/Set;");
    methods->entrySetGetIterator = (*env)->GetMethodID(env, methods->setClass, "iterator", "()Ljava/util/Iterator;");

    methods->hasNext = (*env)->GetMethodID(env, methods->iteratorClass, "hasNext", "()Z");
    methods->next = (*env)->GetMethodID(env, methods->iteratorClass, "next", "()Ljava/lang/Object;");

    methods->entryGetKey = (*env)->GetMethodID(env, methods->entryClass, "getKey", "()Ljava/lang/Object;");
    methods->entryGetValue = (*env)->GetMethodID(env, methods->entryClass, "getValue", "()Ljava/lang/Object;");
    
    methods->integerInit = (*env)->GetMethodID(env, methods->integerClass, "<init>", "(I)V");
    methods->intValue = (*env)->GetMethodID(env, methods->integerClass, "intValue", "()I");  

    methods->callbackHandleResponse = (*env)->GetMethodID(env, methods->callbackClass, "handleResponse", "(Ljava/util/HashMap;)V");

    if(methods->listInit == NULL || methods->listAdd == NULL || methods->mapInit == NULL || methods->mapPut == NULL || methods->integerInit == NULL ||
      NULL == methods->mapGetEntrySet || NULL == methods->callbackHandleResponse || NULL == methods->entrySetGetIterator || NULL == methods->hasNext || NULL == methods->next ||
      NULL == methods->entryGetKey || NULL == methods->entryGetValue || NULL == methods->intValue)
    {
        throwJavaIssue(env, "Problem with Java environment during init methods");
        return NULL;
    }
    return methods;
}

/*
 * Method to throw a RuntimeException.
 */
void
throwJavaIssue(JNIEnv *env, char *message) {
    jclass exClass = (*env)->FindClass( env, "java/lang/RuntimeException");
    (*env)->ThrowNew( env, exClass, message);
}

/*
 * Method to throw a GetDNSException.
 */
void
throwException(JNIEnv *env, getdns_return_t ret) {
    jclass getDNSRetClass = (*env)->FindClass(env, "com/verisign/getdns/wrapper/GetDNSReturn");
    jmethodID fromInt = (*env)->GetStaticMethodID(env, getDNSRetClass, "fromInt", "(I)Lcom/verisign/getdns/wrapper/GetDNSReturn;");
    jobject retObj = (*env)->CallStaticObjectMethod(env, getDNSRetClass, fromInt, ret);
    jclass exClass = (*env)->FindClass( env, "com/verisign/getdns/wrapper/GetDNSException");
    jmethodID init = (*env)->GetMethodID(env, exClass, "<init>", "(Lcom/verisign/getdns/wrapper/GetDNSReturn;)V");
    jobject exObj = (*env)->NewObject(env, exClass, init, retObj);
    (*env)->Throw( env, exObj);
}
