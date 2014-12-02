#include <jni.h>
#include <stdio.h>
#include "com_verisign_getdns_GetDNSContext.h"
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <inttypes.h>
#include <unistd.h>
#include <check.h>
//#include "context.h"
#include "getdns/getdns.h"
//#include "check_getdns_common.h"
#include <ctype.h>
#include <getdns/getdns_ext_libevent.h>
#include <event.h>
#include "getdns_util.h"

static JavaVM *jvm;
/*
 * This is a util method, can be removed later
 */
void
print_response(struct getdns_dict * response)
{
        char *dict_str = getdns_pretty_print_dict(response);
        if (dict_str) {
                fprintf(stdout, "The packet %s\n", dict_str);
                free(dict_str);
        }
}

void cleanup(const char *nativeString, struct getdns_dict *response,getdns_dict * this_extensions,JNIEnv *env,jstring name){
	if(NULL != nativeString)
        (*env)->ReleaseStringUTFChars(env, name, nativeString);

    if(NULL != response) {
	   getdns_dict_destroy(response);
    }
    if(NULL != this_extensions)
        getdns_dict_destroy(this_extensions);

}

JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_contextCreate
  (JNIEnv *env, jobject thisObj, jobject eventBase, jint setFromOs) {
    jint rs = (*env)->GetJavaVM(env, &jvm);
    if(rs != JNI_OK) {
        throwJavaIssue(env, "Cannot get VM PTR");
        return NULL;
    }
    struct getdns_context *context = NULL;
    getdns_return_t ret = getdns_context_create(&context, setFromOs);
    /*if(ret != GETDNS_RETURN_GOOD) {
        throwException(env, ret);
        return NULL;
    }*/
    if(throwExceptionOnError(env,ret))
        return NULL;
        
    if(eventBase != NULL) {
        struct event_base *base = (struct event_base*) (*env)->GetDirectBufferAddress(env, eventBase);
        (void)getdns_extension_set_libevent_base(context, base);
    }
    //getdns_context_set_use_threads(context, 1);
    return (*env)->NewDirectByteBuffer(env, (void*) context, 0);
}

/*
 * TODO: Need to validate contextParam is the context and not something else.
 */
JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_contextDestroy
  (JNIEnv *env, jobject thisObj, jobject contextParam) {
    if(contextParam != NULL) {
        struct getdns_context *context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, contextParam);
        getdns_context_destroy(context);
    }
}


/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_generalSync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jint request_type, jobject extensions) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions); /* getextensions(extensions,env,thisObj);*/

    getdns_return_t ret = getdns_general_sync(context, nativeString, request_type, this_extensions, &response);

    if(!throwExceptionOnError(env, ret) && NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);
    
    cleanup(nativeString, response,this_extensions,env,name);
    return returnValue;
}


JNIEXPORT jobject JNICALL JNICALL Java_com_verisign_getdns_GetDNSContext_addressSync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jobject extensions) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions); /* getextensions(extensions,env,thisObj); */ 

    getdns_return_t ret = getdns_address_sync(context, nativeString,  this_extensions, &response);

    if(!throwExceptionOnError(env, ret) && NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);

    cleanup(nativeString, response,this_extensions,env,name); 
    return returnValue;
}

JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_serviceSync
    (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jobject extensions) {
    
    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;
    

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    getdns_dict * this_extensions =  convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/ 

    getdns_return_t ret = getdns_service_sync(context, nativeString,  this_extensions, &response);

    if(!throwExceptionOnError(env, ret) && NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);

    cleanup(nativeString, response,this_extensions,env,name); 
    return returnValue;
}

JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_hostnameSync(JNIEnv *env, jobject thisObj, jobject contextParam, jobject address, jobject extensions) {
  
  
  
    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    getdns_dict * this_extensions =  convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/
    getdns_dict * this_address =  convertMapToDict(env, thisObj, address); 

    getdns_return_t ret = getdns_hostname_sync(context, this_address, this_extensions, &response);

    if(!throwExceptionOnError(env, ret) && NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);

    cleanup(nativeString, response,this_extensions,env,NULL); 
    return returnValue;
}

