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
#include <time.h>


 
void
print_response(struct getdns_dict * response)
{
        char *dict_str = getdns_pretty_print_dict(response);
        if (dict_str) {
                fprintf(stdout, "The packet %s\n", dict_str);
                free(dict_str);
        }
}

jobject NewInteger(JNIEnv* env, uint32_t value)
{
    jclass cls = (*env)->FindClass(env, "java/lang/Integer");
    jmethodID methodID = (*env)->GetMethodID(env, cls, "<init>", "(I)V");
    return (*env)->NewObject(env, cls, methodID, value);
}
jobject
convertToJavaMap(JNIEnv *env, struct getdns_dict *dict);

jobject 
convertToList(JNIEnv* env, struct getdns_list* list) {

    jobject resultslist;
    if (!list) {
        return NULL;
    }
    jclass alistClass = (*env)->FindClass(env, "java/util/ArrayList");
    if(alistClass == NULL)
    {
        return NULL;
    }

    jmethodID init = (*env)->GetMethodID(env, alistClass, "<init>", "(I)V");
    resultslist = (*env)->NewObject(env, alistClass, init, 1);
    jmethodID add = (*env)->GetMethodID(env, alistClass, "add",
                "(Ljava/lang/Object;)Z");
    size_t len, i;
    getdns_list_get_length(list, &len);
    for (i = 0; i < len; ++i) {
        getdns_data_type type;
        getdns_list_get_data_type(list, i, &type);
        switch (type) {
            case t_bindata:
            {
                getdns_bindata* data = NULL;
                getdns_list_get_bindata(list, i, &data);
                //printf("Came to string1.....!!!!! %s\n", ((char*)data->data));
                (*env)->CallObjectMethod(env, resultslist, add, (*env)->NewStringUTF(env, (char*)data->data));
                break;
            }

            case t_int:
            {
                uint32_t res = 0;
                getdns_list_get_int(list, i, &res);
                //PyObject* res1 = Py_BuildValue("i", res);
                (*env)->CallObjectMethod(env, resultslist, add, NewInteger(env, res));
                //PyDict_SetItem(resultslist, key, res);
                break;
            }
            case t_dict:
            {
                //printf("Came to dict.....!!!!!\n");
                getdns_dict* subdict = NULL;
                getdns_list_get_dict(list, i, &subdict);
                //PyObject* rl1 = convertToDict(subdict);
                //PyObject *res1 = Py_BuildValue("O", rl1);
                (*env)->CallObjectMethod(env, resultslist, add, convertToJavaMap(env, subdict));
                //PyDict_SetItem(resultslist, PyString_FromString((char*)nameBin->data), res1);
                break;
            }
            /*case t_list:
            {
                key = (*env)->NewStringUTF(env, (char*)nameBin->data);
                getdns_list* list = NULL;
                getdns_dict_get_list(dict, (char*)nameBin->data, &list);
                (*env)->CallObjectMethod(env, resultslist, put, key, convertToList(env,list));
                break;
            }*/
            /*case t_bindata:
            {
                getdns_bindata* data = NULL;
                getdns_list_get_bindata(list, i, &data);
                PyObject* res = convertBinData(data, NULL);
                if (res) {
                    PyList_Append(resultslist1, res);
                } else {

                    PyObject* res1 = Py_BuildValue("s", "empty");
                    PyList_Append(resultslist1, res1);
                }
                break;
            }
            case t_int:
            {
                uint32_t res = 0;
                getdns_list_get_int(list, i, &res);
                PyObject* res1 = Py_BuildValue("i", res); 
                PyList_Append(resultslist1, res1);
                break;
            }
            case t_dict:
            {
                getdns_dict* dict = NULL;
                getdns_list_get_dict(list, i, &dict);
                PyObject *rl1 = convertToDict(dict);
                PyObject *res1 = Py_BuildValue("O", rl1);
                PyList_Append(resultslist1, res1);
                break;
            }
            case t_list:
            {
                getdns_list* sublist = NULL;
                getdns_list_get_list(list, i, &sublist);
                PyObject* rl1 = convertToList(sublist);
                PyObject *res1 = Py_BuildValue("O", rl1);
                PyList_Append(resultslist1, res1);
                break;
            }*/
            default:
                break;
        }
    }

    return resultslist;
}

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

char *
convertBinData(getdns_bindata* data,
                    const char* key) {


    size_t i; 


    if (data->size == 1 && data->data[0] == 0) {
        return(".");
    }

    int printable = 1;
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
            //PyErr_SetString(getdns_error, GETDNS_RETURN_GENERIC_ERROR_TEXT);
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
        /*uint8_t *blob = (uint8_t *)malloc(data->size);

        memcpy(blob, data->data, data->size);
        return (PyBuffer_FromMemory(blob, (Py_ssize_t)data->size));*/
        //printf("This is plain byte for key: %s\n", key);
        return NULL;
    }
    return NULL;                /* should never get here .. */
}


