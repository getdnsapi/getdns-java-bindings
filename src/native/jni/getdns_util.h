#include "java_util.h"
#include "GetDNS_common.h"

int
throwExceptionOnErrorWithMessage(JNIEnv *env, char* message,
		getdns_return_t ret);
int
throwExceptionOnError(JNIEnv *env, getdns_return_t ret);

struct getdns_dict* getDnsDict(JNIEnv *env, jobjectArray value,
		struct util_methods methods);

jobject
convertBinData(JNIEnv* env, getdns_bindata* data, const char* key);

jobject
convertToJavaMap(JNIEnv *env, struct util_methods* methods,
		struct getdns_dict *dict);

jobject
convertToList(JNIEnv* env, struct util_methods* methods,
		struct getdns_list* list);

getdns_dict* convertMapToDict(JNIEnv *env, jobject thisObj, jobject mapObj);

getdns_list* convertToGetDNSList(JNIEnv *env, jobject thisObj, jobject mapObj);

