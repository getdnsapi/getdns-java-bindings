#include "com_verisign_getdns_GetDNSContext.h"
#include <ctype.h>
#include <getdns/getdns_ext_libevent.h>
#include <event.h>
#include "getdns_util.h"

static JavaVM *jvm;

void print_response(struct getdns_dict * response) {
	char *dict_str = getdns_pretty_print_dict(response);
	if (dict_str) {
		fprintf(stdout, "The packet %s\n", dict_str);
		free(dict_str);
	}
}

//cleanup function
void cleanup(const char *nativeString, struct getdns_dict *response,
		getdns_dict * this_extensions, JNIEnv *env, jstring name) {
	if (NULL != nativeString)
		(*env)->ReleaseStringUTFChars(env, name, nativeString);

	if (NULL != response) {
		getdns_dict_destroy(response);
	}
	if (NULL != this_extensions)
		getdns_dict_destroy(this_extensions);

}

// Setter functions
typedef getdns_return_t (*getdns_context_uint8_t_setter)(getdns_context*,
		uint8_t);
typedef getdns_return_t (*getdns_context_uint16_t_setter)(getdns_context*,
		uint16_t);

//Set Transport
static getdns_return_t setTransport(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;
	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_dns_transport(context,
				(getdns_transport_t)(*env)->CallIntMethod(env, value,
						methods.intValue));
	}
	return ret;
}

// Set Stub

static getdns_return_t setStub(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.booleanClass)) {
		if ((*env)->CallBooleanMethod(env, value, methods.booleanValue)) {
			ret = getdns_context_set_resolution_type(context,
					GETDNS_RESOLUTION_STUB);
		} else {
			ret = getdns_context_set_resolution_type(context,
					GETDNS_RESOLUTION_RECURSING);
		}
	}
	return ret;
}

//Set Resolution Type
static getdns_return_t setResolutionType(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;
	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_resolution_type(context,
				(getdns_resolution_t)(*env)->CallIntMethod(env, value,
						methods.intValue));
	}
	return ret;
}

//Set TimeOut
static getdns_return_t setTimeout(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_timeout(context,
				(*env)->CallIntMethod(env, value, methods.intValue));
	}
	return ret;
}

//Set Use Threads
static getdns_return_t setUseThreads(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.booleanClass)) {
		ret = getdns_context_set_use_threads(context,
				(*env)->CallBooleanMethod(env, value, methods.booleanValue));
	}
	return ret;
}

//Return dnssec status
static getdns_return_t setReturnDnssecStatus(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;
	if ((*env)->IsInstanceOf(env, value, methods.booleanClass)) {
		if ((*env)->CallBooleanMethod(env, value, methods.booleanValue)) {
			ret = getdns_context_set_return_dnssec_status(context,
					GETDNS_EXTENSION_TRUE);
		} else {
			ret = getdns_context_set_return_dnssec_status(context,
					GETDNS_EXTENSION_FALSE);
		}
	}
	return ret;
}

//Set UpStreams
static getdns_return_t setUpstreams(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	jsize length = (*env)->GetArrayLength(env, value);
	if (length > 0) {
		getdns_list* upstreams = getdns_list_create();
		int i = 0;
		for (i = 0; i < length; i++) {
			struct getdns_dict* ipDict = getDnsDict(env,
					(*env)->GetObjectArrayElement(env, value, i), methods);
			if (NULL != ipDict) {
				size_t len = 0;
				getdns_list_get_length(upstreams, &len);
				getdns_list_set_dict(upstreams, len, ipDict);
				cleanup(NULL, ipDict, NULL, env, NULL);

			} else {
				throwExceptionOnError(env, ret);
			}

		}
		ret = getdns_context_set_upstream_recursive_servers(context, upstreams);
		getdns_list_destroy(upstreams);
	}
	return ret;
}