jobject
convertToJavaMap(JNIEnv *env, struct getdns_dict *dict)
{

    jobject resultslist;
    jclass mapClass = (*env)->FindClass(env, "java/util/HashMap");
    if(mapClass == NULL)
    {
        return NULL;
    }

    jmethodID init = (*env)->GetMethodID(env, mapClass, "<init>", "(I)V");
    resultslist = (*env)->NewObject(env, mapClass, init, 1);
    jmethodID put = (*env)->GetMethodID(env, mapClass, "put",
                "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    jstring key = (*env)->NewStringUTF(env, "key1");
    //(*env)->CallObjectMethod(env, resultslist, put, key, val);
    /*if ((resultslist = PyDict_New()) == NULL)  {
        error_exit("Unable to allocate response list", 0);
        return NULL;
    }*/

    getdns_list* names;
    getdns_dict_get_names(dict, &names);
    size_t len = 0, i = 0;
    getdns_list_get_length(names, &len);
    for (i = 0; i < len; ++i) {
        getdns_bindata* nameBin;
        getdns_list_get_bindata(names, i, &nameBin);
        getdns_data_type type;
        getdns_dict_get_data_type(dict, (char*)nameBin->data, &type);
        switch (type) {
            case t_bindata:
            {
                key = (*env)->NewStringUTF(env, (char*)nameBin->data);
                getdns_bindata* data = NULL;
                getdns_dict_get_bindata(dict, (char*)nameBin->data, &data);
                char *convertedData = convertBinData(data, (char*)nameBin->data);
                //if(convertedData != NULL)
    		    //printf("This is the string: %s\n", convertedData);
                (*env)->CallObjectMethod(env, resultslist, put, key, (*env)->NewStringUTF(env, convertedData));
                /*PyObject *res = convertBinData(data, (char*)nameBin->data);
                if (res) {
                   PyDict_SetItem(resultslist, PyString_FromString((char*)nameBin->data), res);
                } else {
                   PyObject* res1 = Py_BuildValue("s", "empty");
                   PyDict_SetItem(resultslist, PyString_FromString((char*)nameBin->data), res1);
                }*/
                break;
            }
            case t_int:
            {
                uint32_t res = 0;
                key = (*env)->NewStringUTF(env, (char*)nameBin->data);
                getdns_dict_get_int(dict, (char*)nameBin->data, &res);
                //PyObject* res1 = Py_BuildValue("i", res);
                (*env)->CallObjectMethod(env, resultslist, put, key, NewInteger(env, res));
                //PyDict_SetItem(resultslist, key, res);
                break;
            }
            case t_dict:
            {
                //printf("Came to dict inside dict..... %s!!!!!\n", (char*)nameBin->data);
                key = (*env)->NewStringUTF(env, (char*)nameBin->data);
                getdns_dict* subdict = NULL;
                getdns_dict_get_dict(dict, (char*)nameBin->data, &subdict);
                //PyObject* rl1 = convertToDict(subdict);
                //PyObject *res1 = Py_BuildValue("O", rl1);
                (*env)->CallObjectMethod(env, resultslist, put, key, convertToJavaMap(env, subdict));
                //PyDict_SetItem(resultslist, PyString_FromString((char*)nameBin->data), res1);
                break;
            }
            case t_list:
            {
                key = (*env)->NewStringUTF(env, (char*)nameBin->data);
                getdns_list* list = NULL;
                getdns_dict_get_list(dict, (char*)nameBin->data, &list);
                (*env)->CallObjectMethod(env, resultslist, put, key, convertToList(env,list));
                /*PyObject* rl1 = convertToList(list);
                PyObject *res1 = Py_BuildValue("O", rl1);
                PyDict_SetItem(resultslist, PyString_FromString((char*)nameBin->data), res1);*/
                break;
            }
            default:
                break;
        }
    }

    getdns_list_destroy(names);
    return resultslist;
}

/*
 * This is covering only success scenarios and incomplete. 
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_wrapper_GetDNS_createContext
  (JNIEnv *env, jobject thisObj, jint myInt) {
    //printf("This is the param: %d\n",myInt);
    struct getdns_context *context = NULL;
    getdns_context_create(&context,1);
    jobject returnObj = (*env)->NewDirectByteBuffer(env, (void*) context, 0);
    return returnObj;
}
JNIEXPORT void JNICALL Java_com_verisign_getdns_wrapper_GetDNS_destroyContext
  (JNIEnv *env, jobject thisObj, jobject contextParam) {
    struct getdns_context *context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, contextParam);
    getdns_context_destroy(context);
}

JNIEXPORT jobject JNICALL Java_com_verisign_getdns_wrapper_GetDNS_general
  (JNIEnv *env, jobject thisObj, jobject contextParam, jstring name){
    //static double time_taken = 0.0;
    struct getdns_context *context = (struct getdns_context*) (*env)->GetDirectBufferAddress(env, contextParam);
    const char *nativeString = (*env)->GetStringUTFChars(env, name, 0);
    struct getdns_dict *response = NULL;
    int ret = getdns_general_sync(context, nativeString, 1, NULL, &response);
    //printf("Step 1: %d\n", ret);
    (*env)->ReleaseStringUTFChars(env, name, nativeString);
    //print_response(response);
    //getF
    //clock_t begin=clock(), end;
    jobject retObj =  convertToJavaMap(env, response);
    /*end = clock() - begin;
    time_taken += ((double)end)/CLOCKS_PER_SEC;
    printf("fun() took %f seconds to execute \n", time_taken);*/
    return retObj;
}

