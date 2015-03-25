#include "getdns_util.h"

/*
 * Method to throw a GetDNSException.
 */
int throwExceptionOnErrorWithMessage(JNIEnv *env, char* message,
		getdns_return_t ret) {
	if (GETDNS_RETURN_GOOD == ret)
		return 0;

	jclass getDNSRetClass = (*env)->FindClass(env,
			"com/verisign/getdns/GetDNSReturn");
	jmethodID fromInt = (*env)->GetStaticMethodID(env, getDNSRetClass,
			"fromInt", "(I)Lcom/verisign/getdns/GetDNSReturn;");
	jobject retObj = (*env)->CallStaticObjectMethod(env, getDNSRetClass,
			fromInt, ret);
	jclass exClass = (*env)->FindClass(env,
			"com/verisign/getdns/GetDNSException");
	jmethodID init = (*env)->GetMethodID(env, exClass, "<init>",
			"(Ljava/lang/String;Lcom/verisign/getdns/GetDNSReturn;)V");
	jstring jMessage = NULL;
	CHECK_NULL_AND_CONVERT_TO_JAVA_STRING(message, jMessage);
	jobject exObj = (*env)->NewObject(env, exClass, init, jMessage, retObj);
	(*env)->Throw(env, exObj);
	return 1;
}

int throwExceptionOnError(JNIEnv *env, getdns_return_t ret) {
	return throwExceptionOnErrorWithMessage(env, NULL, ret);
}

/**
 * This method extracts values from ObjectArray and pass it to ipDict
 */
struct getdns_dict* getDnsDict(JNIEnv *env, jobjectArray value,
		struct util_methods methods) {
	struct getdns_dict* ipDict = NULL;
	if ((*env)->GetArrayLength(env, value) > 0) {
		int port = -1;
		const char* serverIP = NULL;
		jsize length = (*env)->GetArrayLength(env, value);
		for (int i = 0; i < length; i++) {
			if (serverIP == NULL) {
				serverIP = getStringFromArrayWithIndex(env, value, methods, i);

			} else {
				port = getIntFromArrayWithIndex(env, value, methods, i);
			}

		}
		if (serverIP != NULL && strcmp(serverIP, "") != 0) {
			ipDict = getdns_util_create_ip(serverIP);
			if (port != -1) {
				getdns_dict_set_int(ipDict, "port", port);
			}
		}
		(*env)->ReleaseStringUTFChars(env, NULL, serverIP);
	}
	return ipDict;
}

/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
static int priv_getdns_bindata_is_dname(struct getdns_bindata *bindata) {
	size_t i = 0, n_labels = 0;
	while (i < bindata->size) {
		i += ((size_t) bindata->data[i]) + 1;
		n_labels++;
	}
	return i == bindata->size && n_labels > 1
			&& bindata->data[bindata->size - 1] == 0;
}

/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
jobject convertBinData(JNIEnv* env, getdns_bindata* data, const char* key) {
	char* extractedData = NULL;
	char* dnameOrIp = NULL;
	jobject result = NULL;

	if (data->size == 1 && data->data[0] == 0) {
		extractedData = ".";
	} else if (isPrintable((char *) data->data, data->size) == 1) {
		extractedData = (char *) data->data;
	} else if (priv_getdns_bindata_is_dname(data)) {

		if (getdns_convert_dns_name_to_fqdn(data, &dnameOrIp)
				!= GETDNS_RETURN_GOOD) {
			fprintf(stderr,
					"getdns_convert_dns_name_to_fqdn != GETDNS_RETURN_GOOD for the key: %s\n",
					key);
		}
	} else if (key != NULL
			&& (strcmp(key, "ipv4_address") == 0
					|| strcmp(key, "ipv6_address") == 0
					|| strcmp(key, "address_data") == 0)) {
		dnameOrIp = getdns_display_ip_address(data);
	} else {
		result = convertUnsignedCharArrayToByteArray(env, data->data,
				data->size);
	}

	CHECK_NULL_AND_CONVERT_TO_JAVA_STRING(extractedData, result)
	CHECK_NULL_AND_CONVERT_TO_JAVA_STRING(dnameOrIp, result)

	return result;
}

/*
 * convert getdns_list* to Arraylist
 */