// set namespaces
static getdns_return_t setNamespace(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_GOOD;
	getdns_namespace_t *namespaces;

	jsize length = (*env)->GetArrayLength(env, value);

	if (length > 0) {
		if ((namespaces = malloc(sizeof(getdns_namespace_t) * length)) == 0) {
			return ret;
		}
		int i = 0;
		for (i = 0; i < length; i++) {
			int intNamespace = getIntFromArrayWithIndex(env, value, methods, i);
			if (intNamespace != 0) {
				namespaces[i] = (getdns_namespace_t) intNamespace;

			} else {
				ret = GETDNS_RETURN_INVALID_PARAMETER;
				break;
			}
		}
		if (ret != GETDNS_RETURN_INVALID_PARAMETER) {
			ret = getdns_context_set_namespaces(context, (size_t) length,
					namespaces);
			free(namespaces);
		} else {
			throwExceptionOnError(env, ret);
		}

	}
	return ret;
}

//set FollowRedirect
static getdns_return_t setFollowRedirect(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_follow_redirects(context,
				(getdns_redirects_t)(*env)->CallIntMethod(env, value,
						methods.intValue));
	}
	return ret;
}

//set AppendName
static getdns_return_t setAppendName(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {

	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_append_name(context,
				(getdns_append_name_t)(*env)->CallIntMethod(env, value,
						methods.intValue));
	}

	return ret;
}

//set DnsRootSevers
static getdns_return_t setDnsRootServers(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	jsize length = (*env)->GetArrayLength(env, value);
	if (length > 0) {
		getdns_list* dns_servers = getdns_list_create();
		int i = 0;
		for (i = 0; i < length; i++) {
			struct getdns_dict* ipDict = getDnsDict(env,
					(*env)->GetObjectArrayElement(env, value, i), methods);
			if (NULL != ipDict) {
				size_t len = 0;
				getdns_list_get_length(dns_servers, &len);
				getdns_list_set_dict(dns_servers, len, ipDict);
				cleanup(NULL, ipDict, NULL, env, NULL);

			} else {
				throwExceptionOnError(env, ret);
			}

		}
		ret = getdns_context_set_dns_root_servers(context, dns_servers);
		getdns_list_destroy(dns_servers);
	}
	return ret;
}

//set Suffix
static getdns_return_t setSuffix(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_GOOD;

	jsize length = (*env)->GetArrayLength(env, value);
	if (length > 0) {
		getdns_list* suffix_list = getdns_list_create();
		int i = 0;
		for (i = 0; i < length; i++) {
			getdns_bindata bin_value;
			const char* suffix = getStringFromArrayWithIndex(env, value,
					methods, i);

			if (suffix != NULL && strcmp(suffix, "") != 0) {
				bin_value.data = (uint8_t *) suffix;
				bin_value.size = strlen(suffix);
				getdns_list_set_bindata(suffix_list, (size_t) i, &bin_value);
				cleanup(suffix, NULL, NULL, env, NULL);
			} else {
				ret = GETDNS_RETURN_INVALID_PARAMETER;
				break;
			}

		}
		if (ret != GETDNS_RETURN_INVALID_PARAMETER) {
			ret = getdns_context_set_suffix(context, suffix_list);
		} else {
			throwExceptionOnError(env, ret);
		}
		getdns_list_destroy(suffix_list);
	}
	return ret;
}

static getdns_return_t setDnssecTrustAnchor(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	jsize length = (*env)->GetArrayLength(env, value);
	if (length > 0) {
		getdns_list* addresses = getdns_list_create();
		int i = 0;
		for (i = 0; i < length; i++) {
			getdns_bindata bin_value;

			const char* address = getStringFromArrayWithIndex(env, value,
					methods, i);
			if (address != NULL && strcmp(address, "") != 0) {
				bin_value.data = (uint8_t *) address;
				bin_value.size = strlen(address);
				getdns_list_set_bindata(addresses, (size_t) i, &bin_value);
				cleanup(address, NULL, NULL, env, NULL);

			} else {
				throwExceptionOnError(env, ret);
			}

		}
		ret = getdns_context_set_dnssec_trust_anchors(context, addresses);
		getdns_list_destroy(addresses);
	}
	return ret;
}

//Set DnssecAllowedSkew
static getdns_return_t setDnssecAllowedSkew(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject value, struct util_methods methods) {
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		ret = getdns_context_set_dnssec_allowed_skew(context,
				(uint32_t)(*env)->CallIntMethod(env, value, methods.intValue));
	}
	return ret;
}
typedef getdns_return_t (*context_setter)(JNIEnv *env, jobject thisObj,
		getdns_context* context, jobject opt, struct util_methods methods);

