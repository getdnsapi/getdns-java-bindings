#include "getdns/getdns.h"
#include "java_util.h"

int
isPrintable(char* data, int size);

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
