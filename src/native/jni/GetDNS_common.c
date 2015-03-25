#include "GetDNS_common.h"

// Helper to create an address dictionary from string
// Must be freed by the user
getdns_dict* getdns_util_create_ip(const char* ip) {
	getdns_return_t ret;
	const char* ipType;
	getdns_bindata ipData;
	uint8_t addrBuff[16];
	size_t addrSize = 0;
	getdns_dict* dict = NULL;

	if (!ip) {
		return NULL;
	}
	// convert to bytes
	if (inet_pton(AF_INET, ip, &addrBuff) == 1) {
		addrSize = 4;
	} else if (inet_pton(AF_INET6, ip, &addrBuff) == 1) {
		addrSize = 16;
	}
	if (addrSize == 0) {
		return NULL;
	}
	// create the dict
	dict = getdns_dict_create();
	if (!dict) {
		return NULL;
	}
	// set fields
	ipType = addrSize == 4 ? "IPv4" : "IPv6";
	ret = getdns_dict_util_set_string(dict, (char*) "address_type", ipType);
	if (ret != GETDNS_RETURN_GOOD) {
		getdns_dict_destroy(dict);
		return NULL;
	}
	ipData.data = addrBuff;
	ipData.size = addrSize;
	ret = getdns_dict_set_bindata(dict, "address_data", &ipData);
	if (ret != GETDNS_RETURN_GOOD) {
		getdns_dict_destroy(dict);
		return NULL;
	}
	return dict;
}

/*
 * TODO:   This method is imported from python port. Compare with python code and make sure if we have handled all necessary conditions.
 */
int isPrintable(char* data, int size) {
	int printable = 1;
	size_t i;
	for (i = 0; i < size; ++i) {
		if (!isprint(data[i])) {
			if (data[i] == 0 && i == size - 1) {
				break;
			}
			printable = 0;
			break;
		}
	}
	return printable;
}