typedef struct OptionSetter {
	const char* opt_name;
	context_setter setter;
} OptionSetter;

static OptionSetter SETTERS[] = { { "stub", setStub }, { "upstreams",
		setUpstreams }, { "upstream_recursive_servers", setUpstreams }, {
		"timeout", setTimeout }, { "use_threads", setUseThreads }, {
		"return_dnssec_status", setReturnDnssecStatus }, { "dns_transport",
		setTransport }, { "resolution_type", setResolutionType }, { "namespace",
		setNamespace }, { "followredirect", setFollowRedirect }, { "appendname",
		setAppendName }, { "dnsrootservers", setDnsRootServers }, { "suffix",
		setSuffix }, { "dnssec_trust_anchor", setDnssecTrustAnchor }, {
		"dnssec_allowed_skew", setDnssecAllowedSkew } };

static size_t NUM_SETTERS = sizeof(SETTERS) / sizeof(OptionSetter);

typedef struct Uint8OptionSetter {
	const char* opt_name;
	getdns_context_uint8_t_setter setter;
} Uint8OptionSetter;

static Uint8OptionSetter UINT8_OPTION_SETTERS[] = { { "edns_extended_rcode",
		getdns_context_set_edns_extended_rcode }, { "edns_version",
		getdns_context_set_edns_version }, { "edns_do_bit",
		getdns_context_set_edns_do_bit } };

static size_t NUM_UINT8_SETTERS = sizeof(UINT8_OPTION_SETTERS)
		/ sizeof(Uint8OptionSetter);

typedef struct Uint16OptionSetter {
	const char* opt_name;
	getdns_context_uint16_t_setter setter;
} Uint16OptionSetter;

static Uint16OptionSetter UINT16_OPTION_SETTERS[] = { {
		"limit_outstanding_queries",
		getdns_context_set_limit_outstanding_queries }, {
		"edns_maximum_udp_payloadSize",
		getdns_context_set_edns_maximum_udp_payload_size } };

static size_t NUM_UINT16_SETTERS = sizeof(UINT16_OPTION_SETTERS)
		/ sizeof(Uint16OptionSetter);

JNIEXPORT jobject
JNICALL Java_com_verisign_getdns_GetDNSContext_contextCreate(JNIEnv *env,
		jobject thisObj, jobject eventBase, jint setFromOs) {
	jint rs = (*env)->GetJavaVM(env, &jvm);
	if (rs != JNI_OK) {
		throwJavaIssue(env, "Cannot get VM PTR");
		return NULL;
	}
	struct getdns_context *context = NULL;
	getdns_return_t ret = getdns_context_create(&context, setFromOs);
	/*if(ret != GETDNS_RETURN_GOOD) {
	 throwException(env, ret);
	 return NULL;
	 }*/
	if (throwExceptionOnError(env, ret))
		return NULL;

	if (eventBase != NULL) {
		struct event_base *base =
				(struct event_base*) (*env)->GetDirectBufferAddress(env,
						eventBase);
		(void) getdns_extension_set_libevent_base(context, base);
	}
//getdns_context_set_use_threads(context, 1);
	return (*env)->NewDirectByteBuffer(env, (void*) context, 0);
}

/*
 * TODO: Need to validate contextParam is the context and not something else.
 */
JNIEXPORT
void JNICALL
Java_com_verisign_getdns_GetDNSContext_contextDestroy(JNIEnv *env,
		jobject thisObj, jobject contextParam) {
	if (contextParam != NULL) {
		struct getdns_context *context =
				(struct getdns_context*) (*env)->GetDirectBufferAddress(env,
						contextParam);
		getdns_context_destroy(context);
	}
}

/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jobject
JNICALL Java_com_verisign_getdns_GetDNSContext_generalSync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring name, jint request_type,
		jobject extensions) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	struct getdns_dict *response = NULL;
	struct util_methods methods;
	jobject returnValue = NULL;

