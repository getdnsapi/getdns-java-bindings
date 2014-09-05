#include <jni.h>
#include <stdio.h>
#include "com_verisign_getdns_wrapper_GetDNS.h"
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
#include "java_util.h"

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

/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
static int
priv_getdns_bindata_is_dname(struct getdns_bindata *bindata)
{
    size_t i = 0, n_labels = 0;
    while (i < bindata->size) {
        i += ((size_t)bindata->data[i]) + 1;
        n_labels++;
    }
    return i == bindata->size && n_labels > 1 &&
        bindata->data[bindata->size - 1] == 0;
}
/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
char *
convertBinData(getdns_bindata* data,
                    const char* key) {
    if (data->size == 1 && data->data[0] == 0) {
        return(".");
    }

    int printable = 1;
    size_t i; 
    for (i = 0; i < data->size; ++i) {
        if (!isprint(data->data[i])) {
            if (data->data[i] == 0 &&
                i == data->size - 1) {
                break;
            }
            printable = 0;
            break;
        }
    }

    if (printable == 1) {
        return (char *)data->data;
    }
    if (priv_getdns_bindata_is_dname(data)) {
        char* dname = NULL;

        if (getdns_convert_dns_name_to_fqdn(data, &dname)
            == GETDNS_RETURN_GOOD) {
            return dname;
        } else {
            printf("getdns_convert_dns_name_to_fqdn != GETDNS_RETURN_GOOD for the key: %s\n", key);
            return NULL;
        }
    } else if (key != NULL &&
        (strcmp(key, "ipv4_address") == 0 ||
         strcmp(key, "ipv6_address") == 0)) {
        char* ipStr = getdns_display_ip_address(data);
        if (ipStr) {
            return ipStr;
        }
    }
    else if(key != NULL &&
        (strcmp(key, "address_data") == 0)) {
        return getdns_display_ip_address(data);
    }
    else  {
/*
 * TODO:   Not sure how to handle this binary data, need to incorporate as required.
 */
        /*uint8_t *blob = (uint8_t *)malloc(data->size);

        memcpy(blob, data->data, data->size);
        return (PyBuffer_FromMemory(blob, (Py_ssize_t)data->size));*/
        //printf("This is plain byte for key: %s\n", key);
        return NULL;
    }
    return NULL;                /* should never get here .. */
}

jobject
convertToJavaMap(JNIEnv *env, struct util_methods* methods, struct getdns_dict *dict);//, jobject responseObject);

/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
jobject 
convertToList(JNIEnv* env, struct util_methods* methods, struct getdns_list* list) {

    if (!list) 
        return NULL;
    
    jobject resultslist = (*env)->NewObject(env, methods->listClass, methods->listInit, 1);

    size_t len, i;
    getdns_list_get_length(list, &len);

    /*
     * Declaring the variables used inside the loop.
     */
    getdns_data_type type;
    getdns_list* sublist = NULL;
    getdns_bindata* data = NULL;
    uint32_t res = 0;
    getdns_dict* subdict = NULL;
    char *convertedData = NULL;
    for (i = 0; i < len; ++i) {
        getdns_list_get_data_type(list, i, &type);
        switch (type) {
            case t_bindata:
            {
                if(getdns_list_get_bindata(list, i, &data) == GETDNS_RETURN_GOOD) {
                    convertedData = convertBinData(data, NULL);
                    if(convertedData != NULL)
		        (*env)->CallObjectMethod(env, resultslist, methods->listAdd, (*env)->NewStringUTF(env, convertedData));
                }
                break;
            }

            case t_int:
            {
                if(getdns_list_get_int(list, i, &res) == GETDNS_RETURN_GOOD)
                    (*env)->CallObjectMethod(env, resultslist, methods->listAdd, (*env)->NewObject(env, methods->integerClass, methods->integerInit, res));
                break;
            }
            case t_dict:
            {
                if(getdns_list_get_dict(list, i, &subdict) == GETDNS_RETURN_GOOD)
                    (*env)->CallObjectMethod(env, resultslist, methods->listAdd, convertToJavaMap(env, methods, subdict));
                break;
            }
            case t_list:
            {
                if(getdns_list_get_list(list, i, &sublist) == GETDNS_RETURN_GOOD)
                    (*env)->CallObjectMethod(env, resultslist, methods->listAdd, convertToList(env, methods, list));
                break;
            }
            default:
                break;
        }
    }

    return resultslist;
}