jobject convertToList(JNIEnv* env, struct util_methods* methods,
		struct getdns_list* list) {

	if (!list)
		return NULL;

	jobject resultslist = (*env)->NewObject(env, methods->listClass,
			methods->listInit, 1);

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
	jobject convertedData = NULL;
	for (i = 0; i < len; ++i) {
		getdns_list_get_data_type(list, i, &type);
		switch (type) {
		case t_bindata: {
			if (getdns_list_get_bindata(list, i, &data) == GETDNS_RETURN_GOOD) {
				convertedData = convertBinData(env, data, NULL);
				if (convertedData != NULL)
					(*env)->CallObjectMethod(env, resultslist, methods->listAdd,
							convertedData);
			}
			break;
		}

		case t_int: {
			if (getdns_list_get_int(list, i, &res) == GETDNS_RETURN_GOOD)
				(*env)->CallObjectMethod(env, resultslist, methods->listAdd,
						(*env)->NewObject(env, methods->integerClass,
								methods->integerInit, res));
			break;
		}
		case t_dict: {
			if (getdns_list_get_dict(list, i, &subdict) == GETDNS_RETURN_GOOD)
				(*env)->CallObjectMethod(env, resultslist, methods->listAdd,
						convertToJavaMap(env, methods, subdict));
			break;
		}
		case t_list: {
			if (getdns_list_get_list(list, i, &sublist) == GETDNS_RETURN_GOOD)
				(*env)->CallObjectMethod(env, resultslist, methods->listAdd,
						convertToList(env, methods, list));
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
jobject convertToJavaMap(JNIEnv *env, struct util_methods* methods,
		struct getdns_dict *dict) //, jobject responseObject)
{
	jobject responseObject = (*env)->NewObject(env, methods->mapClass,
			methods->mapInit, 1);
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
	jobject convertedData = NULL;

	for (i = 0; i < len; ++i) {
		getdns_list_get_bindata(names, i, &nameBin);
		getdns_dict_get_data_type(dict, (char*) nameBin->data, &type);
		key = (*env)->NewStringUTF(env, (char*) nameBin->data);
		switch (type) {
		case t_bindata: {
			if (getdns_dict_get_bindata(dict, (char*) nameBin->data, &data)
					== GETDNS_RETURN_GOOD) {
				convertedData = convertBinData(env, data,
						(char*) nameBin->data);
				if (convertedData != NULL)
					(*env)->CallObjectMethod(env, responseObject,
							methods->mapPut, key, convertedData);
			}
			break;
		}
		case t_int: {
			if (getdns_dict_get_int(dict, (char*) nameBin->data, &res)
					== GETDNS_RETURN_GOOD)
				(*env)->CallObjectMethod(env, responseObject, methods->mapPut,
						key,
						(*env)->NewObject(env, methods->integerClass,
								methods->integerInit, res));
			break;
		}
		case t_dict: {
			getdns_dict_get_dict(dict, (char*) nameBin->data, &subdict);
			(*env)->CallObjectMethod(env, responseObject, methods->mapPut, key,
					convertToJavaMap(env, methods, subdict)); //, (*env)->NewObject(env, mapClass, init, 1)));
			break;
		}
		case t_list: {
			getdns_dict_get_list(dict, (char*) nameBin->data, &sublist);
			(*env)->CallObjectMethod(env, responseObject, methods->mapPut, key,
					convertToList(env, methods, sublist));
			break;
		}
		default:
			break;
		}
	}

	getdns_list_destroy(names);
	return responseObject;
}

getdns_list* convertToGetDNSList(JNIEnv *env, jobject thisObj, jobject mapObj) {
	if (NULL == mapObj)
		return NULL;
	struct getdns_list *result = getdns_list_create();
	struct util_methods methods;

	if (NULL == init_util_methods(env, &methods)) {
		throwJavaIssue(env,
				"Error while converting to dict, could not init java methods");
		return NULL;
	}

	//jobject jentrySet = (*env)->CallObjectMethod(env, mapObj, methods.mapGetEntrySet);
	jobject jiterator = (*env)->CallObjectMethod(env, mapObj,
			methods.listGetIterator);
	size_t idx = 0;

	while ((*env)->CallBooleanMethod(env, jiterator, methods.hasNext)) {
		getdns_list_get_length(result, &idx);
		jobject jvalue = (*env)->CallObjectMethod(env, jiterator, methods.next);
		jclass valueClass = (*env)->GetObjectClass(env, jvalue);

		if ((*env)->IsAssignableFrom(env, methods.integerClass, valueClass)) { // Integer value case.
			getdns_list_set_int(result, idx,
					(*env)->CallIntMethod(env, jvalue, methods.intValue));
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/lang/String"), valueClass)) { // String value case.
			const char* value = (*env)->GetStringUTFChars(env, jvalue, 0);
			getdns_bindata bdata = { (*env)->GetStringUTFLength(env, jvalue),
					(uint8_t *) value };
			getdns_list_set_bindata(result, idx, &bdata);
			(*env)->ReleaseStringUTFChars(env, jvalue, value);
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/util/HashMap"), valueClass)) { // Map value case.
			getdns_dict* subdict = convertMapToDict(env, thisObj, jvalue);
			getdns_list_set_dict(result, idx, subdict);
			getdns_dict_destroy(subdict);
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/util/List"), valueClass)) { // Map value case.
			getdns_list* sublist = convertToGetDNSList(env, thisObj, jvalue);
			getdns_list_set_list(result, idx, sublist);
			getdns_list_destroy(sublist);
		} else if ((*env)->IsAssignableFrom(env, (*env)->FindClass(env, "[B"),
				valueClass)) { // Map value case.
			int len = 0;
			unsigned char *bytes = convertByteArrayToUnsignedCharArray(env,
					jvalue, &len);
			getdns_bindata this_ipv4_addr = { len, bytes };
			getdns_list_set_bindata(result, idx, &this_ipv4_addr);
			free(bytes);
		}

	}
	return result;
}

getdns_dict* convertMapToDict(JNIEnv *env, jobject thisObj, jobject mapObj) {
	if (NULL == mapObj)
		return NULL;
	struct getdns_dict *result = getdns_dict_create();
	struct util_methods methods;

	if (NULL == init_util_methods(env, &methods)) {
		throwJavaIssue(env,
				"Error while converting to dict, could not init java methods");
		return NULL;
	}

	jobject jentrySet = (*env)->CallObjectMethod(env, mapObj,
			methods.mapGetEntrySet);
	jobject jiterator = (*env)->CallObjectMethod(env, jentrySet,
			methods.entrySetGetIterator);

	while ((*env)->CallBooleanMethod(env, jiterator, methods.hasNext)) {
		jobject jentry = (*env)->CallObjectMethod(env, jiterator, methods.next);
		jobject jkey = (*env)->CallObjectMethod(env, jentry,
				methods.entryGetKey);
		jobject jvalue = (*env)->CallObjectMethod(env, jentry,
				methods.entryGetValue);
		jclass valueClass = (*env)->GetObjectClass(env, jvalue);
		const char* key = (*env)->GetStringUTFChars(env, jkey, 0);

		if (key == NULL)
			continue; // Ignoring null key for now.

		if ((*env)->IsAssignableFrom(env, methods.integerClass, valueClass)) { // Integer value case.
			getdns_dict_set_int(result, key,
					(*env)->CallIntMethod(env, jvalue, methods.intValue));
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/lang/String"), valueClass)) { // String value case.
			const char* value = (*env)->GetStringUTFChars(env, jvalue, 0);
			printf("value:  %s\n", value);
			getdns_dict_util_set_string(result, (char*) key, value);
			(*env)->ReleaseStringUTFChars(env, jvalue, value);
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/util/HashMap"), valueClass)) { // Map value case.
			getdns_dict* subdict = convertMapToDict(env, thisObj, jvalue);
			getdns_dict_set_dict(result, key, subdict);
			getdns_dict_destroy(subdict);
		} else if ((*env)->IsAssignableFrom(env,
				(*env)->FindClass(env, "java/util/ArrayList"), valueClass)) { // Map value case.
			getdns_list* sublist = convertToGetDNSList(env, thisObj, jvalue);
			getdns_dict_set_list(result, key, sublist);
			getdns_list_destroy(sublist);
		} else if ((*env)->IsAssignableFrom(env, (*env)->FindClass(env, "[B"),
				valueClass)) { // Map value case.
			int len = 0;
			unsigned char *bytes = convertByteArrayToUnsignedCharArray(env,
					jvalue, &len);
			getdns_bindata this_ipv4_addr = { len, bytes };
			getdns_dict_set_bindata(result, key, &this_ipv4_addr);
			free(bytes);
		}
		(*env)->ReleaseStringUTFChars(env, jkey, key);

	}
	return result;
}