//find ?
	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)

	getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions); /* getextensions(extensions,env,thisObj);*/

	getdns_return_t ret = getdns_general_sync(context, nativeString,
			request_type, this_extensions, &response);

	if (!throwExceptionOnError(env, ret)
			&& NULL != init_util_methods(env, &methods))
		returnValue = convertToJavaMap(env, &methods, response);

	cleanup(nativeString, response, this_extensions, env, name);
	return returnValue;
}

JNIEXPORT jobject
JNICALL JNICALL
Java_com_verisign_getdns_GetDNSContext_addressSync(JNIEnv *env, jobject thisObj,
		jobject contextParam, jstring name, jobject extensions) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	struct getdns_dict *response = NULL;
	struct util_methods methods;
	jobject returnValue = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)
	getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions); /* getextensions(extensions,env,thisObj); */

	getdns_return_t ret = getdns_address_sync(context, nativeString,
			this_extensions, &response);

	if (!throwExceptionOnError(env, ret)
			&& NULL != init_util_methods(env, &methods))
		returnValue = convertToJavaMap(env, &methods, response);

	cleanup(nativeString, response, this_extensions, env, name);
	return returnValue;
}

JNIEXPORT jobject
JNICALL Java_com_verisign_getdns_GetDNSContext_serviceSync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring name, jobject extensions) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	struct getdns_dict *response = NULL;
	struct util_methods methods;
	jobject returnValue = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)
	getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/

	getdns_return_t ret = getdns_service_sync(context, nativeString,
			this_extensions, &response);

	if (!throwExceptionOnError(env, ret)
			&& NULL != init_util_methods(env, &methods))
		returnValue = convertToJavaMap(env, &methods, response);

	cleanup(nativeString, response, this_extensions, env, name);
	return returnValue;
}

JNIEXPORT jobject
JNICALL Java_com_verisign_getdns_GetDNSContext_hostnameSync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring address,
		jobject extensions) {

	struct getdns_context *context = NULL;
	struct getdns_dict *response = NULL;
	struct getdns_dict* ipDict = NULL;
	struct util_methods methods;
	jobject returnValue = NULL;
	const char *serverIP = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/
	if (address != NULL) {
		serverIP = (*env)->GetStringUTFChars(env, address, 0);
	}

	if (serverIP != NULL && strcmp(serverIP, "") != 0) {
		ipDict = getdns_util_create_ip(serverIP);
	}
	getdns_return_t ret = getdns_hostname_sync(context, ipDict, this_extensions,
			&response);

	if (!throwExceptionOnError(env, ret)
			&& NULL != init_util_methods(env, &methods))
		returnValue = convertToJavaMap(env, &methods, response);

	cleanup(serverIP, response, this_extensions, env, address);
	getdns_dict_destroy(ipDict);
	return returnValue;
}

void callbackfn(struct getdns_context *context,
		getdns_callback_type_t callback_type, struct getdns_dict *response,
		void *userarg, getdns_transaction_t transaction_id) {
	jobject callbackObj = userarg;
	JNIEnv * env;
	jint rs = (*jvm)->AttachCurrentThread(jvm, (void**) &env, NULL);
	struct util_methods methods;
	if (NULL == callbackObj) {
		throwJavaIssue(env, "Callback object was NULL");
		return;
	}
	if (NULL == response) {
		throwJavaIssue(env, "Response was NULL");
		return;
	}
	if (JNI_OK != rs) {
		throwJavaIssue(env, "JVM issue: Cannot get VM PTR");
		return;
	}
	if (NULL == init_util_methods(env, &methods))
		return;

	(*env)->CallObjectMethod(env, callbackObj, methods.callbackHandleResponse,
			convertToJavaMap(env, &methods, response));

	/*
	 * Cleaning up.
	 */
	getdns_dict_destroy(response);
	(*env)->DeleteGlobalRef(env, callbackObj);
}
JNIEXPORT jobject
JNICALL Java_com_verisign_getdns_GetDNSContext_createEventBase(JNIEnv *env,
		jclass class) {

	struct event_base *eventBase;
	eventBase = event_base_new();
	if (eventBase == NULL) {
		fprintf(stderr, "Trying to create the event base failed.\n");
		throwJavaIssue(env, "Trying to create the event base failed.");
		return NULL;
	}
	return (*env)->NewDirectByteBuffer(env, (void*) eventBase, 0);
}