/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
jobject
convertToJavaMap(JNIEnv *env, struct util_methods* methods, struct getdns_dict *dict)//, jobject responseObject)
{
    jobject responseObject = (*env)->NewObject(env, methods->mapClass, methods->mapInit, 1);
    getdns_list* names;
    getdns_dict_get_names(dict, &names);
    size_t len = 0, i = 0;
    getdns_list_get_length(names, &len);

    /*
     * Declaring the variables used inside the loop.
     */
    getdns_bindata* nameBin;
    getdns_data_type type;
    getdns_bindata* data = NULL;
    uint32_t res = 0;
    getdns_dict* subdict = NULL;
    getdns_list* sublist = NULL;
    jstring key = NULL;
    char *convertedData = NULL;

    for (i = 0; i < len; ++i) {
        getdns_list_get_bindata(names, i, &nameBin);
        getdns_dict_get_data_type(dict, (char*)nameBin->data, &type);
        key = (*env)->NewStringUTF(env, (char*)nameBin->data);
        switch (type) {
            case t_bindata:
            {
                if(getdns_dict_get_bindata(dict, (char*)nameBin->data, &data) == GETDNS_RETURN_GOOD) {
                    convertedData = convertBinData(data, (char*)nameBin->data);
                    if(convertedData != NULL)
                        (*env)->CallObjectMethod(env, responseObject, methods->mapPut, key, (*env)->NewStringUTF(env, convertedData));
		}
                break;
            }
            case t_int:
            {
                if(getdns_dict_get_int(dict, (char*)nameBin->data, &res) == GETDNS_RETURN_GOOD) 
                    (*env)->CallObjectMethod(env, responseObject, methods->mapPut, key, (*env)->NewObject(env, methods->integerClass, methods->integerInit, res));
                break;
            }
            case t_dict:
            {
                getdns_dict_get_dict(dict, (char*)nameBin->data, &subdict);
                (*env)->CallObjectMethod(env, responseObject, methods->mapPut, key, convertToJavaMap(env, methods, subdict));//, (*env)->NewObject(env, mapClass, init, 1)));
                break;
            }
            case t_list:
            {
                getdns_dict_get_list(dict, (char*)nameBin->data, &sublist);
                (*env)->CallObjectMethod(env, responseObject, methods->mapPut, key, convertToList(env, methods, sublist));
                break;
            }
            default:
                break;
        }
    }

    getdns_list_destroy(names);
    return responseObject;
}

JNIEXPORT jobject JNICALL Java_com_verisign_getdns_wrapper_GetDNS_contextCreate
  (JNIEnv *env, jobject thisObj, jint myInt) {
    struct getdns_context *context = NULL;
    getdns_return_t ret = getdns_context_create(&context,1);
    if(ret != GETDNS_RETURN_GOOD) {
        throwException(env, ret);
        return NULL;
    }
    return (*env)->NewDirectByteBuffer(env, (void*) context, 0);
}

/*
 * TODO: Need to validate contextParam is the context and not something else.
 */
JNIEXPORT void JNICALL Java_com_verisign_getdns_wrapper_GetDNS_contextDestroy
  (JNIEnv *env, jobject thisObj, jobject contextParam) {
    if(contextParam != NULL) {
        struct getdns_context *context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, contextParam);
        getdns_context_destroy(context);
    }
}

/*
 * TODO: Conversion of extensions into getdns_dict is pending.
 *       Need to validate contextParam.
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_wrapper_GetDNS_generalSync
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name, jint request_type, jobject extensions) {

    struct getdns_context *context = NULL;
    const char *nativeString = NULL;
    struct getdns_dict *response = NULL;
    struct util_methods methods;
    jobject returnValue = NULL;

    if(NULL != contextParam)
        context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, contextParam);

    if(NULL != name)
        nativeString = (*env)->GetStringUTFChars(env, name, 0);

    getdns_return_t ret = getdns_general_sync(context, nativeString, request_type, NULL, &response);

    if(GETDNS_RETURN_GOOD != ret) 
        throwException(env, ret);

    else if(NULL != init_util_methods(env, &methods))
        returnValue = convertToJavaMap(env, &methods, response);//, responseObject);
    
    /*
     * Cleanup
     */
    if(NULL != nativeString)
        (*env)->ReleaseStringUTFChars(env, name, nativeString);

    if(NULL != response) {
        getdns_dict_destroy(response);
    }
    return returnValue;
}