void callbackfn(struct getdns_context *context,
                getdns_callback_type_t callback_type,
                struct getdns_dict *response,
                void *userarg,
                getdns_transaction_t transaction_id){
    jobject callbackObj = userarg;
    JNIEnv *env;
    jint rs = (*jvm)->AttachCurrentThread(jvm, (void**) &env, NULL);
    struct util_methods methods;
    if(NULL == callbackObj) {
        throwJavaIssue(env, "Callback object was NULL");
        return;
    }
    if(NULL == response) {
        throwJavaIssue(env, "Response was NULL");
        return;
    }
    if(JNI_OK != rs) {
        throwJavaIssue(env, "JVM issue: Cannot get VM PTR");
        return;
    }
    if(NULL == init_util_methods(env, &methods) )
        return;
    
    (*env)->CallObjectMethod(env, callbackObj, methods.callbackHandleResponse, convertToJavaMap(env, &methods, response)); 
   
    /*
     * Cleaning up.
     */
    getdns_dict_destroy(response);
    (*env)->DeleteGlobalRef(env, callbackObj);
}
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_createEventBase
  (JNIEnv *env, jclass class) {

    struct event_base *eventBase;
    eventBase = event_base_new();
    if (eventBase == NULL)
    {
        fprintf(stderr, "Trying to create the event base failed.\n");
        throwJavaIssue(env, "Trying to create the event base failed.");
        return NULL;
    }
    return (*env)->NewDirectByteBuffer(env, (void*) eventBase, 0);
}

JNIEXPORT void JNICALL Java_com_verisign_getdns_GetDNSContext_startListening
  (JNIEnv *env, jclass class, jobject eventBase) {
    if(eventBase != NULL) {
        struct event_base *base = (struct event_base*) (*env)->GetDirectBufferAddress(env, eventBase);
        event_base_dispatch(base);
    }
}

/*
 * TODO: 
 *       Need to validate contextParam.
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_generalAsync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jint request_type, jobject extensions, jobject callbackObj) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    getdns_transaction_t transaction_id = 0;
    struct util_methods methods;
    getdns_dict * this_extensions = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    this_extensions = convertMapToDict(env, thisObj, extensions);

    getdns_return_t ret = getdns_general(context, nativeString, request_type, this_extensions, (*env)->NewGlobalRef(env, callbackObj), &transaction_id, callbackfn);
    throwExceptionOnError(env, ret);

    cleanup(nativeString, NULL,this_extensions,env,name);

    return ret;
}



/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_addressAsync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jobject extensions, jobject callbackObj) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    getdns_transaction_t transaction_id = 0;
    struct util_methods methods;
    getdns_dict * this_extensions = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    this_extensions = convertMapToDict(env, thisObj, extensions);

    getdns_return_t ret = getdns_address(context, nativeString, this_extensions, (*env)->NewGlobalRef(env, callbackObj), &transaction_id, callbackfn);
    throwExceptionOnError(env, ret);

    cleanup(nativeString, NULL,this_extensions,env,name);

    return ret;
}


/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_serviceAsync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jobject extensions, jobject callbackObj) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    getdns_transaction_t transaction_id = 0;
    struct util_methods methods;
    getdns_dict * this_extensions = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    CHECK_NULL_AND_INIT_STR(name, nativeString)
    this_extensions = convertMapToDict(env, thisObj, extensions);

    getdns_return_t ret = getdns_service(context, nativeString, this_extensions, (*env)->NewGlobalRef(env, callbackObj), &transaction_id, callbackfn);
    throwExceptionOnError(env, ret);

    cleanup(nativeString, NULL,this_extensions,env,name);

    return ret;
}


JNIEXPORT jlong JNICALL Java_com_verisign_getdns_GetDNSContext_hostnameAsync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jobject address, jobject extensions, jobject callbackObj) {
  
   struct getdns_context *context = NULL;
    const char *nativeString = NULL;
     getdns_transaction_t transaction_id = 0;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;

    CHECK_NULL_INIT_PTR(contextParam, context)
    getdns_dict * this_extensions =  convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/
    getdns_dict * this_address =  convertMapToDict(env, thisObj, address); 

    getdns_return_t ret = getdns_hostname(context, this_address, this_extensions, (*env)->NewGlobalRef(env, callbackObj), &transaction_id, callbackfn);

    if(!throwExceptionOnError(env, ret) && NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);

    cleanup(nativeString, response,this_extensions,env,NULL); 
    return ret;
 
  }