JNIEXPORT
void JNICALL
Java_com_verisign_getdns_GetDNSContext_startListening(JNIEnv *env, jclass class,
		jobject eventBase) {
	if (eventBase != NULL) {
		struct event_base *base =
				(struct event_base*) (*env)->GetDirectBufferAddress(env,
						eventBase);
		event_base_dispatch(base);
	}
}

/*
 * TODO:
 *       Need to validate contextParam.
 */
JNIEXPORT jlong
JNICALL Java_com_verisign_getdns_GetDNSContext_generalAsync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring name, jint request_type,
		jobject extensions, jobject callbackObj) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	getdns_transaction_t transaction_id = 0;
	struct util_methods methods;
	getdns_dict * this_extensions = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)
	this_extensions = convertMapToDict(env, thisObj, extensions);

	getdns_return_t ret = getdns_general(context, nativeString, request_type,
			this_extensions, (*env)->NewGlobalRef(env, callbackObj),
			&transaction_id, callbackfn);
	throwExceptionOnError(env, ret);

	cleanup(nativeString, NULL, this_extensions, env, name);

	return ret;
}

/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jlong
JNICALL Java_com_verisign_getdns_GetDNSContext_addressAsync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring name, jobject extensions,
		jobject callbackObj) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	getdns_transaction_t transaction_id = 0;
	struct util_methods methods;
	getdns_dict * this_extensions = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)
	this_extensions = convertMapToDict(env, thisObj, extensions);

	getdns_return_t ret = getdns_address(context, nativeString, this_extensions,
			(*env)->NewGlobalRef(env, callbackObj), &transaction_id,
			callbackfn);
	throwExceptionOnError(env, ret);

	cleanup(nativeString, NULL, this_extensions, env, name);

	return ret;
}

/*
 * TODO: Need to validate contextParam.
 */
JNIEXPORT jlong
JNICALL Java_com_verisign_getdns_GetDNSContext_serviceAsync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring name, jobject extensions,
		jobject callbackObj) {

	struct getdns_context *context = NULL;
	const char *nativeString = NULL;
	getdns_transaction_t transaction_id = 0;
	struct util_methods methods;
	getdns_dict * this_extensions = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	CHECK_NULL_AND_INIT_STR(name, nativeString)
	this_extensions = convertMapToDict(env, thisObj, extensions);

	getdns_return_t ret = getdns_service(context, nativeString, this_extensions,
			(*env)->NewGlobalRef(env, callbackObj), &transaction_id,
			callbackfn);
	throwExceptionOnError(env, ret);

	cleanup(nativeString, NULL, this_extensions, env, name);

	return ret;
}

JNIEXPORT jlong
JNICALL Java_com_verisign_getdns_GetDNSContext_hostnameAsync(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring address,
		jobject extensions, jobject callbackObj) {

	struct getdns_context *context = NULL;
	getdns_transaction_t transaction_id = 0;
	//struct getdns_dict *response = NULL;
	struct getdns_dict* ipDict = NULL;
	struct util_methods methods;
	jobject returnValue = NULL;
	const char *serverIP = NULL;

	CHECK_NULL_INIT_PTR(contextParam, context)
	getdns_dict * this_extensions = convertMapToDict(env, thisObj, extensions);/* getextensions(extensions,env,thisObj);*/

	if (address != NULL) {
		serverIP = (*env)->GetStringUTFChars(env, address, 0);
	}
	if (serverIP != NULL && strcmp(serverIP, "") != 0) {
		ipDict = getdns_util_create_ip(serverIP);
	}
	getdns_return_t ret = getdns_hostname(context, ipDict, this_extensions,
			(*env)->NewGlobalRef(env, callbackObj), &transaction_id,
			callbackfn);
	throwExceptionOnError(env, ret);

//	if (NULL != init_util_methods(env, &methods)) {
//		returnValue = convertToJavaMap(env, &methods, response);
//	}
	cleanup(serverIP, ipDict, this_extensions, env, NULL);
	//getdns_dict_destroy(ipDict);
	return ret;

}

JNIEXPORT
void JNICALL
Java_com_verisign_getdns_GetDNSContext_applyContextOption(JNIEnv *env,
		jobject thisObj, jobject contextParam, jstring key, jobject value) {

	struct util_methods methods;
	const char *nativeKey = NULL;
	struct getdns_context *context = NULL;
	getdns_return_t ret = GETDNS_RETURN_INVALID_PARAMETER;

	CHECK_NULL_INIT_PTR(contextParam, context);
	if (NULL == init_util_methods(env, &methods)) {
		throwJavaIssue(env,
				"Error while converting to dict, could not init java methods");
	}
	nativeKey = (*env)->GetStringUTFChars(env, key, 0);
	size_t s = 0;
	int found = 0;

	for (s = 0; s < NUM_SETTERS && !found; ++s) {
		if (strcmp(SETTERS[s].opt_name, nativeKey) == 0) {
			found = 1;
			ret = SETTERS[s].setter(env, thisObj, context, value, methods);
			break;
		}
	}

	if ((*env)->IsInstanceOf(env, value, methods.integerClass)) {
		for (s = 0; s < NUM_UINT8_SETTERS && !found; ++s) {
			if (strcmp(UINT8_OPTION_SETTERS[s].opt_name, nativeKey) == 0) {
				found = 1;
				ret = UINT8_OPTION_SETTERS[s].setter(context,
						(uint8_t)(*env)->CallIntMethod(env, value,
								methods.intValue));
				break;
			}
		}
		for (s = 0; s < NUM_UINT16_SETTERS && !found; ++s) {
			if (strcmp(UINT16_OPTION_SETTERS[s].opt_name, nativeKey) == 0) {
				found = 1;
				ret = UINT16_OPTION_SETTERS[s].setter(context,
						(uint16_t)(*env)->CallIntMethod(env, value,
								methods.intValue));
				break;
			}
		}
	}

	char message[100];
	sprintf(message, "Error in contextOption: %s", nativeKey);

	throwExceptionOnErrorWithMessage(env, message, ret);
	cleanup(nativeKey, NULL, NULL, env, NULL);
}

/**
 * Helper function
 * Convert Unicode to Ascii
 */
JNIEXPORT jstring JNICALL Java_com_verisign_getdns_GetDNSContext_ConvertUnicodeToAscii(
		JNIEnv *env, jobject thisObj, jstring value) {
	const char *ulabel = NULL;
	jstring alabel = NULL;
	if (NULL != value) {
		ulabel = (*env)->GetStringUTFChars(env, value, 0);
		alabel = (*env)->NewStringUTF(env,
				getdns_convert_ulabel_to_alabel(ulabel));
		if (NULL == alabel) {
			throwJavaIssue(env, "Error:  Invalid Unicode String");
		}
	}
	cleanup(ulabel, NULL, NULL, env, value);
	return alabel;

}

/**
 * Helper function
 * Convert Ascii to Unicode
 */
JNIEXPORT jstring JNICALL Java_com_verisign_getdns_GetDNSContext_ConvertAsciiToUnicode(
		JNIEnv *env, jobject thisObj, jstring value) {
	const char *alabel = NULL;
	jstring ulabel = NULL;
	if (NULL != value) {
		alabel = (*env)->GetStringUTFChars(env, value, 0);
		ulabel = (*env)->NewStringUTF(env,
				getdns_convert_alabel_to_ulabel(alabel));
		if (NULL == ulabel) {
			throwJavaIssue(env, "Error:  Invalid Ascii String");
		}
	}
	cleanup(alabel, NULL, NULL, env, value);
	return ulabel;

}
#define SIZE 256
/**
 * Helper function
 */
JNIEXPORT jobject JNICALL Java_com_verisign_getdns_GetDNSContext_GetDnsRootTrustAnchor(
		JNIEnv *env, jobject thisObj) {

	struct getdns_list* records = NULL;
	struct util_methods methods;
	if (NULL == init_util_methods(env, &methods)) {
		throwJavaIssue(env,
				"Error while converting to dict, could not init java methods");
	}
	records = getdns_root_trust_anchor(NULL);
	return convertToList(env, &methods, records);
}
